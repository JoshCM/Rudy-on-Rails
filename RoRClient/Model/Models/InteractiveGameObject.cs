using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    public abstract class InteractiveGameObject : ModelBase
    {
        /// <summary>
        /// Abstrakte Klasse für alle Objekte, mit denen interagiert werden kann
        /// </summary>

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
                    NotifyPropertyChanged("Square");            
                }
            }
        }

        public InteractiveGameObject(Square square)
        {
            this.square = square;
        }
    }
}
