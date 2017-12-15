﻿using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.ViewModels.Commands;
using RoRClient.ViewModels.Helper;
using System.ComponentModel;
using System.Windows.Input;

namespace RoRClient.ViewModels.Lobby
{
    class StartViewModel : ViewModelBase
    {
        public UIState uiState;
        private LobbyModel lobbyModel;

        public StartViewModel(UIState uiState)
        {
            lobbyModel = new LobbyModel();
            this.uiState = uiState;

            lobbyModel.PropertyChanged += OnClientModelChanged;
        }
        /// <summary>
        /// Muss noch ersetzt werden durch CreateGameSessionCommand
        /// </summary>
        private ICommand start2GameCmd;
        public ICommand StartToGameCommand
        {
            get
            {
                if (start2GameCmd == null)
                {
                    start2GameCmd = new ActionCommand(e => { uiState.State = "game"; });
                }
                return start2GameCmd;
            }
        }
        /// <summary>
        /// Listener des Editor Buttons, welcher SendCreateEditorSessionCommand() beim klicken aufruft
        /// </summary>
        private ICommand createEditorSessionCommand;
        public ICommand CreateEditorSessionCommand
        {
            get
            {
                if (createEditorSessionCommand == null)
                {
                    createEditorSessionCommand = new ActionCommand(param => SendCreateEditorSessionCommand());
                }
                return createEditorSessionCommand;
            }
        }
        /// <summary>
        /// Wird von CreateEditorSessionCommand aufgerufen schickt passende Nachricht an Server
        /// </summary>
        private void SendCreateEditorSessionCommand()
        {
            lobbyModel.StartConnection();
            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("playerName", "Heinz");
            messageInformation.PutValue("editorName", "Editor1");
            lobbyModel.getFromClientRequestSender().SendMessage("CreateEditorSession", messageInformation);
        }
        /// <summary>
        /// Listener des Game Buttons, welcher SendCreateGameSessionCommand() beim klicken aufruft
        /// </summary>
        private ICommand createGameSessionCommand;
        public ICommand CreateGameSessionCommand
        {
            get
            {
                if (createGameSessionCommand == null)
                {
                    createGameSessionCommand = new ActionCommand(param => SendCreateGameSessionCommand());
                }
                return createGameSessionCommand;
            }
        }
        /// <summary>
        /// Wird von CreateGameSessionCommand aufgerufen schickt passende Nachricht an Server
        /// </summary>
        private void SendCreateGameSessionCommand()
        {
            lobbyModel.StartConnection();
            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("playerName", "Heinz");
            messageInformation.PutValue("gameName", "Game1");
            lobbyModel.getFromClientRequestSender().SendMessage("CreateGameSession", messageInformation);
        }




        public LobbyModel LobbyModel
        {
            get
            {
                return lobbyModel;
            }
        }

        /// <summary>
        /// In FromClientRequestQueueDispatcher wird je eine Boolean Variable fuer Game und Session der Klasse LobbyModel
        /// auf true gesetzt (falls ein Game bzw. ein Editor erstellt oder gejoined werden soll), 
        /// hier wird darauf reagiert und dem entsprechend der uiState auf "editor" oder "game" gesetzt.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OnClientModelChanged(object sender, PropertyChangedEventArgs e)
        {
            if(e.PropertyName == "Connected_Editor")
            {
                if (lobbyModel.Connected_Editor)
                {
                    uiState.State = "editor";
                }
            }
            if (e.PropertyName == "Connected_Game")
            {
                if (lobbyModel.Connected_Game)
                {
                    uiState.State = "game";
                }
            }
        }

    }
}
