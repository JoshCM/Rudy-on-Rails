using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Models.Game;
using RoRClient.Models.Session;

namespace RoRClient.ViewModels.Game
{
    class ScoreboardViewModel : ViewModelBase
    {
        private double opacity;

        public ObservableCollection<Player> playerList;

        public ObservableCollection<Player> PlayerList
        {
            get { return playerList; }
        }

        public ScoreboardViewModel()
        {
            playerList = GameSession.GetInstance().Players;
            opacity = 0.0;
        }


        public double Opacity
        {
            get { return opacity; }
            set { opacity = value; OnPropertyChanged("Opacity"); }
        }

        public void ToggleScoreboard()
        {
            foreach (GamePlayer player in PlayerList)
            {
                Console.WriteLine("Name" + player.Name + " Punkte " + player.PointCount);
            }

            if (Opacity == 1.0)
            {
                Opacity = 0.0;
            }
            else
            {
                Opacity = 1.0;
            }

        }


    }
}
