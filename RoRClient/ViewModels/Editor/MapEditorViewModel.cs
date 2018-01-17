using RoRClient.Models.Game;
using RoRClient.ViewModels.Helper;
using RoRClient.Models.Session;
using System;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Windows.Input;
using System.Linq;
using System.Collections.Generic;
using System.Windows;
using System.Windows.Threading;
using RoRClient.ViewModels.Commands;
using RoRClient.Models.Base;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Editor
{
    /// <summary>
    /// Hält alle ViewModels die placeableOnSquare sind, sowie die Squares der Map
    /// und momentan noch die Map an sich
    /// </summary>
    public class MapEditorViewModel : ViewModelBase
    {
        private TaskFactory taskFactory;
        private ToolbarViewModel toolbarViewModel;

        private CanvasEditorViewModel _previousSelectEditorCanvasViewModel;

        public MapEditorViewModel(ToolbarViewModel toolbarViewModel)
        {
            this.toolbarViewModel = toolbarViewModel;
            taskFactory = new TaskFactory(TaskScheduler.FromCurrentSynchronizationContext());
            map = EditorSession.GetInstance().Map;
            InitSquares();
            MapWidth = map.Squares.GetLength(0) * ViewConstants.SQUARE_DIM;
            MapHeight = map.Squares.GetLength(1) * ViewConstants.SQUARE_DIM;
        }

        public CanvasEditorViewModel PreviousSelectedEditorCanvasViewModel
        {
            get
            {
                return _previousSelectEditorCanvasViewModel;
            }
            set
            {
                _previousSelectEditorCanvasViewModel = value;
            }
        }

        private CanvasEditorViewModel _selectedEditorCanvasViewModel;
        public CanvasEditorViewModel SelectedEditorCanvasViewModel
        {
            get
            {
                return _selectedEditorCanvasViewModel;
            }
            set
            {
                _selectedEditorCanvasViewModel = value;
                OnPropertyChanged("SelectedEditorCanvasViewModel");
            }
        }

        private Map map;

        private ObservableCollection<SquareEditorViewModel> squareViewModels = new ObservableCollection<SquareEditorViewModel>();
        public ObservableCollection<SquareEditorViewModel> SquareViewModels
        {
            get
            {
                return squareViewModels;
            }
        }

        private ObservableCollection<CanvasEditorViewModel> placeableOnSquareCollection = new ObservableCollection<CanvasEditorViewModel>();
        public ObservableCollection<CanvasEditorViewModel> PlaceableOnSquareCollection
        {
            get
            {
                return placeableOnSquareCollection;
            }
        }

        private ObservableCollection<CanvasEditorViewModel> placeableOnRailCollection = new ObservableCollection<CanvasEditorViewModel>();
        public ObservableCollection<CanvasEditorViewModel> PlaceableOnRailCollection
        {
            get
            {
                return placeableOnRailCollection;
            }
        }

        private int mapWidth;
        public int MapWidth
        {
            get { return mapWidth; }
            set
            {
                if (mapWidth != value)
                {
                    mapWidth = value;
                    OnPropertyChanged("MapWidth");
                }
            }
        }

        private int mapHeight;
        public int MapHeight
        {
            get { return mapHeight; }
            set
            {
                if (mapHeight != value)
                {
                    mapHeight = value;
                    OnPropertyChanged("MapHeight");
                }
            }
        }

        /// <summary>
        /// Erstellt eine Map mit mehreren Rails, die zurzeit zu Testzwecken verwendet wird.
        /// Später wird die Map dann über den Server geschickt.
        /// </summary>
        /// <returns>TestMap</returns>
        private void InitSquares()
        {
            foreach (Square square in map.Squares)
            {
                SquareEditorViewModel squareViewModel = new SquareEditorViewModel(square, toolbarViewModel);
                squareViewModel.MapViewModel = this;
                squareViewModels.Add(squareViewModel);
                square.PropertyChanged += OnSquarePropertyChanged;

                /*
                if (square.PlaceableOnSquare != null)
                {
                    switch (square.PlaceableOnSquare.GetType().Name)
                    {
                        case "Rail":
                            Rail rail = (Rail)square.PlaceableOnSquare;
                            RailEditorViewModel railViewModel = new RailEditorViewModel(rail);
                            railViewModel.ToolbarViewModel = toolbarViewModel;
                            placeableOnSquareCollection.Add(railViewModel);
                            rail.PropertyChanged += OnRailPropertyChanged;
                            break;
                        case "Trainstation":
                            Trainstation trainstation = (Trainstation)square.PlaceableOnSquare;
                            TrainstationEditorViewModel trainstationViewModel = new TrainstationEditorViewModel(trainstation);
                            placeableOnSquareCollection.Add(trainstationViewModel);
                            trainstation.PropertyChanged += OnTrainstationPropertyChanged;
                            break;
                    }
                }
                */
            }
        }

        /// <summary>
        /// Hier werden testweise alle Rails von Squares gelöscht und zufällig neue generiert und den Squares zugeordnet. 
        /// </summary>
        private void CreateRandomRails()
        {
            Random rand = new Random();
            foreach (SquareEditorViewModel squareViewModel in squareViewModels)
            {
                squareViewModel.Square.PlaceableOnSquare = null;
                if(rand.Next(3) == 0)
                {
                    List<RailSection> railSections = new List<RailSection>();
                    railSections.Add(new RailSection(Guid.NewGuid(), Compass.NORTH, Compass.SOUTH));
                    railSections.Add(new RailSection(Guid.NewGuid(), Compass.WEST, Compass.SOUTH));
                    railSections.Add(new RailSection(Guid.NewGuid(), Compass.EAST, Compass.WEST));
                    railSections.Add(new RailSection(Guid.NewGuid(), Compass.WEST, Compass.NORTH));
                    railSections.Add(new RailSection(Guid.NewGuid(), Compass.EAST, Compass.SOUTH));
                    railSections.Add(new RailSection(Guid.NewGuid(), Compass.EAST, Compass.NORTH));

                    List<RailSection> actualRailSection = new List<RailSection>();
                    actualRailSection.Add(railSections[rand.Next(railSections.Count)]);
                    Rail rail = new Rail(Guid.NewGuid(), squareViewModel.Square, actualRailSection);
                    squareViewModel.Square.PlaceableOnSquare = rail;
                }
            }
        }

        /// <summary>
        /// Wenn sich das PlaceableOnRail im Rail ändert wird diese Methode aufgerufen
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OnRailPropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            Rail rail = (Rail)sender;

            if (e.PropertyName == "PlaceableOnRail")
            {
                PropertyChangedExtendedEventArgs<IPlaceableOnRail> eventArgs = (PropertyChangedExtendedEventArgs<IPlaceableOnRail>)e;

                if (rail.PlaceableOnRail == null)
                {
                    IModel model = (IModel)eventArgs.OldValue;
                    CanvasEditorViewModel result = placeableOnRailCollection.Where(x => x.Id == model.Id).First();

                    if (result != null)
                    {
                        taskFactory.StartNew(() => placeableOnRailCollection.Remove(result));
                    }
                }
                else
                {
                    ViewModelFactory factory = new ViewModelFactory();
                    CanvasEditorViewModel viewModel = factory.CreateEditorViewModelForModel(rail.PlaceableOnRail, this);

                    taskFactory.StartNew(() => placeableOnRailCollection.Add(viewModel));
                }
            }

        }

        private void OnTrainstationPropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            Playertrainstation trainstation = (Playertrainstation)sender;
        }

        private void OnPublicTrainstationPropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            Publictrainstation trainstation = (Publictrainstation)sender;
        }

        /// <summary>
        /// Wenn sich eine Property in Square ändert, dann wird diese Methode aufgerufen.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void OnSquarePropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            Square square = (Square)sender;

            if (e.PropertyName == "PlaceableOnSquare")
            {
                PropertyChangedExtendedEventArgs<IPlaceableOnSquare> eventArgs = (PropertyChangedExtendedEventArgs<IPlaceableOnSquare>)e;

                if (square.PlaceableOnSquare == null)
                {
                    IModel model = (IModel)eventArgs.OldValue;
                    CanvasEditorViewModel result = placeableOnSquareCollection.Where(x => x.Id == model.Id).First();

                    if (result != null)
                    {
                        taskFactory.StartNew(() => placeableOnSquareCollection.Remove(result));
                    }
                }
                else
                {
                    ViewModelFactory factory = new ViewModelFactory();
                    CanvasEditorViewModel viewModel = factory.CreateEditorViewModelForModel(square.PlaceableOnSquare, this);

                    taskFactory.StartNew(() => placeableOnSquareCollection.Add(viewModel));

                    if (viewModel is RailEditorViewModel)
                    {
                        // Sollte es sich um ein Rail handeln, muss die OnRailPropertyChanged registiert werden und das ToolBarViewModel übergeben werden
                        RailEditorViewModel railEditorViewModel = (RailEditorViewModel)viewModel;
                        railEditorViewModel.Rail.PropertyChanged += OnRailPropertyChanged;
                        railEditorViewModel.ToolbarViewModel = toolbarViewModel;
                    }
                }
            }
        }

        /// <summary>
        /// EditorObject (Rail etc.) ausgewählt + Quicknavigation anzeigen
        /// </summary>
        public void SwitchQuickNavigationForCanvasViewModel()
        {
            // Falls ein anderes EditorCanvasViewModel angeklickt wurde
            if (_previousSelectEditorCanvasViewModel != _selectedEditorCanvasViewModel) {
                IsQuickNavigationVisible = false;
                Console.WriteLine("Neues EditorCanvasViewModel wurde angeklickt");
            }

            if (IsQuickNavigationVisible)
            {
                Console.WriteLine("Quicknavigation deaktiviert");

				// SelectedEditorCanvasViewModel muss auf null zurückgesetzt werden damit man den Scope nicht mehr auf dieses Objekt hat
	            SelectedEditorCanvasViewModel = null;
				IsQuickNavigationVisible = false;

            }
            else
            {
                Console.WriteLine("Quicknavigation aktiviert");
                IsQuickNavigationVisible = true;
            }

			if(SelectedEditorCanvasViewModel != null)
				Console.WriteLine("Selected ViewModel: " + SelectedEditorCanvasViewModel.ToString() + " / ID: " + SelectedEditorCanvasViewModel.Id);
        }

        /// <summary>
        /// Binding für MapUserControl
        /// </summary>
        private Boolean isQuickNavigationVisible = false;
        public Boolean IsQuickNavigationVisible
        {
            get
            {
                return isQuickNavigationVisible;
            }
            set
            {
                isQuickNavigationVisible = value;
                OnPropertyChanged("IsQuickNavigationVisible");
            }
        }

        /// <summary>
        /// Command für RotateRight erstellen
        /// </summary>
        private ICommand rotateRightCommand;
        public ICommand RotateRightCommand
        {
            get
            {
                if (rotateRightCommand == null)
                {
                    rotateRightCommand = new ActionCommand(param => RotateRight());
                }

                return rotateRightCommand;
            }
        }

        /// <summary>
        /// Das aktuell ausgewählte EditorCanvasViewModel nach rechts rotieren
        /// </summary>
        private void RotateRight()
        {
            SelectedEditorCanvasViewModel.RotateRight();
        }

        /// <summary>
        /// Command für RotateLeft erstellen
        /// </summary>
        private ICommand rotateLeftCommand;
        public ICommand RotateLeftCommand
        {
            get
            {
                if (rotateLeftCommand == null)
                {
                    rotateLeftCommand = new ActionCommand(param => RotateLeft());
                }
                return rotateLeftCommand;
            }
        }

        /// <summary>
        /// Das aktuell ausgewählte EditorCanvasViewModel nach links rotieren
        /// </summary>
        private void RotateLeft()
        {
            SelectedEditorCanvasViewModel.RotateLeft();
        }

        /// <summary>
        /// Command für Delete erstellen
        /// </summary>
        private ICommand deleteCommand;
        public ICommand DeleteCommand
        {
            get
            {
                if (deleteCommand == null)
                {
                    deleteCommand = new ActionCommand(param => Delete());
                }
                return deleteCommand;
            }
        }

        /// <summary>
        /// Das aktuell ausgewählte EditorCanvasViewModel löschen
        /// </summary>
        private void Delete()
        {
			if (SelectedEditorCanvasViewModel != null)
			{
				SelectedEditorCanvasViewModel.Delete();
			}
            // Quicknavigation nach dem Löschen nicht mehr anzeigen
            IsQuickNavigationVisible = false;
        }

        /// <summary>
        /// Command für Move erstellen
        /// </summary>
        private ICommand moveCommand;
        public ICommand MoveCommand
        {
            get
            {
                if (moveCommand == null)
                {
                    moveCommand = new ActionCommand(param => Move());
                }
                return moveCommand;
            }
        }

        /// <summary>
        /// Das aktuell ausgewählte CanvasViewModel verschieben
        /// </summary>
        private void Move()
        {
	        SelectedEditorCanvasViewModel.Move();

	        // Quicknavigation nach dem Verschieben nicht mehr anzeigen
            IsQuickNavigationVisible = false;
        }
    }
}
