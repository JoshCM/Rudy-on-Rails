using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Lobby;
using RoRClient.ViewModels.Commands;
using RoRClient.ViewModels.Helper;
using RoRClient.Views.Popup;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModels.Lobby
{
    class JoinEditorLobbyViewModel : ViewModelBase
    {
        private UIState uiState;
        private LobbyModel lobbyModel;
        private EditorSessionInfo selectedEditorSessionInfo;

        public JoinEditorLobbyViewModel(UIState uiState, LobbyModel lobbyModel)
        {
            this.uiState = uiState;
            this.lobbyModel = lobbyModel;

            lobbyModel.PropertyChanged += OnClientModelChanged;
            uiState.OnUiStateChanged += OnUiStateChanged;
        }

        public EditorSessionInfo SelectedEditorSessionInfo
        {
            get
            {
                return selectedEditorSessionInfo;
            }
            set
            {
                if(selectedEditorSessionInfo != value)
                {
                    selectedEditorSessionInfo = value;
                    OnPropertyChanged("SelectedEditorSessionInfo");
                }
            }
        }

        public LobbyModel LobbyModel
        {
            get
            {
                return lobbyModel;
            }
        }

        private void OnUiStateChanged(object sender, UiChangedEventArgs args)
        {
            lobbyModel.StartConnection();
            lobbyModel.ReadEditorSessions();
        }

        /// <summary>
        /// Listener des Editor Buttons, welcher SendCreateEditorSessionCommand() beim klicken aufruft
        /// </summary>
        private ICommand joinEditorSessionCommand;
        public ICommand JoinEditorSessionCommand
        {
            get
            {
                if (joinEditorSessionCommand == null)
                {
                    joinEditorSessionCommand = new ActionCommand(param => SendJoinEditorSessionCommand());
                }
                return joinEditorSessionCommand;
            }
        }
        /// <summary>
        /// Wird von CreateEditorSessionCommand aufgerufen schickt passende Nachricht an Server
        /// </summary>
        private void SendJoinEditorSessionCommand()
        {
            if(selectedEditorSessionInfo != null)
            {
                MessageInformation messageInformation = new MessageInformation();
                messageInformation.PutValue("playerName", lobbyModel.PlayerName);
                messageInformation.PutValue("editorName", selectedEditorSessionInfo.Name);
                lobbyModel.getFromClientRequestSender().SendMessage("JoinEditorSession", messageInformation);
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
            string editorName = PopupCreator.AskUserToInputString("Gib deiner EditorSession einen Namen!");

            if(editorName != "")
            {
                MessageInformation messageInformation = new MessageInformation();
                messageInformation.PutValue("playerName", lobbyModel.PlayerName);
                messageInformation.PutValue("editorName", editorName);
                lobbyModel.getFromClientRequestSender().SendMessage("CreateEditorSession", messageInformation);
            }
        }

        private void OnClientModelChanged(object sender, PropertyChangedEventArgs e)
        {
            if (e.PropertyName == "Connected_Editor")
            {
                if (lobbyModel.Connected_Editor)
                {
                    uiState.State = "editorLobby";
                } else
                {
                    uiState.State = "joinEditorLobby";
                }
            }
        }

        private ICommand refreshEditorSessionsCommand;
        public ICommand RefreshEditorSessionsCommand
        {
            get
            {
                if (refreshEditorSessionsCommand == null)
                {
                    refreshEditorSessionsCommand = new ActionCommand(param => RefreshEditorSessionInfos());
                }
                return refreshEditorSessionsCommand;
            }
        }

        public void RefreshEditorSessionInfos()
        {
            lobbyModel.ReadEditorSessions();
        }

        private ICommand leaveJoinEditorLobbyCommand;
        public ICommand LeaveJoinEditorLobbyCommand
        {
            get
            {
                if (leaveJoinEditorLobbyCommand == null)
                {
                    leaveJoinEditorLobbyCommand = new ActionCommand(param => LeaveJoinEditorLobby());
                }
                return leaveJoinEditorLobbyCommand;
            }
        }

        private void LeaveJoinEditorLobby()
        {
            uiState.State = "start";
        }
    }
}
