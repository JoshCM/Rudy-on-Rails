using RoRClient.Communication.Queue;
using RoRClient.Communication.Topic;
using RoRClient.Models.Base;
using RoRClient.Models.Game;
using System.Collections.ObjectModel;

namespace RoRClient.Models.Editor
{
    /// <summary>
    /// Hält alle nötigen Informationen für eine Session im Editor
    /// Initialisiert einen QueueSender und einen TopiReceiver
    /// </summary>
    public class EditorSession : ModelBase
    {
        private string name;
        private Map map;
        private ObservableCollection<Player> players = new ObservableCollection<Player>();
        private static EditorSession editorSession;
        private QueueSender queueSender;
        private TopicReceiver topicReceiver;

        private EditorSession()
        {
            map = new Map();
        }

        public void Init(string topicName)
        {
            queueSender = new QueueSender(topicName);
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

        public QueueSender QueueSender
        {
            get
            {
                return queueSender;
            }
        }
    }
}
