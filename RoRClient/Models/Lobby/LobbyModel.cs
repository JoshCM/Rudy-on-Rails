using System;
using RoRClient.Models.Base;
using RoRClient.Communication.Queue;
using RoRClient.Communication;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Lobby;
using System.Collections.ObjectModel;
using System.Threading.Tasks;
using RoRClient.Models.Session;

namespace RoRClient.Models.Game
{
    class LobbyModel : ModelBase
    {
        private TaskFactory taskFactory;

        private ObservableCollection<EditorSessionInfo> editorSessionInfos = new ObservableCollection<EditorSessionInfo>();
        private ObservableCollection<GameSessionInfo> gameSessionInfos = new ObservableCollection<GameSessionInfo>();
	    private ObservableCollection<GameInfo> gameInfos = new ObservableCollection<GameInfo>();

		private string playerName = "fresh_meat_" + Guid.NewGuid().ToString();

        private QueueSender fromClientRequestSender;
        private FromServerResponseReceiver queueReceiver;
		private Guid clientId;
        private bool connected_Editor;
        private bool connected_Game;

        public LobbyModel() {
            taskFactory = new TaskFactory(TaskScheduler.FromCurrentSynchronizationContext());
        }

        public string PlayerName
        {
            get
            {
                return playerName;
            }
            set
            {
                if(playerName != value)
                {
                    playerName = value;
                    NotifyPropertyChanged("PlayerName");
                }
            }
        }
    
        public ObservableCollection<EditorSessionInfo> EditorSessionInfos
        {
            get
            {
                return editorSessionInfos;
            }
        }

        public ObservableCollection<GameSessionInfo> GameSessionInfos
        {
            get
            {
                return gameSessionInfos;
            }
        }

	    public ObservableCollection<GameInfo> GameInfos
	    {
		    get
		    {
			    return gameInfos;
		    }
	    }

		public void StartConnection()
        {
            ClientConnection.GetInstance().Setup();

            // Anmelden bei Queue, an die alle Clients ihre Anfragen schicken
            Console.Write("Anmelden bei ClientRequestQueue");
            fromClientRequestSender = new QueueSender("ClientRequestQueue");

            // Erstelle die eigene Queue, an die der Server etwas zurücksenden kann
            clientId = ClientConnection.GetInstance().ClientId;
            Console.Write("Erstellt receiverQueue mit id:" + clientId.ToString());
            queueReceiver = new FromServerResponseReceiver(clientId.ToString(), this);
        }


        public QueueSender getFromClientRequestSender()
		{
			return fromClientRequestSender;
		}

		public Guid ClientId
        {
            get
            {
                return clientId;
            }
        }

        public void ReadEditorSessions()
        {
            MessageInformation messageInformation = new MessageInformation();
            fromClientRequestSender.SendMessage("ReadEditorSessions", messageInformation);
        }
        public void ReadGameSessions()
        {
            MessageInformation messageInformation = new MessageInformation();
            fromClientRequestSender.SendMessage("ReadGameSessions", messageInformation);
        }
	    public void ReadGameInfos()
	    {
			MessageInformation messageInformation = new MessageInformation();
			messageInformation.PutValue("sessionName", GameSession.GetInstance().Name);
		    fromClientRequestSender.SendMessage("ReadGameInfos", messageInformation);
		}

		public bool Connected_Editor
        {
            get
            {
                return connected_Editor;
            }
            set
            {
                if(connected_Editor != value)
                {
                    connected_Editor = value;
                    NotifyPropertyChanged("Connected_Editor");
                }
            }
        }
        public bool Connected_Game
        {
            get
            {
                return connected_Game;
            }
            set
            {
                if (connected_Game != value)
                {
                    connected_Game = value;
                    NotifyPropertyChanged("Connected_Game");
                }
            }
        }

        public void AddEditorSessionInfo(EditorSessionInfo editorSessionInfo)
        {
            taskFactory.StartNew(() => editorSessionInfos.Add(editorSessionInfo));
            NotifyPropertyChanged("EditorSessionInfos");
        }

        public void ClearEditorSessionInfos()
        {
            taskFactory.StartNew(() => editorSessionInfos.Clear());
            NotifyPropertyChanged("EditorSessionInfos");
        }

        public void AddGameSessionInfo(GameSessionInfo gameSessionInfo)
        {
            taskFactory.StartNew(() => gameSessionInfos.Add(gameSessionInfo));
            NotifyPropertyChanged("GameSessionInfos");
        }

        public void ClearGameSessionInfos()
        {
            taskFactory.StartNew(() => gameSessionInfos.Clear());
            NotifyPropertyChanged("GameSessionInfos");
        }

	    public void AddGameInfo(GameInfo gameInfo)
	    {
		    taskFactory.StartNew(() => gameInfos.Add(gameInfo));
		    NotifyPropertyChanged("GameInfos");
	    }

	    public void ClearGameInfos()
	    {
		    taskFactory.StartNew(() => gameInfos.Clear());
		    NotifyPropertyChanged("GameInfos");
	    }
    }
}
