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

        /// <param name="modelId"></param>
        public CanvasGameViewModel(Guid modelId) : base(modelId)
        {
        }

        /// <summary>
        /// Auswählen/Selektieren von ViewModels
        /// </summary>
        private ICommand selectInteractiveGameObjectCommand;
        public ICommand SelectInteractiveGameObjectCommand
        {
            get
            {
                if(selectInteractiveGameObjectCommand == null)
                {
                    selectInteractiveGameObjectCommand = new ActionCommand(param => SelectInteractiveGameObject());
                }
                return selectInteractiveGameObjectCommand;
            }
        }

        /// <summary>
        /// GameObject (Rail etc.) ausgewählt 
        /// </summary>
        public void SelectInteractiveGameObject()
        {
            Console.WriteLine("Ausgewähltes ViewModel: " + this.ToString());

            // Beim Klick auf CanvasViewModel (außer Sensor) darf ein Sensor nicht bearbeitet werden (DropDownMenü)
            MapViewModel.GameInteractionsViewModel.CanConfigureSensor = false;

            // Neues CanvasGameViewModel (this) in MapViewModel merken
            MapViewModel.SelectedGameCanvasViewModel = this;

            if (MapViewModel.SelectedGameCanvasViewModel is RailGameViewModel)
            {
                RailGameViewModel railGameViewModel = (RailGameViewModel)MapViewModel.SelectedGameCanvasViewModel;
                if (!railGameViewModel.Rail.hasSensor())
                {
                    MapViewModel.GameInteractionsViewModel.CanPlaceSensor = true;
                }
                
            } else
            {
                MapViewModel.GameInteractionsViewModel.CanPlaceSensor = false;
            }
        }
    }
}
