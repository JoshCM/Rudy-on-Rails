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

namespace RoRClient.ViewModels.Game
{
    public class GameMapViewModel : ViewModelBase
    {
        private GameCanvasViewModel gameCanvasViewModel;

        public GameCanvasViewModel GameCanvasViewModel
        {
            get { return gameCanvasViewModel; }
            set { gameCanvasViewModel = value; }
        }

        private ObservableCollection<GameSquareViewModel> squareViewModels =
            new ObservableCollection<GameSquareViewModel>();

        public ObservableCollection<GameSquareViewModel> SquareViewModels
        {
            get { return squareViewModels; }
        }

        private Map map;

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

        public GameMapViewModel()
        {
            map = GameSession.GetInstance().Map;
            InitSquares();
            MapWidth = map.Squares.GetLength(0) * ViewConstants.SQUARE_DIM;
            MapHeight = map.Squares.GetLength(1) * ViewConstants.SQUARE_DIM;
        }

        private void InitSquares()
        {
            foreach (Square square in map.Squares)
            {
                GameSquareViewModel squareViewModel = new GameSquareViewModel(square);
                squareViewModel.MapViewModel = this;
                squareViewModels.Add(squareViewModel);
                //square.PropertyChanged += OnSquarePropertyChanged;
            }
        }
    }
}