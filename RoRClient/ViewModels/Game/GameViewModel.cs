using RoRClient.Models.Session;
using RoRClient.ViewModels.Helper;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Input;
using RoRClient.ViewModels.Commands;

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
            GameSession.GetInstance().PropertyChanged += OnWinningPlayerChanged;
            ViewConstants.Init();
        }

        private void OnWinningPlayerChanged(object sender, PropertyChangedEventArgs args)
        {
            if(args.PropertyName == "WinningPlayer")
            {
                if(GameSession.GetInstance().WinningPlayer != null)
                {
                    uiState.State = "gameResult";
                }
            }
        }

        public void OnKeyDownHandler(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.E)
            {
                MessageBox.Show("Du hast E gedrückt!", "Knopf gedrückt");
            }

            if (e.Key == Key.Tab)
            {
                MessageBox.Show("Du hast TAB gedrückt!", "Knopf gedrückt");
            }
        }

    }
}
