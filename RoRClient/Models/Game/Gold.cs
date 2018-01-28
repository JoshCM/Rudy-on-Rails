using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Klasse für Ressource Gold
    /// </summary>
    public class Gold : Resource, IPlaceableOnSquare
    {
        public Gold(Guid id, Square square) : base(id, square)
        {
            this.id = id;
        }
    }
}
