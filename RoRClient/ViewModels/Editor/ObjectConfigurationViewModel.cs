using RoRClient.ViewModels.Editor.ObjectConfiguration;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Editor
{
    class ObjectConfigurationViewModel : ViewModelBase
    {
        private MapEditorViewModel mapViewModel;
        private RailConfigurationViewModel railConfigurationViewModel;

        private ViewModelBase selectedConfigurationViewModel;

        public ObjectConfigurationViewModel(MapEditorViewModel mapViewModel)
        {
            this.mapViewModel = mapViewModel;
            railConfigurationViewModel = new RailConfigurationViewModel(mapViewModel);
            mapViewModel.PropertyChanged += OnSelectedEditorCanvasViewModelChanged;
        }

        public ViewModelBase SelectedConfigurationViewModel
        {
            get
            {
                return selectedConfigurationViewModel;
            }
            set
            {
                selectedConfigurationViewModel = value;
                OnPropertyChanged("SelectedConfigurationViewModel");
            }
        }

        public MapEditorViewModel MapViewModel
        {
            get
            {
                return mapViewModel;
            }
        }

        private void OnSelectedEditorCanvasViewModelChanged(object sender, PropertyChangedEventArgs e)
        {
            if (e.PropertyName == "SelectedEditorCanvasViewModel")
            {
                if(mapViewModel.SelectedEditorCanvasViewModel != null)
                {
                    if (mapViewModel.SelectedEditorCanvasViewModel.GetType() == typeof(RailEditorViewModel))
                    {
                        SelectedConfigurationViewModel = railConfigurationViewModel;
                    }
                }
                else
                {
                    SelectedConfigurationViewModel = null;
                }
            }
        }
    }
}
