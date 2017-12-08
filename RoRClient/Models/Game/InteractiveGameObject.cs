using RoRClient.Models.Base;

namespace RoRClient.Models.Game
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
