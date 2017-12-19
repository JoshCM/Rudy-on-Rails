using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Klasse für Trainstations die einem Square zugeordnet sind
    /// und eine Liste von Rails die zu der Trainstation gehören
    /// </summary>
    public class Trainstation : InteractiveGameObject, IPlaceableOnSquare
    {
        private List<Rail> trainstationRails;
        private Compass alignment;
        public Trainstation(Guid id, Square square, List<Rail> trainstationRails, Compass alignment) : base(square)
        {
            this.id = id;
            this.trainstationRails = trainstationRails;
            this.alignment = alignment;
        }

        public List<Rail> TrainstationRails
        {
            get
            {
                return trainstationRails;
            }
        }

        public Compass Alignment
        {
            get
            {
                return alignment;
            }
            set
            {
                alignment = value;
                NotifyPropertyChanged("Alignment");
            }
        }
    }
}
