using RoRClient.ViewModel.Helper;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    class Square : INotifyPropertyChanged
    {
        //Klasse für ein Feld, dass auf der Map liegt und eine Position hat.
        //Auf dem Feld platzierte Objekte können auf die jeweilige Position zugreifen

        #region Das hier später in Base Klasse für Models und/oder ViewModels
        public event PropertyChangedEventHandler PropertyChanged;

        public virtual void OnPropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            PropertyChangedEventHandler handler = PropertyChanged;
            if (handler != null)
                handler(sender, e);
        }

        protected void NotifyPropertyChanged<T>(string propertyName, T oldvalue, T newvalue)
        {
            OnPropertyChanged(this, new PropertyChangedExtendedEventArgs<T>(propertyName, oldvalue, newvalue));
        }
        #endregion

        private int posX;
        public int PosX
        {
            get
            {
                return posX;
            }
            set
            {
                if(posX != value)
                {
                    int temp = posX;
                    posX = value;
                    NotifyPropertyChanged("XPos", temp, posX);
                }
            }
        }

        private int posY;

        public int PosY
        {
            get
            {
                return posY;
            }
            set
            {
                if(posY != value)
                {
                    int temp = posY;
                    posY = value;
                    NotifyPropertyChanged("YPos", temp, posY);
                }
            }
        }

        public Square (int xPos, int yPos)
        {
            this.posX = xPos;
            this.posY = yPos;
        }

        PlaceableOnSquare placeableOnSquare = null;
        public PlaceableOnSquare PlaceableOnSquare
        {
            get
            {
                return placeableOnSquare;
            }
            set
            {
                if(placeableOnSquare != value)
                {
                    PlaceableOnSquare temp = placeableOnSquare;
                    placeableOnSquare = value;
                    NotifyPropertyChanged("PlaceableOnSquare", temp, placeableOnSquare);
                }
            }
        }
    }
}
