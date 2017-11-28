using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    class Square
    {
        //Klasse für ein Feld, dass auf der Map liegt und eine Position hat.
        //Auf dem Feld platzierte Objekte können auf die jeweilige Position zugreifen

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
                xPos = value;
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
                yPos = value;
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
