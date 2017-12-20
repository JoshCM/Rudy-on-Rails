
using RoRClient.Models.Base;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Klasse für ein Feld, dass auf der Map liegt und eine Position hat.
    /// Auf dem Feld platzierte Objekte können auf die jeweilige Position zugreifen
    /// </summary>
    public class Square : ModelBase
    {
        protected IPlaceableOnSquare placeableOnSquare = null;
        private int posX;
        private int posY;

        public Square() : base()
        {

        }

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

        public IPlaceableOnSquare PlaceableOnSquare
        {
            get
            {
                return placeableOnSquare;
            }
            set
            {
                if(placeableOnSquare != value)
                {
                    IPlaceableOnSquare temp = placeableOnSquare;
                    placeableOnSquare = value;
                    NotifyPropertyChanged("PlaceableOnSquare", temp, placeableOnSquare);
                }
            }
        }
    }
}
