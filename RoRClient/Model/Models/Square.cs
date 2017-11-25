using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    class Square
    {
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
