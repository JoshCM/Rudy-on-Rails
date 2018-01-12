using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Commands;
using RoRClient.ViewModels.Helper;
using System.ComponentModel;
using System.Windows.Input;

namespace RoRClient.ViewModels.Lobby
{
	class GameLobbyViewModel : ViewModelBase
    {
        private UIState uiState;
        private bool isHost;
	    private LobbyModel lobbyModel;
	    private GameSession gameSession;

		public GameLobbyViewModel(UIState uiState, LobbyModel lobbyModel)
        {
            this.uiState = uiState;
	        this.lobbyModel = lobbyModel;
	        this.gameSession = GameSession.GetInstance();

			GameSession.GetInstance().PropertyChanged += OnGameStarted;
            GameSession.GetInstance().PropertyChanged += OnGameLeft;
            uiState.OnUiStateChanged += OnUiStateChanged;
        }

		/// <summary>
		/// Die GameSession muss hier als Property vorhanden sein, damit der MapName
		/// in der MapListBox gebindet werden kann
		/// </summary>
	    public GameSession GameSession
	    {
		    get { return gameSession; }
		    set { gameSession = value; }
	    }

	    public LobbyModel LobbyModel
	    {
		    get { return lobbyModel; }
		    set { lobbyModel = value; }
	    }

		/// <summary>
		/// Setzt den boolean, ob der User Host ist oder nicht
		/// </summary>
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


	    private ICommand refreshGameInfosCommand;
	    public ICommand RefreshGameInfosCommand
		{
		    get
		    {
			    if (refreshGameInfosCommand == null)
			    {
				    refreshGameInfosCommand = new ActionCommand(param => RefreshGameInfos());
			    }
			    return refreshGameInfosCommand;
		    }
	    }

	    public void RefreshGameInfos()
	    {
		    lobbyModel.ReadGameInfos();
	    }

		private void StartGame()
        {
            if (GameSession.GetInstance().OwnPlayer.IsHost)
            {
				MessageInformation messageInformation = new MessageInformation();
                GameSession.GetInstance().QueueSender.SendMessage("StartGame", messageInformation);
            }
        }

        private ICommand leaveGameCommand;
        public ICommand LeaveGameCommand
        {
            get
            {
                if (leaveGameCommand == null)
                {
                    leaveGameCommand = new ActionCommand(param => LeaveGame());
                }
                return leaveGameCommand;
            }
        }

        private void LeaveGame()
        {
            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("playerId", GameSession.GetInstance().OwnPlayer.Id);
            messageInformation.PutValue("isHost", GameSession.GetInstance().OwnPlayer.IsHost);
            GameSession.GetInstance().QueueSender.SendMessage("LeaveGame", messageInformation);
        }

        private void OnGameLeft(object sender, PropertyChangedEventArgs e)
        {
            if (e.PropertyName == "Left")
            {
                uiState.State = "joinGameLobby";
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
				lobbyModel.ReadMapInfos();
				lobbyModel.ReadGameInfos();
            }
        }
    }
}
