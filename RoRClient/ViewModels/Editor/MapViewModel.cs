using RoRClient.Models.Game;
using RoRClient.ViewModels.Helper;
using RoRClient.Models.Editor;
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

namespace RoRClient.ViewModels.Editor
{
    /// <summary>
    /// Hält alle ViewModels die placeableOnSquare sind, sowie die Squares der Map
    /// und momentan noch die Map an sich
    /// </summary>
    class MapViewModel : ViewModelBase
    {
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

        public MapViewModel()
        {
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
                SquareViewModel squareViewModel = new SquareViewModel(square);
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

        private ICommand createRandomRailsCommand;
        public ICommand CreateRandomRailsCommand
        {
            get
            {
                if (createRandomRailsCommand == null)
                {
                    createRandomRailsCommand = new ActionCommand(param => ChangeRailSectionsFromActiveRails());
                }
                return createRandomRailsCommand;
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
                    railSections.Add(new RailSection(RailSectionPosition.NORTH, RailSectionPosition.SOUTH));
                    railSections.Add(new RailSection(RailSectionPosition.WEST, RailSectionPosition.SOUTH));
                    railSections.Add(new RailSection(RailSectionPosition.EAST, RailSectionPosition.WEST));
                    railSections.Add(new RailSection(RailSectionPosition.WEST, RailSectionPosition.NORTH));
                    railSections.Add(new RailSection(RailSectionPosition.EAST, RailSectionPosition.SOUTH));
                    railSections.Add(new RailSection(RailSectionPosition.EAST, RailSectionPosition.NORTH));

                    Rail rail = new Rail(Guid.NewGuid(), squareViewModel.Square, railSections[rand.Next(railSections.Count)]);
                    squareViewModel.Square.PlaceableOnSquare = rail;
                }
            }
        }

        private void ChangeRailSectionsFromActiveRails()
        {
            Random rand = new Random();
            foreach (SquareViewModel squareViewModel in squareViewModels)
            {
                if(squareViewModel.Square.PlaceableOnSquare != null)
                {
                    List<RailSection> railSections = new List<RailSection>();
                    railSections.Add(new RailSection(RailSectionPosition.NORTH, RailSectionPosition.SOUTH));
                    railSections.Add(new RailSection(RailSectionPosition.WEST, RailSectionPosition.SOUTH));
                    railSections.Add(new RailSection(RailSectionPosition.EAST, RailSectionPosition.WEST));
                    railSections.Add(new RailSection(RailSectionPosition.WEST, RailSectionPosition.NORTH));
                    railSections.Add(new RailSection(RailSectionPosition.EAST, RailSectionPosition.SOUTH));
                    railSections.Add(new RailSection(RailSectionPosition.EAST, RailSectionPosition.NORTH));

                    Rail rail = (Rail)squareViewModel.Square.PlaceableOnSquare;
                    rail.Section1 = railSections[rand.Next(railSections.Count)];
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
                    CanvasViewModel viewModel = factory.CreateViewModelForModel(square.PlaceableOnSquare);

                    // ToDo: Das muss doch auch ohne den Dispatcher gehen...
                    Application.Current.Dispatcher.BeginInvoke(DispatcherPriority.Background, new Action(() => placeableOnSquareCollection.Add(viewModel)));
                }
            }
        }
    }
}
