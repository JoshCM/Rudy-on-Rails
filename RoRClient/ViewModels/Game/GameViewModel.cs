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
        private MapGameViewModel mapGameViewModel;
        private GameStatusViewModel gameStatusViewModel;

        #region Properties
        public MapGameViewModel MapGameViewModel
        {
            get { return mapGameViewModel; }
        }
        public GameStatusViewModel GameStatusViewModel
        {
            get { return gameStatusViewModel; }
        }
        #endregion

        public GameViewModel(UIState uiState)
        {
            this.uiState = uiState;
            mapGameViewModel = new MapGameViewModel();
            uiState.OnUiStateChanged += OnUiStateChanged;
        }

        private void OnUiStateChanged(object sender, UiChangedEventArgs args)
        {
            if (uiState.State == "game")
            {
                gameStatusViewModel = new GameStatusViewModel();
            }
        }
    }
}
