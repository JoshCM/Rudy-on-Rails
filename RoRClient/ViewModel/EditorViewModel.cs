using RoRClient.Model.Models;
using RoRClient.ViewModel.Helper;
using System;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Windows.Input;

namespace RoRClient.ViewModel
{
    class EditorViewModel : INotifyPropertyChanged
    {
        #region Das hier später in einer Base Klasse
        public event PropertyChangedEventHandler PropertyChanged;

        public virtual void OnPropertyChanged(string propertyName)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }
        #endregion

        private ObservableCollection<Square> squares = new ObservableCollection<Square>();
        public ObservableCollection<Square> Squares
        {
            get
            {
                return squares;
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
            }

            MapWidth = map.Squares.GetLength(0) * ViewConstants.SQUARE_DIM;
            MapHeight = map.Squares.GetLength(1) * ViewConstants.SQUARE_DIM;
        }

        /*
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
            foreach Square square in Squares)
            {
                square.Rail = null;

                if (rand.Next(2) == 0)
                {
                    square.Rail = new DummyRail();
                }
            }
        }
        */
    }
}
