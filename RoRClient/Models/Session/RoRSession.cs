using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Models.Game;
using System.Collections.ObjectModel;
using RoRClient.Communication.Queue;
using RoRClient.Models.Base;
using RoRClient.Communication.Topic;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Communication;

namespace RoRClient.Models.Session
{
    public class RoRSession : ObservableBase
    {
        protected string name;
        protected Map map;
	    protected string mapName;

		protected ObservableCollection<Player> players = new ObservableCollection<Player>();

        protected QueueSender queueSender;
        protected TopicReceiver topicReceiver;

        public RoRSession()
        {
            map = new Map();
        }

        public string Name
        {
            get
            {
                return name;
            }
            set
            {
                name = value;
            }
        }

        public void Init(string topicName)
        {
            queueSender = new QueueSender(topicName);
        }


        public Map Map
        {
            get
            {
                return map;
            }
            set
            {
                map = value;
            }
        }

        public ObservableCollection<Player> Players
        {
            get
            {
                return players;
            }
        }

        public Player OwnPlayer
        {
            get
            {
                Player ownPlayer = players.Where(x => x.Id == ClientConnection.GetInstance().ClientId).FirstOrDefault();
                if (ownPlayer != null)
                {
                    return ownPlayer;
                }
                return new Player(Guid.NewGuid(), "");
            }
        }

        public void AddPlayer(Player player)
        {
            players.Add(player);
        }

        public void RemovePlayer(Player player)
        {
            players.Remove(player);
        }

        public Player GetPlayerById(Guid playerId)
        {
            return players.Where(x => x.Id == playerId).FirstOrDefault();
        }
        public QueueSender QueueSender
        {
            get
            {
                return queueSender;
            }
        }

	    public string MapName
	    {
		    get { return mapName; }
		    set
		    {
			    if (mapName != value)
			    {
				    mapName = value;
				    NotifyPropertyChanged("MapName");
			    }
		    }
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
			    if (started != value)
			    {
				    started = value;
				    NotifyPropertyChanged("Started");
			    }
		    }
	    }
	}
}
