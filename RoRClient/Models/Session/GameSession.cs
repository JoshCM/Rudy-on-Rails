using RoRClient.Communication.DataTransferObject;
using RoRClient.Communication.Dispatcher;
using RoRClient.Communication.Topic;
using RoRClient.Models.Game;
using RoRClient.Sound;
using System;
using System.Collections.ObjectModel;
using System.Linq;


namespace RoRClient.Models.Session
{
    public class GameSession : RoRSession
    {
        private static GameSession gameSession = null;
        private GamePlayer winningPlayer;
        protected ObservableCollection<Loco> locos = new ObservableCollection<Loco>();
        protected ObservableCollection<Mine> mines = new ObservableCollection<Mine>();
        protected ObservableCollection<Cart> carts = new ObservableCollection<Cart>();
        protected ObservableCollection<Publictrainstation> publictrainstations = new ObservableCollection<Publictrainstation>();
        private Scripts scripts = new Scripts();

        private GameSession() : base()
        {  
        }

        public Scripts Scripts
        {
            get
            {
                return scripts;
            }
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

        public GamePlayer WinningPlayer
        {
            get
            {
                return winningPlayer;
            }
            set
            {
                winningPlayer = value;
                NotifyPropertyChanged("WinningPlayer");
            }
        }

        public Loco GetLocoById(Guid locoId)
        {
            return locos.Where(x => x.Id == locoId).First();
        }

        public Loco GetLocoByPlayerId(Guid playerId)
        {
            return locos.Where(x => x.PlayerId == playerId).First();
        }

        public void AddLoco(Loco loco)
        {
            locos.Add(loco);
            NotifyPropertyChanged("Locos", null, loco);
        }

        public void addMine(Mine mine)
        {
            mines.Add(mine);
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

        public void DeleteGameSession()
        {
            if(topicReceiver != null)
            {
                topicReceiver.StopConnection();
            }
            gameSession = null;
            NotifyPropertyChanged("GameSessionDeleted");
        }
	

        public Mine getMineByPosition(int xPos, int yPos)
        {
            foreach (Mine mine in mines)
            {
                if (mine.Square.PosX == xPos && mine.Square.PosY == yPos)
                {
                    return mine;
                }
            }
            return null;
        }

        public ObservableCollection<Publictrainstation> Publictrainstations
        {
            get
            {
                return publictrainstations;
            }
        }

        public void addPublictrainstation(Publictrainstation publictrainstation)
        {
            publictrainstations.Add(publictrainstation);
        }

        public Publictrainstation GetTradeableTrainstation()
        {
            foreach (Publictrainstation t in publictrainstations)
            {
                if (t.Tradeable)
                {
                    return t;
                }
            }
            return null;
        }
    }
}