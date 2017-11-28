using RoRClient.Model.Models;
using RoRClient.ViewModel.Helper;
using System;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Windows.Input;
using System.Linq;

namespace RoRClient.ViewModel
{
    class EditorViewModel : BaseViewModel
    {
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
            Map map = new Map();
            foreach(Square square in map.Squares)
            {
                squares.Add(square);
                square.PropertyChanged += OnSquarePropertyChanged;

                if(square.PlaceableOnSquare != null && square.PlaceableOnSquare.GetType() == typeof(Rail))
                {
                    Rail rail = (Rail)square.PlaceableOnSquare;
                    RailViewModel railViewModel = new RailViewModel(rail);
                    placeableOnSquareCollection.Add(railViewModel);
                    rail.PropertyChanged += OnRailPropertyChanged;                    
                }
            }

            MapWidth = map.Squares.GetLength(0) * ViewConstants.SQUARE_DIM;
            MapHeight = map.Squares.GetLength(1) * ViewConstants.SQUARE_DIM;
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

        private void CreateRandomRails()
        {
            Random rand = new Random();
            foreach (Square square in Squares)
            {
                square.PlaceableOnSquare = null;
                if(rand.Next(3) == 0)
                {
                    Rail rail = new Rail(square, new RailSection(RailSectionPosition.NORTH, RailSectionPosition.SOUTH));
                    square.PlaceableOnSquare = rail;
                }
            }
        }

        private void OnRailPropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            Rail rail = (Rail)sender;

            
        }

        private void OnSquarePropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            Square square = (Square)sender;

            // Erstmal Annahme: Ist immer Rail
            if (e.PropertyName == "PlaceableOnSquare")
            {
                PropertyChangedExtendedEventArgs<PlaceableOnSquare> eventArgs = (PropertyChangedExtendedEventArgs<PlaceableOnSquare>)e;

                if (square.PlaceableOnSquare == null)
                {
                    Rail rail = (Rail)eventArgs.OldValue;
                    CanvasViewModel result = placeableOnSquareCollection.Where(x => x.Id == rail.Id).First();

                    if (result != null)
                    {
                        placeableOnSquareCollection.Remove(result);
                    }
                }
                else
                {
                    RailViewModel railViewModel = new RailViewModel((Rail)square.PlaceableOnSquare);
                    placeableOnSquareCollection.Add(railViewModel);
                }
            }
        }
    }
}
