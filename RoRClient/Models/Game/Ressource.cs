using RoRClient.Models.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    public abstract class Ressource : InteractiveGameObject
    {
        private Square square;
        public Ressource(Guid id, Square square) : base(square)
        {
            this.id = id;
            this.square = square;
        }
    }
}
