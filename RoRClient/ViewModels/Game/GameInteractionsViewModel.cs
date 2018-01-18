using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    public class GameInteractionsViewModel : ViewModelBase
    {
        public Scripts Scripts
        {
            get
            {
                return GameSession.GetInstance().Scripts;
            }
        }
    }
}
