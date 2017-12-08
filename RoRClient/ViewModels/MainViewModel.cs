using RoRClient.ViewModels.Editor;
using RoRClient.ViewModels.Helper;
using RoRClient.ViewModels.Lobby;
using System.Collections.Generic;

namespace RoRClient.ViewModels
{
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

        private void changeToView(object sender, UiChangedEventArgs args)
        {
            CurrentViewModel = viewmodels[args.Statename];
        }
        public MainViewModel()
        {
            viewmodels.Add("start", new StartViewModel(uiState));
            viewmodels.Add("editor", new EditorViewModel(uiState));

            uiState.OnUiStateChanged += changeToView;

            CurrentViewModel = viewmodels["start"];
        }
    }
}
