﻿using RoRClient.ViewModels.Commands;
using RoRClient.ViewModels.Editor;
using System;
using System.Windows.Input;

namespace RoRClient.ViewModels
{
    /// <summary>
    /// Diese Klasse wird als Base-Klasse für alle ViewModels verwendet, die auf einem Canvas angezeigt werden.
    /// Dazu gehören zum Beispiel Squares und Rails. 
    /// </summary>
    public class CanvasViewModel : ViewModelBase
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

        public CanvasViewModel(Guid modelId)
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

        // Auswählen/Selektieren von ViewModels
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

        // EditorObject (Rail etc.) ausgewählt + Quicknavigation anzeigen (sollte noch umbenannt werden)
        public void SelectInteractiveGameObject()
        {
            // Neues CanvasViewModel im MapViewModel merken
            MapViewModel.SelectedCanvasViewModel = this;
            // Initial das vorherige CanvasViewModel auf das Neue setzen
            if (MapViewModel.PreviousSelectedCanvasViewModel == null)
            {
                MapViewModel.PreviousSelectedCanvasViewModel = this;
                Console.WriteLine("1. Mal");
            }
            // Anzeigen der Quicknavigation
            MapViewModel.setQuickNavigationForCanvasViewModel();

            // Danach das CanvasViewModel als vorheriges CanvasViewModel merken, wenn es sich geänder hat
            if (this != MapViewModel.PreviousSelectedCanvasViewModel)
            {
                MapViewModel.PreviousSelectedCanvasViewModel = this;
                Console.WriteLine("Model hat sich geändert!");

            }
           
        }
    }
}
