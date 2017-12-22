using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    class Coal : Ressource
    {

        private Square square;
        public Coal(Guid id, Square square) : base(id, square)
        {
            this.id = id;
            this.square = square;
        }
    }
}
