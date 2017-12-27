using RoRClient.ViewModels.Editor;
using RoRClient.ViewModels.Helper;
using RoRClient.ViewModels.Lobby;
using System.Collections.Generic;
using RoRClient.ViewModels.Game;
using RoRClient.Models.Game;

namespace RoRClient.ViewModels
{
    /// <summary>
    /// Hält das momentane ViewModel und kann es austauschen (UIState)
    /// </summary>
    class MainViewModel : ViewModelBase
    {
        Dictionary<string, ViewModelBase> viewmodels = new Dictionary<string, ViewModelBase>();
        private UIState uiState = new UIState();

        private ViewModelBase _currentViewModel;
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
            CurrentViewModel = viewmodels[args.Statename];
        }

        public MainViewModel()
        {
            LobbyModel lobbyModel = new LobbyModel();

            viewmodels.Add("start", new StartViewModel(uiState, lobbyModel));
            viewmodels.Add("editor", new EditorViewModel(uiState));
            viewmodels.Add("game", new GameViewModel(uiState));
            viewmodels.Add("gameLobby", new GameLobbyViewModel(uiState, lobbyModel));
            viewmodels.Add("joinEditorLobby", new JoinEditorLobbyViewModel(uiState, lobbyModel));
            viewmodels.Add("joinGameLobby", new JoinGameLobbyViewModel(uiState, lobbyModel));

            uiState.OnUiStateChanged += ChangeToView;

            CurrentViewModel = viewmodels["start"];
        }
    }
}
