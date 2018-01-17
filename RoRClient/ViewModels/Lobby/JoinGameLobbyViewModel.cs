using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Lobby;
using RoRClient.Models.Session;
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
    class JoinGameLobbyViewModel : ViewModelBase
    {
        private UIState uiState;
        private LobbyModel lobbyModel;
        private GameSessionInfo selectedGameSessionInfo;

        public JoinGameLobbyViewModel(UIState uiState, LobbyModel lobbyModel)
        {
            this.uiState = uiState;
            this.lobbyModel = lobbyModel;

            lobbyModel.PropertyChanged += OnClientModelChanged;
            uiState.OnUiStateChanged += OnUiStateChanged;
        }

        public GameSessionInfo SelectedGameSessionInfo
        {
            get
            {
                return selectedGameSessionInfo;
            }
            set
            {
                if(selectedGameSessionInfo != value)
                {
                    selectedGameSessionInfo = value;
                    OnPropertyChanged("SelectedGameSessionInfo");
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
            lobbyModel.ReadGameSessions();
        }

        /// <summary>
        /// Listener des Game Buttons, welcher SendCreateGameSessionCommand() beim klicken aufruft
        /// </summary>
        private ICommand joinGameSessionCommand;
        public ICommand JoinGameSessionCommand
        {
            get
            {
                if (joinGameSessionCommand == null)
                {
                    joinGameSessionCommand = new ActionCommand(param => SendJoinGameSessionCommand());
                }
                return joinGameSessionCommand;
            }
        }
        /// <summary>
        /// Wird von CreateGameSessionCommand aufgerufen schickt passende Nachricht an Server
        /// </summary>
        private void SendJoinGameSessionCommand()
        {
            if(selectedGameSessionInfo != null)
            {
                MessageInformation messageInformation = new MessageInformation();
                messageInformation.PutValue("playerName", lobbyModel.PlayerName);
                messageInformation.PutValue("gameName", selectedGameSessionInfo.Name);
                lobbyModel.getFromClientRequestSender().SendMessage("JoinGameSession", messageInformation);
            }
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
            string gameName = PopupCreator.AskUserToInputString("Gib einer GameSession einen Namen!");

            if(gameName == null) 
            {
                Random rnd = new Random();
                int random = rnd.Next(100000, 999999);
                gameName = "TollesGame" + random.ToString();
            }
            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("playerName", lobbyModel.PlayerName);
            messageInformation.PutValue("gameName", gameName);
            lobbyModel.getFromClientRequestSender().SendMessage("CreateGameSession", messageInformation);
        }

        private ICommand refreshGameSessionsCommand;
        public ICommand RefreshGameSessionsCommand
        {
            get
            {
                if (refreshGameSessionsCommand == null)
                {
                    refreshGameSessionsCommand = new ActionCommand(param => RefreshGameSessionInfos());
                }
                return refreshGameSessionsCommand;
            }
        }

        public void RefreshGameSessionInfos()
        {
            lobbyModel.ReadGameSessions();
        }

        private void OnClientModelChanged(object sender, PropertyChangedEventArgs e)
        {
            if (e.PropertyName == "Connected_Game")
            {
                if (lobbyModel.Connected_Game)
                {
                    uiState.State = "gameLobby";
                } else
                {
                    uiState.State = "joinGameLobby";
                }
            }
        }
    }
}
