using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Editor
{
    class ObjectConfigurationViewModel : ViewModelBase
    {
        private MapEditorViewModel mapViewModel;

        public ObjectConfigurationViewModel(MapEditorViewModel mapViewModel)
        {
            this.mapViewModel = mapViewModel;
        }

        public MapEditorViewModel MapViewModel
        {
            get
            {
                return mapViewModel;
            }
        }
    }
}
