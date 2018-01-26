using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Helper;
using System.Windows.Input;
using RoRClient.Communication.DataTransferObject;
using RoRClient.ViewModels.Commands;
using System.Collections.ObjectModel;
using RoRClient.Models.Lobby;

namespace RoRClient.ViewModels.Lobby
{
	class EditorLobbyViewModel : ViewModelBase
	{
		private UIState uiState;
		private bool isHost;
		private LobbyModel lobbyModel;
		private EditorSession editorSession;
        private bool canStartEditor;
        private bool editorIsNotStarted = true;
        private MapInfo selectedMapInfo;

        private ObservableCollection<PossibleMapSize> possibleMapSizes = new ObservableCollection<PossibleMapSize>();
        private PossibleMapSize selectedPossibleMapSize;
        private bool newMapIsSelected;

        public EditorLobbyViewModel(UIState uiState, LobbyModel lobbyModel)
        {
            this.uiState = uiState;
            this.lobbyModel = lobbyModel;
            this.editorSession = EditorSession.GetInstance();
            InitPossibleMapSizes();

            editorSession = EditorSession.GetInstance();
            editorSession.PropertyChanged += OnEditorSessionChanged;

            isHost = EditorSession.GetInstance().OwnPlayer.IsHost;
            lobbyModel.ReadMapInfos();
            lobbyModel.ReadEditorInfos();
        }

        private void InitPossibleMapSizes()
        {
            possibleMapSizes.Add(new PossibleMapSize(30, "Sehr klein (30x30)"));
            possibleMapSizes.Add(new PossibleMapSize(50, "Klein (50x50)"));
            possibleMapSizes.Add(new PossibleMapSize(70, "Mittel (70x70)"));
            possibleMapSizes.Add(new PossibleMapSize(100, "Groß (100x100)"));
            selectedPossibleMapSize = possibleMapSizes[1];
        }

        public ObservableCollection<PossibleMapSize> PossibleMapSizes
        {
            get
            {
                return possibleMapSizes;
            }
        }

        public PossibleMapSize SelectedPossibleMapSize
        {
            get
            {
                return selectedPossibleMapSize;
            }
            set
            {
                if(selectedPossibleMapSize != value)
                {
                    selectedPossibleMapSize = value;
                    OnPropertyChanged("SelectedPossibleMapSize");
                }
            }
        }

        public bool NewMapIsSelected
        {
            get
            {
                return newMapIsSelected;
            }
            set
            {
                if(newMapIsSelected != value)
                {
                    newMapIsSelected = value;
                    OnPropertyChanged("NewMapIsSelected");
                }
            }
        }

        /// <summary>
        /// Die EditorSession muss hier als Property vorhanden sein, damit der MapName
        /// in der MapListBox gebindet werden kann
        /// </summary>
        public EditorSession EditorSession
		{
			get { return editorSession; }
			set { editorSession = value; }
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

        public bool CanStartEditor
        {
            get
            {
                return canStartEditor;
            }
            set
            {
                canStartEditor = value;
                OnPropertyChanged("CanStartEditor");
            }
        }

        public bool EditorIsNotStarted
        {
            get
            {
                return editorIsNotStarted;
            }
            set
            {
                editorIsNotStarted = value;
                OnPropertyChanged("EditorIsNotStarted");
            }
        }

        public MapInfo SelectedMapInfo
        {
            get
            {
                return selectedMapInfo;
            }
            set
            {
                if (selectedMapInfo != value)
                {
                    selectedMapInfo = value;
                    ChangeMapName();
                    OnPropertyChanged("SelectedMapInfo");

                    if (selectedMapInfo != null && selectedMapInfo.Name.StartsWith("#"))
                    {
                        NewMapIsSelected = true;
                    } else
                    {
                        NewMapIsSelected = false;
                    }
                }
            }
        }

        /// <summary>
        /// Wenn der Player der Host der GameSession ist, dann wird die MapName-Änderung
        /// and den Server geschickt und über den Topic der Session an alle Clients der
        /// GameSession verteilt
        /// </summary>
        private void ChangeMapName()
        {
            if (editorSession.OwnPlayer.IsHost)
            {
                MessageInformation messageInformation = new MessageInformation();
                messageInformation.PutValue("mapName", selectedMapInfo.Name);
                editorSession.QueueSender.SendMessage("ChangeMapSelection", messageInformation);
            }
        }

        private ICommand startEditorCommand;
		public ICommand StartEditorCommand
		{
			get
			{
				if (startEditorCommand == null)
				{
					startEditorCommand = new ActionCommand(param => StartEditor());
				}
				return startEditorCommand;
			}
		}

		private ICommand refreshEditorInfosCommand;
		public ICommand RefreshEditorInfosCommand
		{
			get
			{
				if (refreshEditorInfosCommand == null)
				{
					refreshEditorInfosCommand = new ActionCommand(param => RefreshEditorInfos());
				}
				return refreshEditorInfosCommand;
			}
		}

		private void RefreshEditorInfos()
		{
			lobbyModel.ReadEditorInfos();
		}

		private void StartEditor()
		{
			if (EditorSession.GetInstance().OwnPlayer.IsHost)
			{
                EditorIsNotStarted = false;
				MessageInformation messageInformation = new MessageInformation();
                messageInformation.PutValue("mapSize", selectedPossibleMapSize.MapSize);
				EditorSession.GetInstance().QueueSender.SendMessage("StartEditor", messageInformation);
			}
		}

        private ICommand leaveEditorCommand;
        public ICommand LeaveEditorCommand
        {
            get
            {
                if (leaveEditorCommand == null)
                {
                    leaveEditorCommand = new ActionCommand(param => LeaveEditor());
                }
                return leaveEditorCommand;
            }
        }

        private void LeaveEditor()
        {
            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("playerId", EditorSession.GetInstance().OwnPlayer.Id);
            messageInformation.PutValue("isHost", EditorSession.GetInstance().OwnPlayer.IsHost);
            EditorSession.GetInstance().QueueSender.SendMessage("LeaveEditor", messageInformation);
        }

        private void OnEditorSessionChanged(object sender, PropertyChangedEventArgs e)
		{
			if (e.PropertyName == "Started")
			{
				uiState.State = "editor";
			}
            else if (e.PropertyName == "Left")
            {
                uiState.State = "joinEditorLobby";
            }
            else if (e.PropertyName == "MapName")
            {
                CanStartEditor = IsHost && editorSession.MapName != "";
            }
        }
	}
}
