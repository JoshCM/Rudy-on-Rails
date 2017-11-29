using RoRClient.ViewModel.Helper;
using System;
using System.Collections.Generic;
using System.ComponentModel;
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

        #region Property Changed 
        public event PropertyChangedEventHandler PropertyChanged;

        public virtual void OnPropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            PropertyChangedEventHandler handler = PropertyChanged;
            if (handler != null)
                handler(sender, e);
        }

        protected void NotifyPropertyChanged<T>(string propertyName, T oldvalue, T newvalue)
        {
            OnPropertyChanged(this, new PropertyChangedExtendedEventArgs<T>(propertyName, oldvalue, newvalue));
        }
        #endregion

        protected IPlaceableOnRail placeableOnRail = null;
        private RailSection section1;
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

        private RailSection section2;
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

        public Rail(Square square, RailSection section) : base(square)
        {
            //Konstruktor für Geraden oder Kurven
            this.section1 = section;
        }

        public Rail(Square square, RailSection section1, RailSection section2) : base(square)
        {
            //Konstruktor für Kreuzungen oder Weichen
            this.section1 = section1;
            this.section2 = section2;
        }

        public void setPlaceableOnRail(IPlaceableOnRail placeableOnRail)
        {
            if (placeableOnRail == null)
            {
                this.placeableOnRail = placeableOnRail;
            }
        }

    }
}
