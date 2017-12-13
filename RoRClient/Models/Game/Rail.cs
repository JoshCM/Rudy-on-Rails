using System;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Klasse für Schienen, die einem Feld (Square) zugeordnet sind
    /// und ein Schienenstück (Gerade, Kurve) bzw. zwei Schienenstücke (Kreuzung, Weiche) besitzen
    /// </summary>
    public class Rail : InteractiveGameObject, IPlaceableOnSquare
    {
        protected IPlaceableOnRail placeableOnRail = null;
        private RailSection section1;
        private RailSection section2;
        public RailSection Section1
        {
            get
            {
                return section1;
            }
            set
            {
                if (section1 != value)
                {
                    RailSection temp = section1;
                    section1 = value;
                    NotifyPropertyChanged("Section1", temp, section1);
                }
            }
        }

        public RailSection Section2
        {
            get
            {
                return section2;
            }
            set
            {
                if (section2 != value)
                {
                    RailSection temp = section2;
                    section2 = value;
                    NotifyPropertyChanged("Section2", temp, section2);
                }
            }
        }

        /// <summary>
        /// Konstruktor für Geraden oder Kurven
        /// </summary>
        /// <param name="square">Das Square auf dem die Rail sein soll</param>
        /// <param name="section">Die Section der Rail</param>
        public Rail(Guid id, Square square, RailSection section) : base(square)
        {
            this.id = id;
            this.section1 = section;
        }

        /// <summary>
        /// Konstruktor für Kreuzungen oder Weichen
        /// </summary>
        /// <param name="square">Das Square auf dem die Rail sein soll</param>
        /// <param name="section1">Die erste RailSection</param>
        /// <param name="section2">Die zweite RailSection</param>
        public Rail(Square square, RailSection section1, RailSection section2) : base(square)
        {
            this.section1 = section1;
            this.section2 = section2;
        }

        /// <summary>
        /// Setzt ein PlaceableOnSquare auf diese Rail
        /// </summary>
        /// <param name="placeableOnRail"></param>
        public void setPlaceableOnRail(IPlaceableOnRail placeableOnRail)
        {
            if (placeableOnRail == null)
            {
                this.placeableOnRail = placeableOnRail;
            }
        }

    }
}
