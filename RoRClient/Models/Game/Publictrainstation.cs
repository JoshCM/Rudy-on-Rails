using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Klasse für Publictrainstations, die einem Square zugeordnet sind
    /// und eine Liste von Rails, die zu der Trainstation gehören
    /// </summary>
    public class Publictrainstation : Trainstation
    {
        private List<Rail> trainstationRails;
        private Compass alignment;
        private Stock stock;

        public Publictrainstation(Guid id, Square square, List<Rail> trainstationRails, Compass alignment, Stock stock) : base(id, square, trainstationRails, alignment, stock)
        {
            this.id = id;
            this.trainstationRails = trainstationRails;
            this.alignment = alignment;
            this.stock = stock;
        }
    }
}