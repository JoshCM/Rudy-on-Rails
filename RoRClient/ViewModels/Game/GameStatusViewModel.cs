using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    class GameStatusViewModel : ViewModelBase
    {
        private GamePlayer gamePlayer;
        
        public GameStatusViewModel()
        {
            Player ownPlayer = (GamePlayer)GameSession.GetInstance().OwnPlayer;
            GamePlayer = (GamePlayer)ownPlayer;
        }

        public GamePlayer GamePlayer
        {
            get
            {
                return gamePlayer;
            }
            set
            {
                gamePlayer = value;
                OnPropertyChanged("GamePlayer");
            }
        }
    }
}
