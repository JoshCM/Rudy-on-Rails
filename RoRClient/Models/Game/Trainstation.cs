using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    public class Trainstation : InteractiveGameObject, IPlaceableOnSquare
    {
        private List<Rail> trainstationRails;

        public Trainstation(Guid id, Square square, List<Rail> trainstationRails) : base(square)
        {
            this.id = id;
            this.trainstationRails = trainstationRails;
        }
    }
}
