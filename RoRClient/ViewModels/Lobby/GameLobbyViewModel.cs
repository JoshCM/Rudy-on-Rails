using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Commands;
using RoRClient.ViewModels.Helper;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModels.Lobby
{
    class GameLobbyViewModel : ViewModelBase
    {
        private UIState uiState;
        private bool isHost;

        public GameLobbyViewModel(UIState uiState)
        {
            this.uiState = uiState;
            GameSession.GetInstance().PropertyChanged += OnGameStarted;
            uiState.OnUiStateChanged += OnUiStateChanged;
        }

        public bool IsHost
        {
            get
            {
                return isHost;
            }
            set
            {
                isHost = value;
                OnPropertyChanged("IsHost");
            }
        }

        private ICommand startGameCommand;
        public ICommand StartGameCommand
        {
            get
            {
                if (startGameCommand == null)
                {
                    startGameCommand = new ActionCommand(param => StartGame());
                }
                return startGameCommand;
            }
        }
        /// <summary>
        /// Wird von CreateEditorSessionCommand aufgerufen schickt passende Nachricht an Server
        /// </summary>
        private void StartGame()
        {
            if (GameSession.GetInstance().OwnPlayer.IsHost)
            {
                GameSession.GetInstance().QueueSender.SendMessage("StartGame", new MessageInformation());
            }
        }

        private void OnGameStarted(object sender, PropertyChangedEventArgs e)
        {
            if (e.PropertyName == "Started")
            {
                uiState.State = "game";
            }
        }

        private void OnUiStateChanged(object sender, UiChangedEventArgs args)
        {
            if (uiState.State == "gameLobby")
            {
                isHost = GameSession.GetInstance().OwnPlayer.IsHost;
            }
        }
    }
}
