using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Klasse für Ressource Kohle
    /// </summary>
    public class Coal : Resource, IPlaceableOnSquare
    {
        public Coal(Guid id, Square square) : base(id, square)
        {
            this.id = id;
        }
    }
}
