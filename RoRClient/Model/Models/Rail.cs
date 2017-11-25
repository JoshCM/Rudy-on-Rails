using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    class Rail : InteractiveGameObject, PlaceableOnSquare
    {
        //Klasse für Schienen, die einem Feld (Square) zugeordnet sind
        //und ein Schienenstück (= Gerade, Kurve) bzw. zwei Schienenstücke (= Kreuzung, Weiche) besitzen

        protected Square square;
        protected PlaceableOnRail placeableOnRail = null;
        protected RailSection section1;
        protected RailSection section2;

        public Rail (Square square, RailSection section)
        {
            //Konstruktor für Geraden oder Kurven
            this.square = square;
            this.section1 = section;
        }

        public Rail (Square square, RailSection section1, RailSection section2)
        {
            //Konstruktor für Kreuzungen oder Weichen
            this.square = square;
            this.section1 = section1;
            this.section2 = section2;
        }

        public void setPlaceableOnRail (PlaceableOnRail placeableOnRail)
        {
            if (placeableOnRail == null)
            {
                this.placeableOnRail = placeableOnRail;
            }
        }

    }
}
