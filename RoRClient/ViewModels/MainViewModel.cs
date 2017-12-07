using Apache.NMS;
using RoRClient.Model.DataTransferObject;
using RoRClient.Model.Models;
using RoRClient.ViewModel.Helper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModel
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
