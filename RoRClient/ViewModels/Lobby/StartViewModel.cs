using RoRClient.Communication.DataTransferObject;
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

        public StartViewModel(UIState uiState, LobbyModel lobbyModel)
        {
            this.uiState = uiState;
            this.lobbyModel = lobbyModel;
        }

        public LobbyModel LobbyModel
        {
            get
            {
                return lobbyModel;
            }
        }

        private ICommand switchToJoinEditorLobbyView;
        public ICommand SwitchToJoinEditorLobbyView
        {
            get
            {
                if (switchToJoinEditorLobbyView == null)
                {
                    switchToJoinEditorLobbyView = new ActionCommand(e => { uiState.State = "joinEditorLobby"; });
                }
                return switchToJoinEditorLobbyView;
            }
        }

        private ICommand switchToJoinGameLobbyView;
        public ICommand SwitchToJoinGameLobbyView
        {
            get
            {
                if (switchToJoinGameLobbyView == null)
                {
                    switchToJoinGameLobbyView = new ActionCommand(e => { uiState.State = "joinGameLobby"; });
                }
                return switchToJoinGameLobbyView;
            }
        }
    }
}
