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

            GameSession.GetInstance().PropertyChanged += OnLocoAddedInGameSession;
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


        private ObservableCollection<CanvasGameViewModel> placeableOnRailCollection = new ObservableCollection<CanvasGameViewModel>();
        public ObservableCollection<CanvasGameViewModel> PlaceableOnRailCollection
        {
            get
            {
                return placeableOnRailCollection;
            }
        }

        private ObservableCollection<CanvasGameViewModel> locos = new ObservableCollection<CanvasGameViewModel>();
        public ObservableCollection<CanvasGameViewModel> Locos
        {
            get
            {
                return locos;
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

                    if (viewModel is RailGameViewModel)
                    { 
                        // Sollte es sich um eine Rail handeln, muss die OnRailPropertyChanged registiert werden und das ToolBarViewModel übergeben werden
                        RailGameViewModel railGameViewModel = (RailGameViewModel)viewModel;
                        railGameViewModel.Rail.PropertyChanged += OnRailPropertyChanged;
                    }
                }
            }
        }

        private void OnRailPropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            Rail rail = (Rail)sender;

            if (e.PropertyName == "PlaceableOnRail")
            {
                PropertyChangedExtendedEventArgs<IPlaceableOnRail> eventArgs = (PropertyChangedExtendedEventArgs<IPlaceableOnRail>)e;

                if (rail.PlaceableOnRail == null)
                {
                    IModel model = (IModel)eventArgs.OldValue;
                    CanvasGameViewModel result = placeableOnRailCollection.Where(x => x.Id == model.Id).First();

                    if (result != null)
                    {
                        taskFactory.StartNew(() => placeableOnRailCollection.Remove(result));
                    }
                }
                else
                {
                    IModel model = (IModel)eventArgs.NewValue;
                    if (model.GetType() == typeof(Cart))
                    {
                        ViewModelFactory factory = new ViewModelFactory();
                        Cart cart = eventArgs.NewValue as Cart;
                        CartGameViewModel cartGameViewModel = new CartGameViewModel(cart);
                        taskFactory.StartNew(() => locos.Add(cartGameViewModel));
                    }
                    else
                    {
                        ViewModelFactory factory = new ViewModelFactory();
                        CanvasGameViewModel viewModel = factory.CreateGameViewModelForModel(rail.PlaceableOnRail, this);

                        taskFactory.StartNew(() => placeableOnRailCollection.Add(viewModel));

                    }
              
                }
            }
        }

        private void OnLocoAddedInGameSession(object sender, PropertyChangedEventArgs e)
        {
            if(e.PropertyName == "Locos")
            {
                PropertyChangedExtendedEventArgs<Loco> eventArgs = (PropertyChangedExtendedEventArgs<Loco>)e;
                Loco loco = eventArgs.NewValue;
                if(loco is PlayerLoco)
                {
                    PlayerLocoGameViewModel locoGameViewModel = new PlayerLocoGameViewModel(loco);
                    taskFactory.StartNew(() => locos.Add(locoGameViewModel));
                }
                else
                {
                    GhostLocoGameViewModel locoGameViewModel = new GhostLocoGameViewModel((GhostLoco)loco);
                    taskFactory.StartNew(() => locos.Add(locoGameViewModel));
                }

                loco.PropertyChanged += OnCartAddedInLoco;
            }
        }

        private void OnCartAddedInLoco(object sender, PropertyChangedEventArgs e)
        {
            if (e.PropertyName == "Carts")
            {
                PropertyChangedExtendedEventArgs<Cart> eventArgs = (PropertyChangedExtendedEventArgs<Cart>)e;
                Cart cart = eventArgs.NewValue;
                CartGameViewModel cartGameViewModel = new CartGameViewModel(cart);
                taskFactory.StartNew(() => locos.Add(cartGameViewModel));
            }
        }
    }
}