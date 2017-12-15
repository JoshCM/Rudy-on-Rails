using RoRClient.ViewModels.Helper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    class GameViewModel : ViewModelBase
    {
        UIState uiState;
        private GameMapViewModel gameMapViewModel;

        #region Properties
        public GameMapViewModel GameMapViewModel
        {
            get { return gameMapViewModel; }
        }
        #endregion

        public GameViewModel(UIState uiState)
        {
            this.uiState = uiState;
            gameMapViewModel = new GameMapViewModel();
        }
    }
}
