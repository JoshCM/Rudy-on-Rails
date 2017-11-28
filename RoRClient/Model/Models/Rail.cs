using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    class Rail : InteractiveGameObject, IPlaceableOnSquare
    {
        //Klasse für Schienen, die einem Feld (Square) zugeordnet sind
        //und ein Schienenstück (= Gerade, Kurve) bzw. zwei Schienenstücke (= Kreuzung, Weiche) besitzen

        // ToDo: Das mit den IDs müssen ALLE Models bekommen, die durchs Netzwerk geschickt werden. Sonst doof. Alles ganz doof.
        
        protected IPlaceableOnRail placeableOnRail = null;
        protected RailSection section1;
        protected RailSection section2;

        public Rail (Square square, RailSection section) : base(square) 
        {
            //Konstruktor für Geraden oder Kurven
            this.section1 = section;
        }

        public Rail (Square square, RailSection section1, RailSection section2) : base(square)
        {
            //Konstruktor für Kreuzungen oder Weichen
            this.section1 = section1;
            this.section2 = section2;
        }

        public void setPlaceableOnRail (IPlaceableOnRail placeableOnRail)
        {
            if (placeableOnRail == null)
            {
                this.placeableOnRail = placeableOnRail;
            }
        }

    }
}
