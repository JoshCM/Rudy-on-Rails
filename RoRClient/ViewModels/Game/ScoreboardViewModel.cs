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

        public ObservableCollection<Player> PlayerList
        {
            get { return GameSession.GetInstance().Players; }
        }

        public ScoreboardViewModel()
        {
            opacity = 0.0;
        }

        public double Opacity
        {
            get { return opacity; }
            set { opacity = value; OnPropertyChanged("Opacity"); }
        }

        public void ToggleScoreboard()
        {
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
