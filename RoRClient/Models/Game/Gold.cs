using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    class Gold : Ressource
    {

        private Square square;
        public Gold(Guid id, Square square) : base(id, square)
        {

            this.square = square;
            this.id = id;
        }
    }
}
