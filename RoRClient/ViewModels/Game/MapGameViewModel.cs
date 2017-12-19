using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Shapes;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Commands;
using Point = System.Windows.Point;
using System.ComponentModel;
using RoRClient.ViewModels.Helper;
using RoRClient.Models.Base;

namespace RoRClient.ViewModels.Game
{
    public class MapGameViewModel : ViewModelBase
    {
        private CanvasGameViewModel gameCanvasViewModel;
        private TaskFactory taskFactory;

        public MapGameViewModel()
        {
            taskFactory = new TaskFactory(TaskScheduler.FromCurrentSynchronizationContext());
            map = GameSession.GetInstance().Map;
            InitSquares();

            //TO-DO: nur zum Testen
            //CreateRandomRails();

            MapWidth = map.Squares.GetLength(0) * ViewConstants.SQUARE_DIM;
            MapHeight = map.Squares.GetLength(1) * ViewConstants.SQUARE_DIM;
        }

        public CanvasGameViewModel GameCanvasViewModel
        {
            get { return gameCanvasViewModel; }
            set { gameCanvasViewModel = value; }
        }

        private ObservableCollection<SquareGameViewModel> squareViewModels =
            new ObservableCollection<SquareGameViewModel>();

        public ObservableCollection<SquareGameViewModel> SquareViewModels
        {
            get { return squareViewModels; }
        }

        private Map map;


        private ObservableCollection<CanvasGameViewModel> placeableOnSquareCollection = new ObservableCollection<CanvasGameViewModel>();
        public ObservableCollection<CanvasGameViewModel> PlaceableOnSquareCollection
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

        private void InitSquares()
        {
            foreach (Square square in map.Squares)
            {
                SquareGameViewModel squareViewModel = new SquareGameViewModel(square);
                squareViewModel.MapViewModel = this;
                squareViewModels.Add(squareViewModel);
                square.PropertyChanged += OnSquarePropertyChanged;
            }
        }

        private void OnSquarePropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            Square square = (Square)sender;

            if (e.PropertyName == "PlaceableOnSquare")
            {
                PropertyChangedExtendedEventArgs<IPlaceableOnSquare> eventArgs = (PropertyChangedExtendedEventArgs<IPlaceableOnSquare>)e;

                if (square.PlaceableOnSquare == null)
                {
                    IModel model = (IModel)eventArgs.OldValue;
                    CanvasGameViewModel result = placeableOnSquareCollection.Where(x => x.Id == model.Id).First();

                    if (result != null)
                    {
                        taskFactory.StartNew(() => placeableOnSquareCollection.Remove(result));
                    }
                }
                else
                {
                    ViewModelFactory factory = new ViewModelFactory();
                    CanvasGameViewModel viewModel = factory.CreateGameViewModelForModel(square.PlaceableOnSquare, this);

                    taskFactory.StartNew(() => placeableOnSquareCollection.Add(viewModel));
                }
            }
        }
        private void CreateRandomRails()
        {
            Random rand = new Random();
            foreach (SquareGameViewModel squareViewModel in squareViewModels)
            {
                squareViewModel.Square.PlaceableOnSquare = null;
                if (rand.Next(3) == 0)
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
    }
}