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

        public EditorViewModel(UIState uiState)
        {
            this.uiState = uiState;
        }
    }
}
