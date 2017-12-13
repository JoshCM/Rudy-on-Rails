using RoRClient.ViewModels.Editor;
using RoRClient.ViewModels.Helper;
using RoRClient.ViewModels.Lobby;
using System.Collections.Generic;

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
            viewmodels.Add("start", new StartViewModel(uiState));
            viewmodels.Add("editor", new EditorViewModel(uiState));
            //viewmodels.Add("game", new GameViewModel(uiState));

            uiState.OnUiStateChanged += ChangeToView;

            CurrentViewModel = viewmodels["start"];
        }
    }
}
