using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModel
{
    class EditorViewModel : INotifyPropertyChanged
    {
        private const int SQUARE_DIM = 50;

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

        public EditorViewModel()
        {
            DummyMap map = new DummyMap(100);
            foreach(DummySquare square in map.Squares)
            {
                squares.Add(square);
            }

            MapWidth = map.Squares.GetLength(0) * SQUARE_DIM;
            MapHeight = map.Squares.GetLength(1) * SQUARE_DIM;
        }

        private ObservableCollection<DummySquare> squares = new ObservableCollection<DummySquare>();
        public ObservableCollection<DummySquare> Squares
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
                if(mapHeight != value)
                {
                    mapHeight = value;
                    OnPropertyChanged("MapHeight");
                }
            }
        }
    }
}
