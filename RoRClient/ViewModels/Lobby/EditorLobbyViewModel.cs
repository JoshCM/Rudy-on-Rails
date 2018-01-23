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

namespace RoRClient.ViewModels.Lobby
{
	class EditorLobbyViewModel : ViewModelBase
	{
		private UIState uiState;
		private bool isHost;
		private LobbyModel lobbyModel;
		private EditorSession editorSession;
        private bool canStartEditor;
        private string selectedMapName;

		public EditorLobbyViewModel(UIState uiState, LobbyModel lobbyModel)
		{
			this.uiState = uiState;
			this.lobbyModel = lobbyModel;
			this.editorSession = EditorSession.GetInstance();
			uiState.OnUiStateChanged += OnUiStateChanged;
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

        public string SelectedMapName
        {
            get
            {
                return selectedMapName;
            }
            set
            {
                if (selectedMapName != value)
                {
                    selectedMapName = value;
                    ChangeMapName();
                    OnPropertyChanged("SelectedMapName");
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
                messageInformation.PutValue("mapName", selectedMapName);
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
				MessageInformation messageInformation = new MessageInformation();
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

		private void OnUiStateChanged(object sender, UiChangedEventArgs args)
		{
			if (uiState.State == "editorLobby")
			{
                editorSession = EditorSession.GetInstance();
                editorSession.PropertyChanged += OnEditorSessionChanged;

                isHost = EditorSession.GetInstance().OwnPlayer.IsHost;
                lobbyModel.ReadMapInfos();
				lobbyModel.ReadEditorInfos();
            }
		}
	}
}
