using RoRClient.Model.Connections;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models.Editor
{
    public class EditorSession : ModelBase
    {
        private string name;
        private Map map;
        private ObservableCollection<Player> players = new ObservableCollection<Player>();
        private static EditorSession editorSession;
        private QueueSender sender;
        private TopicReceiver topicReceiver;

        private EditorSession()
        {
            
        }

        public void Init(string topicName)
        {
            sender = new QueueSender(topicName);
            topicReceiver = new TopicReceiver(topicName);
        }

        public static EditorSession GetInstance()
        {
            if(editorSession == null)
            {
                editorSession = new EditorSession();
            }
            return editorSession;
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

    }
}
