using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModel
{
    class DummySquare : INotifyPropertyChanged
    {

        #region Das hier später in Base Klasse für Models
        public event PropertyChangedEventHandler PropertyChanged;

        public virtual void OnPropertyChanged(string propertyName)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }
        #endregion

        public DummySquare(int xPos, int yPos)
        {
            this.xPos = xPos;
            this.yPos = yPos;
        }

        private DummyRail rail;
        public DummyRail Rail
        {
            get { return rail; }
            set
            {
                if(rail != value)
                {
                    rail = value;
                    OnPropertyChanged("Rail");
                }
            }
        }

        private int xPos;
        public int XPos
        {
            get { return xPos; }
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
            get { return yPos; }
            set
            {
                if (yPos != value)
                {
                    yPos = value;
                    OnPropertyChanged("YPos");
                }
            }
        }
    }
}
