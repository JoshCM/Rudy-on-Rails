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

namespace RoRClient.Models.Session
{
    public class RoRSession : ModelBase
    {
        protected string name;
        protected Map map;
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

        public void AddPlayer(Player player)
        {
            players.Add(player);
        }

        public void RemovePlayer(Player player)
        {
            players.Remove(player);
        }

        public QueueSender QueueSender
        {
            get
            {
                return queueSender;
            }
        }

        public void loadDefaultMapAtStartup()
        {
            QueueSender.SendMessage("LoadMap", new MessageInformation());
        }
    }


}
