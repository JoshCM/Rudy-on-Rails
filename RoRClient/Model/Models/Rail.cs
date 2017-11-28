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

        // ToDo: Das mit den IDs müssen ALLE Models bekommen, die durchs Netzwerk geschickt werden. Sonst doof. Alles ganz doof.
        private Guid id;
        public Guid Id
        {
            get
            {
                return id;
            }
        }
        
        protected PlaceableOnRail placeableOnRail = null;
        protected RailSection section1;
        protected RailSection section2;

        public Rail (Square square, RailSection section) : base(square)
        {
            //Konstruktor für Geraden oder Kurven
            this.section1 = section;

            // ToDo: Refactor
            id = Guid.NewGuid();
        }

        public Rail (Square square, RailSection section1, RailSection section2) : base(square)
        {
            //Konstruktor für Kreuzungen oder Weichen
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
