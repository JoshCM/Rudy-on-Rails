using RoRClient.ViewModels.Helper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace RoRClient.ViewModels.Editor
{
    class EditorViewModel : ViewModelBase
    {
        private UIState uiState;
        private MapViewModel mapViewModel;
        private ToolbarViewModel toolbarViewModel;
        private TopMenuViewModel topMenuViewModel;

        public MapViewModel MapViewModel
        {
            get
            {
                return mapViewModel;
            }
        }

        public ToolbarViewModel ToolbarViewModel
        {
            get
            {
                return toolbarViewModel;
            }
        }

        public TopMenuViewModel TopMenuViewModel
        {
            get
            {
                return topMenuViewModel;
            }
        }

        public EditorViewModel(UIState uiState)
        {
            this.uiState = uiState;
            toolbarViewModel = new ToolbarViewModel();
            mapViewModel = new MapViewModel(toolbarViewModel);
            topMenuViewModel = new TopMenuViewModel();
        }
    }
}
