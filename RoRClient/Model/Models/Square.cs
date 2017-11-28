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

        public virtual void OnPropertyChanged(string propertyName)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }
        #endregion

        PlaceableOnSquare placeableOnSquare = null;
        private int xPos;
        public int XPos
        {
            get
            {
                return xPos;
            }
            set
            {
                if(xPos != value)
                {
                    xPos = value;
                    OnPropertyChanged("XPos");
                }
            }
        }

        private int yPos;

        public int YPos
        {
            get
            {
                return yPos;
            }
            set
            {
                if(yPos != value)
                {
                    yPos = value;
                    OnPropertyChanged("YPos");
                }
            }
        }

        public Square (int xPos, int yPos)
        {
            this.xPos = xPos;
            this.yPos = yPos;
        }

        public void SetPlaceable (PlaceableOnSquare placeableOnSquare)
        {
            if (placeableOnSquare == null)
            {
                this.placeableOnSquare = placeableOnSquare;
            }
        }
    }
}
