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

        public Trainstation(Guid id, Square square, List<Rail> trainstationRails) : base(square)
        {
            this.id = id;
            this.trainstationRails = trainstationRails;
        }

        public List<Rail> TrainstationRails
        {
            get
            {
                return trainstationRails;
            }
        }
    }
}
