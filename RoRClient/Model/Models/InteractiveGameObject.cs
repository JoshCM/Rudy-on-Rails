using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    public abstract class InteractiveGameObject : ModelBase, INotifyPropertyChanged
    {
        //Abstrakte Klasse für alle Objekte, mit denen interagiert werden kann

        public event PropertyChangedEventHandler PropertyChanged;
        public virtual void OnPropertyChanged(string propertyName)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }

        private Square square;
        public Square Square
        {
            get
            {
                return square;
            }
            set
            {
                if(square != value)
                {
                    square = value;
                    OnPropertyChanged("Square");            
                }
            }
        }

        public InteractiveGameObject(Square square)
        {
            this.square = square;
        }
    }
}
