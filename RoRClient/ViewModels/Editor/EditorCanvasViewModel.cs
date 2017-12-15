using System;
using System.Windows.Input;
using RoRClient.ViewModels.Commands;

namespace RoRClient.ViewModels.Editor
{
    /// <summary>
    /// Diese Klasse wird als Base-Klasse für alle ViewModels verwendet, die auf einem Canvas angezeigt werden.
    /// Dazu gehören zum Beispiel Squares und Rails. 
    /// </summary>
    public abstract class EditorCanvasViewModel : ViewModelBase
    {

        private MapViewModel mapViewModel;
        public MapViewModel MapViewModel
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

        public EditorCanvasViewModel(Guid modelId)
        {
            id = modelId;
        }

        private Guid id;
        public Guid Id
        {
            get
            {
                return id;
            }
        }

        private int squarePosX;
        public int SquarePosX
        {
            get
            {
                return squarePosX;
            }

            set
            {
                if (squarePosX != value)
                {
                    squarePosX = value;
                    OnPropertyChanged("SquarePosX");
                }
            }
        }

        private int squarePosY;
        public int SquarePosY
        {
            get
            {
                return squarePosY;
            }

            set
            {
                if (squarePosY != value)
                {
                    squarePosY = value;
                    OnPropertyChanged("SquarePosY");
                }
            }
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

            // Danach das EditorCanvasViewModel als vorheriges EditorCanvasViewModel merken, wenn es sich geänder hat
            if (this != MapViewModel.PreviousSelectedEditorCanvasViewModel)
            {
                MapViewModel.PreviousSelectedEditorCanvasViewModel = this;
                Console.WriteLine("Model hat sich geändert!");

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
        /// Methode zum Löschen eines EditorCanvasViewModel / Muss in der jeweiligen Unterklasse überschrieben werden
        /// </summary>
        public abstract void Delete();

    }
}
