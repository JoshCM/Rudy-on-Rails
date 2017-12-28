using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Commands;
using RoRClient.ViewModels.Helper;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using RoRClient.Models.Lobby;

namespace RoRClient.ViewModels.Lobby
{
    class GameLobbyViewModel : ViewModelBase
    {
        private UIState uiState;
        private bool isHost;
	    private LobbyModel lobbyModel;
	    private MapInfo selectedMapInfo;

		public GameLobbyViewModel(UIState uiState, LobbyModel lobbyModel)
        {
            this.uiState = uiState;
	        this.lobbyModel = lobbyModel;

            GameSession.GetInstance().PropertyChanged += OnGameStarted;
            uiState.OnUiStateChanged += OnUiStateChanged;
        }

	    public MapInfo SelectedMapInfo
	    {
		    get { return selectedMapInfo; }
		    set
		    {
			    if (selectedMapInfo != value)
			    {
				    selectedMapInfo = value;
				    OnPropertyChanged("SelectedMapInfo");
				    changeMapName();
				}
			}
	    }

	    private void changeMapName()
	    {
		    if (GameSession.GetInstance().OwnPlayer.IsHost)
		    {
			    MessageInformation messageInformation = new MessageInformation();
				messageInformation.PutValue("mapName", selectedMapInfo.Name);
				GameSession.GetInstance().QueueSender.SendMessage("ChangeMapName", messageInformation);
		    }
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

	    public LobbyModel LobbyModel
	    {
		    get { return lobbyModel; }
		    set { lobbyModel = value; }
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
	            if(isHost)
	            {
		            SelectedMapInfo = lobbyModel.MapInfos.FirstOrDefault();
	            }
	            else
	            {
		            //SelectedMapInfo
				}
            }
        }
    }
}
