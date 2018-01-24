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
        private GameInteractionsViewModel gameInteractionsViewModel;
        private TopMenuViewModel topMenuViewModel;

        #region Properties
        public MapGameViewModel MapGameViewModel
        {
            get { return mapGameViewModel; }
        }
        public GameStatusViewModel GameStatusViewModel
        {
            get { return gameStatusViewModel; }
        }
        public GameInteractionsViewModel GameInteractionsViewModel
        {
            get
            {
                return gameInteractionsViewModel;
            }
        }
        public TopMenuViewModel TopMenuViewModel
        {
            get
            {
                return topMenuViewModel;
            }
        }
        #endregion

        public GameViewModel(UIState uiState, TaskFactory taskFactory)
        {
            this.uiState = uiState;
            mapGameViewModel = new MapGameViewModel(taskFactory);
            gameInteractionsViewModel = new GameInteractionsViewModel(taskFactory, mapGameViewModel);
            gameStatusViewModel = new GameStatusViewModel();
            topMenuViewModel = new TopMenuViewModel();
            mapGameViewModel.GameInteractionsViewModel = gameInteractionsViewModel;
        }
    }
}
