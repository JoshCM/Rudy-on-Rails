using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    class Rail : InteractiveGameObject, PlaceableOnSquare
    {
        //Klasse für Schienen, die einem Square zugeordnet sind
        //und ein (Gerade) oder zwei (Kurve) Schienenstücke besitzen

        protected Square square;
        protected PlaceableOnRail placeableOnRail = null;
        protected RailSection section1;
        protected RailSection section2;

        public Rail (Square square, RailSection section)
        {
            //Konstruktor für gerade Schiene
            this.square = square;
            this.section1 = section;
        }

        public Rail (Square square, RailSection section1, RailSection section2)
        {
            //Konstruktor für Kurve
            this.square = square;
            this.section1 = section1;
            this.section2 = section2;
        }

        public void setPlaceableOnRail (PlaceableOnRail placeableOnRail)
        {
            if  (placeableOnRail == null)
            {
                this.placeableOnRail = placeableOnRail;
            }
        }

    }
}
