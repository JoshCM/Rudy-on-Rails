using System;
using System.Windows.Input;
using RoRClient.ViewModels.Commands;
using RoRClient.ViewModels.Base;

namespace RoRClient.ViewModels.Editor
{
    /// <summary>
    /// Diese Klasse wird als Base-Klasse für alle ViewModels verwendet, die auf einem Canvas angezeigt werden.
    /// Dazu gehören zum Beispiel Squares und Rails. 
    /// </summary>
    public abstract class CanvasEditorViewModel : CanvasViewModelBase
    {
        private MapEditorViewModel mapViewModel;
        public MapEditorViewModel MapViewModel
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

        public CanvasEditorViewModel(Guid modelId) : base(modelId)
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
                if (selectInteractiveGameObjectCommand == null)
                {
                    selectInteractiveGameObjectCommand = new ActionCommand(param => SelectInteractiveGameObject());
                }
                return selectInteractiveGameObjectCommand;
            }
        }

        /// <summary>
        /// EditorObject (Rail etc.) ausgewählt + Quicknavigation anzeigen
        /// </summary>
        public void SelectInteractiveGameObject()
        {
            Boolean showable = true;
            
            // checkt ob das CnavasViewModel ein RailViewModel ist
            // und ob dessen Rail eine trainstationId hat
            if (this is RailEditorViewModel){
                if( ((RailEditorViewModel)this).Rail.TrainstationId != Guid.Empty) // ist nie null
                {
                    showable = false;
                }
            }
            if (showable) {
                // Neues EditorCanvasViewModel im MapViewModel merken
                MapViewModel.SelectedEditorCanvasViewModel = this;

                // Initial das vorherige EditorCanvasViewModel auf das Neue setzen
                if (MapViewModel.PreviousSelectedEditorCanvasViewModel == null)
                {
                    MapViewModel.PreviousSelectedEditorCanvasViewModel = this;
                    Console.WriteLine("1. Mal");
                }
                // Anzeigen der Quicknavigation
                MapViewModel.SwitchQuickNavigationForCanvasViewModel();

                // Danach das EditorCanvasViewModel als vorheriges EditorCanvasViewModel merken, wenn es sich geändert hat
                if (this != MapViewModel.PreviousSelectedEditorCanvasViewModel)
                {
                    MapViewModel.PreviousSelectedEditorCanvasViewModel = this;
                    Console.WriteLine("Model hat sich geändert!");
                }
            }
        }
        
        /// <summary>
        /// Methode zum Rotieren eines EditorCanvasViewModel nach links / Muss in der jeweiligen Unterklasse überschrieben werden
        /// </summary>
        public abstract void RotateLeft();

        /// <summary>
        /// Methode zum Rotieren eines EditorCanvasViewModel nach rechts / Muss in der jeweiligen Unterklasse überschrieben werden
        /// </summary>
        public abstract void RotateRight();

        /// <summary>
        /// Methode zum Verschieben eines EditorCanvasViewModel / Muss in der jeweiligen Unterklasse überschrieben werden
        /// </summary>
        public abstract void Move();

        /// <summary>
        /// Methode zum Verschieben eines EditorCanvasViewModel / Muss in der jeweiligen Unterklasse überschrieben werden
        /// </summary>
        public abstract void ChangeSwitch();

        /// <summary>
        /// Methode zum Löschen eines EditorCanvasViewModel / Muss in der jeweiligen Unterklasse überschrieben werden
        /// </summary>
        public abstract void Delete();
    }
}
