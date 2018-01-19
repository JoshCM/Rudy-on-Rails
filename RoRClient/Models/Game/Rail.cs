using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Klasse für Schienen, die einem Feld (Square) zugeordnet sind
    /// und ein Schienenstück (Gerade, Kurve) bzw. zwei Schienenstücke (Kreuzung, Weiche) besitzen
    /// </summary>
    public class Rail : InteractiveGameObject, IPlaceableOnSquare
    {
        protected IPlaceableOnRail placeableOnRail = null;
        private Square square;
        private Guid trainstationId;
        private ObservableCollection<RailSection> railSections = new ObservableCollection<RailSection>();
        private Signals signals;
        private Sensor sensor;
        private Boolean sensorActive = false;

        public Guid TrainstationId
        {
            get { return trainstationId; }
        }

        /// <summary>
        /// Konstruktor für Geraden oder Kurven
        /// </summary>
        /// <param name="square">Das Square auf dem die Rail sein soll</param>
        /// <param name="section">Die Section der Rail</param>
        public Rail(Guid id, Square square, List<RailSection> railSections) : base(square)
        {
            this.id = id;

            foreach (RailSection section in railSections)
            {
                this.railSections.Add(section);
            }

            this.square = square;
            Signals = new Signals(Guid.Empty);
        }

        /// <summary>
        /// Konstruktor für Geraden oder Kurven mit TrainstationId
        /// </summary>
        /// <param name="id"></param>
        /// <param name="square"></param>
        /// <param name="railSections"></param>
        /// <param name="trainstationId"></param>
        public Rail(Guid id, Square square, List<RailSection> railSections, Guid trainstationId) : base(square)
        {
            this.id = id;

            foreach (RailSection section in railSections)
            {
                this.railSections.Add(section);
            }

            this.square = square;
            this.trainstationId = trainstationId;

            // hier, weil base und this hintereinander nicht so klappen will
            Signals = new Signals(Guid.Empty);
        }

        public void AddRailSection(RailSection railSection)
        {
            railSections.Add(railSection);
            NotifyPropertyChanged("RailSections");
        }

        public Signals Signals
        {
            get
            {
                return signals;
            }
            set
            {
                if(signals != value)
                {
                    signals = value;
                    NotifyPropertyChanged("Signals");
                }
            }
        }

        public Sensor Sensor
        {
            get
            {
                return sensor;
            }
            set
            {
                sensor = value;
                NotifyPropertyChanged("Sensor");
            }
        }

        public Boolean SensorActive
        {
            get
            {
                return sensorActive;
            }

            set
            {
                sensorActive = value;
                NotifyPropertyChanged("SensorActive");
            }
        }

        public void ActivateSensor()
        {
            Sensor = new Sensor(Id);
            SensorActive = true;
        }

        #region Properties
        public ObservableCollection<RailSection> RailSections
        {
            get
            {
                return railSections;
            }
        }

        public IPlaceableOnRail PlaceableOnRail
        {
            get
            {
                return placeableOnRail;
            }
            set
            {
                if (placeableOnRail != value)
                {
                    IPlaceableOnRail temp = placeableOnRail;
                    placeableOnRail = value;
                    NotifyPropertyChanged("PlaceableOnRail", temp, placeableOnRail);
                }
            }
        }
        #endregion
    }
}
