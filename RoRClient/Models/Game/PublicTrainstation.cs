using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    class PublicTrainstation : Trainstation, IPlaceableOnSquare
    {
        private List<Rail> trainstationRails;
        private Compass alignment;
        private Stock stock;

        public PublicTrainstation(Guid id, Square square, List<Rail> trainstationRails, Compass alignment, Stock stock) : base(id, square, trainstationRails, alignment, stock)
        {
            this.id = id;
            this.trainstationRails = trainstationRails;
            this.alignment = alignment;
            this.stock = stock;
        }
    }
}
