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
    public class MapViewModel : ViewModelBase
    {
        private TaskFactory taskFactory;
        private ToolbarViewModel toolbarViewModel;

        private CanvasViewModel previusSelectCanvasViewModel;
        public CanvasViewModel PreviousSelectedCanvasViewModel
        {
            get
            {
                return previusSelectCanvasViewModel;
            }
            set
            {
                previusSelectCanvasViewModel = value;
            }
        }

        private CanvasViewModel selectedCanvasViewModel;
        public CanvasViewModel SelectedCanvasViewModel
        {
            get
            {
                return selectedCanvasViewModel;
            }
            set
            {
                selectedCanvasViewModel = value;
            }
        }

        private Map map;

        private ObservableCollection<SquareViewModel> squareViewModels = new ObservableCollection<SquareViewModel>();
        public ObservableCollection<SquareViewModel> SquareViewModels
        {
            get
            {
                return squareViewModels;
            }
        }

        private ObservableCollection<CanvasViewModel> placeableOnSquareCollection = new ObservableCollection<CanvasViewModel>();
        public ObservableCollection<CanvasViewModel> PlaceableOnSquareCollection
        {
            get
            {
                return placeableOnSquareCollection;
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

        public MapViewModel(ToolbarViewModel toolbarViewModel)
        {
            this.toolbarViewModel = toolbarViewModel;
            taskFactory = new TaskFactory(TaskScheduler.FromCurrentSynchronizationContext());
            map = EditorSession.GetInstance().Map;
            InitSquares();
            MapWidth = map.Squares.GetLength(0) * ViewConstants.SQUARE_DIM;
            MapHeight = map.Squares.GetLength(1) * ViewConstants.SQUARE_DIM;
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
                SquareViewModel squareViewModel = new SquareViewModel(square, toolbarViewModel);
                squareViewModels.Add(squareViewModel);
                square.PropertyChanged += OnSquarePropertyChanged;

                if (square.PlaceableOnSquare != null && square.PlaceableOnSquare.GetType() == typeof(Rail))
                {
                    Rail rail = (Rail)square.PlaceableOnSquare;
                    RailViewModel railViewModel = new RailViewModel(rail);
                    placeableOnSquareCollection.Add(railViewModel);
                    rail.PropertyChanged += OnRailPropertyChanged;
                }
            }
        }

        /// <summary>
        /// Hier werden testweise alle Rails von Squares gelöscht und zufällig neue generiert und den Squares zugeordnet. 
        /// </summary>
        private void CreateRandomRails()
        {
            Random rand = new Random();
            foreach (SquareViewModel squareViewModel in squareViewModels)
            {
                squareViewModel.Square.PlaceableOnSquare = null;
                if(rand.Next(3) == 0)
                {
                    List<RailSection> railSections = new List<RailSection>();
                    railSections.Add(new RailSection(Guid.NewGuid(), RailSectionPosition.NORTH, RailSectionPosition.SOUTH));
                    railSections.Add(new RailSection(Guid.NewGuid(), RailSectionPosition.WEST, RailSectionPosition.SOUTH));
                    railSections.Add(new RailSection(Guid.NewGuid(), RailSectionPosition.EAST, RailSectionPosition.WEST));
                    railSections.Add(new RailSection(Guid.NewGuid(), RailSectionPosition.WEST, RailSectionPosition.NORTH));
                    railSections.Add(new RailSection(Guid.NewGuid(), RailSectionPosition.EAST, RailSectionPosition.SOUTH));
                    railSections.Add(new RailSection(Guid.NewGuid(), RailSectionPosition.EAST, RailSectionPosition.NORTH));

                    List<RailSection> actualRailSection = new List<RailSection>();
                    actualRailSection.Add(railSections[rand.Next(railSections.Count)]);
                    Rail rail = new Rail(Guid.NewGuid(), squareViewModel.Square, actualRailSection);
                    squareViewModel.Square.PlaceableOnSquare = rail;
                }
            }
        }

        private void OnRailPropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            Rail rail = (Rail)sender;
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
                    CanvasViewModel result = placeableOnSquareCollection.Where(x => x.Id == model.Id).First();

                    if (result != null)
                    {
                        placeableOnSquareCollection.Remove(result);
                    }
                }
                else
                {
                    ViewModelFactory factory = new ViewModelFactory();
                    CanvasViewModel viewModel = factory.CreateViewModelForModel(square.PlaceableOnSquare, this);

                    taskFactory.StartNew(() => placeableOnSquareCollection.Add(viewModel));
                }
            }
        }

        // EditorObject (Rail etc.) ausgewählt + Quicknavigation anzeigen (sollte noch umbenannt werden)
        public void SwitchQuickNavigationForCanvasViewModel()
        {
            // Falls ein anderes CanvasViewModel angeklickt wurde
            if (previusSelectCanvasViewModel != selectedCanvasViewModel) {
                IsQuickNavigationVisible = false;
                Console.WriteLine("Neues CanvasViewModel wurde angeklickt");
            }

            if (IsQuickNavigationVisible)
            {
                Console.WriteLine("Quicknavigation deaktiviert");
                IsQuickNavigationVisible = false;

            }
            else
            {
                Console.WriteLine("Quicknavigation aktiviert");
                IsQuickNavigationVisible = true;
            }

            Console.WriteLine("Selected ViewModel: " + SelectedCanvasViewModel.ToString() + " / ID: " + SelectedCanvasViewModel.Id);
        }

        // Binding für MapUserControl
        private Boolean isQuickNavigationVisible;
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


    }
}
