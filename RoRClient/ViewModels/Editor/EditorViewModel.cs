using RoRClient.ViewModels.Helper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Editor
{
    class EditorViewModel : ViewModelBase
    {
        private UIState uiState;
        private MapViewModel mapViewModel;
        private ToolbarViewModel toolbarViewModel;

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

        public EditorViewModel(UIState uiState)
        {
            this.uiState = uiState;
            toolbarViewModel = new ToolbarViewModel();
            mapViewModel = new MapViewModel(toolbarViewModel);
        }
    }
}
