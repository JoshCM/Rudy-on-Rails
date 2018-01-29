using RoRClient.ViewModels.Editor;
using RoRClient.ViewModels.Helper;
using RoRClient.ViewModels.Lobby;
using System.Collections.Generic;
using RoRClient.ViewModels.Game;
using RoRClient.Models.Game;
using System;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using RoRClient.ViewModels.Commands;

namespace RoRClient.ViewModels
{
    /// <summary>
    /// Hält das momentane ViewModel und kann es austauschen (UIState)
    /// </summary>
    class MainViewModel : ViewModelBase
    {
        Dictionary<string, ViewModelBase> viewmodels = new Dictionary<string, ViewModelBase>();
        private UIState uiState = new UIState();
        private LobbyModel lobbyModel = new LobbyModel();
        private ViewModelBase _currentViewModel;
        private TaskFactory taskFactory;

        private EditorViewModel editorViewModel;
        private GameViewModel gameViewModel;

        public ViewModelBase CurrentViewModel
        {
            get { return _currentViewModel; }
            set
            {
                _currentViewModel = value;
                OnPropertyChanged("CurrentViewModel");
            }
        }

        private void ChangeToView(object sender, UiChangedEventArgs args)
        {
            switch (args.Statename)
            {
                case "start":
                    CurrentViewModel = new StartViewModel(uiState, lobbyModel);
                    break;
                case "editor":
                    CurrentViewModel = editorViewModel;
                    break;
                case "editorLobby":
                    CurrentViewModel = new EditorLobbyViewModel(uiState, lobbyModel);
                    // wird schon hier erzeugt, weil der UiState erst nach allen Commands geändert wird
                    editorViewModel = new EditorViewModel(uiState, taskFactory);
                    break;
                case "game":
                    CurrentViewModel = gameViewModel;
                    break;
                case "gameLobby":
                    CurrentViewModel = new GameLobbyViewModel(uiState, lobbyModel);
                    // wird schon hier erzeugt, weil der UiState erst nach allen Commands geändert wird
                    gameViewModel = new GameViewModel(uiState, taskFactory);
                    break;
                case "joinEditorLobby":
                    CurrentViewModel = new JoinEditorLobbyViewModel(uiState, lobbyModel);
                    break;
                case "joinGameLobby":
                    CurrentViewModel = new JoinGameLobbyViewModel(uiState, lobbyModel);
                    break;
                case "gameResult":
                    CurrentViewModel = new GameResultViewModel(uiState);
                    break;
            }
            //CurrentViewModel = viewmodels[args.Statename];
        }

        public MainViewModel()
        {
            taskFactory = new TaskFactory(TaskScheduler.FromCurrentSynchronizationContext());
            ViewConstants.TaskFactory = taskFactory;
            uiState.OnUiStateChanged += ChangeToView;
            uiState.State = "start";
        }

        #region Hotkeys
        private ICommand zoomInCommand;

        public ICommand ZoomInCommand
        {
            get
            {
                if (zoomInCommand == null)
                {
                    zoomInCommand = new ActionCommand(param => ZoomIn());
                }
                return zoomInCommand;
            }
        }

        private bool IsInSession()
        {
            return uiState.State == "game" || uiState.State == "editor";
        }

        private void ZoomIn()
        {
            if (IsInSession())
            {
                ViewConstants.SquareDim += 20;
            }
        }

        private ICommand zoomOutCommand;

        public ICommand ZoomOutCommand
        {
            get
            {
                if (zoomOutCommand == null)
                {
                    zoomOutCommand = new ActionCommand(param => ZoomOut());
                }
                return zoomOutCommand;
            }
        }

        private void ZoomOut()
        {
            if (IsInSession() && ViewConstants.SquareDim >= 20)
            {
                ViewConstants.SquareDim -= 20;
            }
        }

        private ICommand toggleScoreboardCommand;

        public ICommand ToggleScoreboardCommand
        {
            get
            {
                if (toggleScoreboardCommand == null)
                {
                    toggleScoreboardCommand = new ActionCommand(param => ToggleScoreboard());
                }
                return toggleScoreboardCommand;
            }
        }

        /// <summary>
        /// Setzt die Opacity des Scoreboards auf 1 oder 0.
        /// </summary>
        private void ToggleScoreboard()
        {
            // Darf nur im Game geöffnet werden
            if (uiState.State == "game")
            {
                ScoreboardViewModel scoreboardViewModel = gameViewModel.ScoreboardViewModel;
                scoreboardViewModel.ToggleScoreboard();
            }
        }

        private ICommand increaseSpeedCommand;
        public ICommand IncreaseSpeedCommand
        {
            get
            {
                if (increaseSpeedCommand == null)
                {
                    increaseSpeedCommand = new ActionCommand(param => RaiseIncreaseSpeedEvent(new EventArgs()));
                }
                return increaseSpeedCommand;
            }
        }

        public static event EventHandler IncreaseSpeedInput;
        private void RaiseIncreaseSpeedEvent(EventArgs e)
        {
            if (uiState.State == "game")
            {
                IncreaseSpeedInput?.Invoke(this, e);
            }
        }

        private ICommand decreaseSpeedCommand;
        public ICommand DecreaseSpeedCommand
        {
            get
            {
                if (decreaseSpeedCommand == null)
                {
                    decreaseSpeedCommand = new ActionCommand(param => RaiseDecreaseSpeedEvent(new EventArgs()));
                }
                return decreaseSpeedCommand;
            }
        }

        public static event EventHandler DecreaseSpeedInput;
        private void RaiseDecreaseSpeedEvent(EventArgs e)
        {
            if (uiState.State == "game")
            {
                DecreaseSpeedInput?.Invoke(this, e);
            }
        }

        private ICommand stopOwnLocoCommand;
        public ICommand StopOwnLocoCommand
        {
            get
            {
                if (stopOwnLocoCommand == null)
                {
                    stopOwnLocoCommand = new ActionCommand(param => RaiseStopOwnLocoEvent(new EventArgs()));
                }
                return stopOwnLocoCommand;
            }
        }

        public static event EventHandler StopOwnLocoInput;
        private void RaiseStopOwnLocoEvent(EventArgs e)
        {
            if (uiState.State == "game")
            {
                StopOwnLocoInput?.Invoke(this, e);
            }
        }

        private ICommand unloadCartsCommand;
        public ICommand UnloadCartsCommand
        {
            get
            {
                if (unloadCartsCommand == null)
                {
                    unloadCartsCommand = new ActionCommand(param => RaiseUnloadCartsEvent(new EventArgs()));
                }
                return unloadCartsCommand;
            }
        }

        public static event EventHandler UnloadCartsInput;
        private void RaiseUnloadCartsEvent(EventArgs e)
        {
            if (uiState.State == "game")
            {
                UnloadCartsInput?.Invoke(this, e);
            }
        }
        #endregion
    }
}