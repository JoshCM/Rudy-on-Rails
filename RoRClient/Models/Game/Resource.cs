using RoRClient.Models.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Oberklasse für Ressourcen
    /// </summary>
    public abstract class Resource : InteractiveGameObject
    {
        private Square square;
        public Resource(Guid id, Square square) : base(square)
        {
            this.id = id;
            this.square = square;
        }
    }
}
