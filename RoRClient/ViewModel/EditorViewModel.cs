using RoRClient.Model.Models;
using RoRClient.ViewModel.Helper;
using System;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Windows.Input;
using System.Linq;
using System.Collections.Generic;

namespace RoRClient.ViewModel
{
    /// <summary>
    /// Hält alle ViewModels die placeableOnSquare sind, sowie die Squares der Map
    /// und momentan noch die Map an sich
    /// </summary>
    class EditorViewModel : ViewModelBase
    {
        private Map testMap;

        private ObservableCollection<Square> squares = new ObservableCollection<Square>();
        public ObservableCollection<Square> Squares
        {
            get
            {
                return squares;
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

        public EditorViewModel()
        {
            testMap = GenerateTestMap();
            MapWidth = testMap.Squares.GetLength(0) * ViewConstants.SQUARE_DIM;
            MapHeight = testMap.Squares.GetLength(1) * ViewConstants.SQUARE_DIM;
        }

        /// <summary>
        /// Erstellt eine Map mit mehreren Rails, die zurzeit zu Testzwecken verwendet wird.
        /// Später wird die Map dann über den Server geschickt.
        /// </summary>
        /// <returns>TestMap</returns>
        private Map GenerateTestMap()
        {
            Map map = new Map();
            foreach (Square square in map.Squares)
            {
                squares.Add(square);
                square.PropertyChanged += OnSquarePropertyChanged;

                if (square.PlaceableOnSquare != null && square.PlaceableOnSquare.GetType() == typeof(Rail))
                {
                    Rail rail = (Rail)square.PlaceableOnSquare;
                    RailViewModel railViewModel = new RailViewModel(rail);
                    placeableOnSquareCollection.Add(railViewModel);
                    rail.PropertyChanged += OnRailPropertyChanged;
                }
            }

            return map;
        }

        private ICommand createRandomRailsCommand;
        public ICommand CreateRandomRailsCommand
        {
            get
            {
                if (createRandomRailsCommand == null)
                {
                    createRandomRailsCommand = new ActionCommand(param => CreateRandomRails());
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
            foreach (Square square in Squares)
            {
                square.PlaceableOnSquare = null;
                if(rand.Next(3) == 0)
                {
                    List<RailSection> railSections = new List<RailSection>();
                    railSections.Add(new RailSection(RailSectionPosition.NORTH, RailSectionPosition.SOUTH));
                    railSections.Add(new RailSection(RailSectionPosition.WEST, RailSectionPosition.SOUTH));
                    railSections.Add(new RailSection(RailSectionPosition.EAST, RailSectionPosition.WEST));
                    railSections.Add(new RailSection(RailSectionPosition.WEST, RailSectionPosition.NORTH));
                    railSections.Add(new RailSection(RailSectionPosition.EAST, RailSectionPosition.SOUTH));
                    railSections.Add(new RailSection(RailSectionPosition.EAST, RailSectionPosition.NORTH));

                    Rail rail = new Rail(square, railSections[rand.Next(railSections.Count)]);
                    square.PlaceableOnSquare = rail;
                }
            }
        }

        private void OnRailPropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            Rail rail = (Rail)sender;

            // ToDo
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
                    placeableOnSquareCollection.Add(viewModel);
                }
            }
        }
    }
}
