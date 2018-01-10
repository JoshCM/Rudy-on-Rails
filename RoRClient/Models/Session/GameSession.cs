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
        protected ObservableCollection<Mine> mines = new ObservableCollection<Mine>();

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

        public void AddLoco(Loco loco)
        {
            locos.Add(loco);
            NotifyPropertyChanged("Locos",null,loco);
            
        }

        public void addMine(Mine mine)
        {
            mines.Add(mine);
        }
        public static GameSession GetInstance()
        {
            if (gameSession == null)
            {
                gameSession = new GameSession();
            }
            return gameSession;
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
    }
}