using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    class Square
    {
        //Klasse für ein Feld auf der Map mit einer Position,
        //auf die darauf platzierbare Objekte zugreifen können

        PlaceableOnSquare placeableOnSquare = null;
        int xIndex { set; get; }
        int yIndex { set; get; }

        public Square (int xIndex, int yIndex)
        {
            this.xIndex = xIndex;
            this.yIndex = yIndex;
        }

        public void setPlaceable (PlaceableOnSquare placeableOnSquare)
        {
            if (placeableOnSquare == null)
            {
                this.placeableOnSquare = placeableOnSquare;
            }
        }
    }
}
