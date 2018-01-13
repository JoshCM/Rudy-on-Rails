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
        protected ObservableCollection<PlayerLoco> playerLocos = new ObservableCollection<PlayerLoco>();
        protected ObservableCollection<GhostLoco> ghostLocos = new ObservableCollection<GhostLoco>();
        protected ObservableCollection<Cart> carts = new ObservableCollection<Cart>();

        private GameSession() : base()
        {

        }

        public new void Init(string topicName)
        {
            base.Init(topicName);
            topicReceiver = new TopicReceiver(topicName, new TopicGameDispatcher());
        }

        public ObservableCollection<PlayerLoco> PlayerLocos
        {
            get
            {
                return playerLocos;
            }
        }

        public ObservableCollection<GhostLoco> GhostLocos
        {
            get
            {
                return ghostLocos;
            }
        }

        public Loco GetLocoById(Guid locoId)
        {
            return playerLocos.Where(x => x.Id == locoId).First();
        }

        public ObservableCollection<Cart> Carts
        {
            get
            {
                return Carts;
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
                if(started != value)
                {
                    started = value;
                    NotifyPropertyChanged("Started");
                }
            }
        }

        public void AddPlayerLoco(PlayerLoco loco)
        {
            playerLocos.Add(loco);
            NotifyPropertyChanged("PlayerLocos", null, loco);
        }
        public void AddGhostLoco(GhostLoco loco)
        {
            ghostLocos.Add(loco);
            NotifyPropertyChanged("GhostLocos", null, loco);
        }

        public void AddCart(Cart cart)
        {
            carts.Add(cart);
            NotifyPropertyChanged("Carts", null, cart);

        }
        public static GameSession GetInstance()
        {
            if (gameSession == null)
            {
                gameSession = new GameSession();
            }
            return gameSession;
        }
	}
}