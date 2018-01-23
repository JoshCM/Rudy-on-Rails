using RoRClient.ViewModels.Editor;
using RoRClient.ViewModels.Helper;
using RoRClient.ViewModels.Lobby;
using System.Collections.Generic;
using RoRClient.ViewModels.Game;
using RoRClient.Models.Game;
using System;
using System.Threading.Tasks;

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
                case "start": CurrentViewModel = new StartViewModel(uiState, lobbyModel);
                        break;
                case "editor": CurrentViewModel = editorViewModel;
                    break;
                case "editorLobby":
                    CurrentViewModel = new EditorLobbyViewModel(uiState, lobbyModel);
                    // wird schon hier erzeugt, weil der UiState erst nach allen Commands geändert wird
                    editorViewModel = new EditorViewModel(uiState, taskFactory);
                    break;
                case "game": CurrentViewModel = gameViewModel;
                    break;
                case "gameLobby":
                    CurrentViewModel = new GameLobbyViewModel(uiState, lobbyModel);
                    // wird schon hier erzeugt, weil der UiState erst nach allen Commands geändert wird
                    gameViewModel = new GameViewModel(uiState, taskFactory);
                    break;
                case "joinEditorLobby": CurrentViewModel = new JoinEditorLobbyViewModel(uiState, lobbyModel);
                    break;
                case "joinGameLobby": CurrentViewModel = new JoinGameLobbyViewModel(uiState, lobbyModel);
                    break;
            }
            //CurrentViewModel = viewmodels[args.Statename];
        }

        public MainViewModel()
        {
            taskFactory = new TaskFactory(TaskScheduler.FromCurrentSynchronizationContext());
            uiState.OnUiStateChanged += ChangeToView;
            uiState.State = "start";
        }
    }
}
