using System;
using System.Windows.Input;
using RoRClient.ViewModels.Base;
using RoRClient.ViewModels.Commands;

namespace RoRClient.ViewModels.Game
{
    /// <summary>
    /// Diese Klasse wird als Base-Klasse für alle ViewModels verwendet, die auf einem Canvas angezeigt werden.
    /// Dazu gehören zum Beispiel Squares und Rails. 
    /// </summary>
    public abstract class CanvasGameViewModel : CanvasViewModelBase
    {

        private MapGameViewModel mapViewModel;
        public MapGameViewModel MapViewModel
        {
            get
            {
                return mapViewModel;
            }
            set
            {
                mapViewModel = value;
            }
        }

        public CanvasGameViewModel(Guid modelId) : base(modelId)
        {
        }

      

    }
}
