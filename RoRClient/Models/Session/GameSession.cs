using RoRClient.Communication.DataTransferObject;
using RoRClient.Communication.Dispatcher;
using RoRClient.Communication.Topic;
using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace RoRClient.Models.Session
{
    public class GameSession : RoRSession
    {
        private static GameSession gameSession = null;
        protected ObservableCollection<Loco> locos = new ObservableCollection<Loco>();

        private GameSession() : base()
        {

        }

        public new void Init(string topicName)
        {
            base.Init(topicName);
            topicReceiver = new TopicReceiver(topicName, new TopicGameDispatcher());
        }

        public ObservableCollection<Loco> Locos
        {
            get
            {
                return locos;
            }
        }

        public Loco GetLocoById(Guid locoId)
        {
            return locos.Where(x => x.Id == locoId).First();
        }

        private bool started;
        public bool Started
        {
            get
            {
                return started;
            }
            set
            {
                if(started != value)
                {
                    started = value;
                    NotifyPropertyChanged("Started");
                }
            }
        }

        public void AddLoco(Loco loco)
        {
            locos.Add(loco);
            NotifyPropertyChanged("Locos",null,loco);
            
        }
        public static GameSession GetInstance()
        {
            if (gameSession == null)
            {
                gameSession = new GameSession();
            }
            return gameSession;
        }

	    private string mapName;
	    public string MapName
	    {
		    get { return mapName; }
		    set
		    {
			    if (mapName != value)
			    {
				    mapName = value;
				    changeMapName();
				    NotifyPropertyChanged("MapName");
			    }
		    }
	    }

		/// <summary>
		/// Wenn der Player der Host der GameSession ist, dann wird die MapName-Änderung
		/// and den Server geschickt und über den Topic der Session an alle Clients der
		/// GameSession verteilt
		/// </summary>
	    private void changeMapName()
	    {
		    if (GameSession.GetInstance().OwnPlayer.IsHost)
		    {
			    MessageInformation messageInformation = new MessageInformation();
			    messageInformation.PutValue("mapName", gameSession.MapName);
			    GameSession.GetInstance().QueueSender.SendMessage("ChangeMapName", messageInformation);
		    }
	    }
	}
}