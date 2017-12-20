using RoRClient.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Klasse für einen Cart, der einer Schiene zugeordnet ist
    /// Der Cart besitzt mehrere Container (wird erst später implementiert)
    /// </summary>
    public class Cart : InteractiveGameObject, IPlaceableOnRail
    {
        public Cart(Guid id, Square square) : base(square)
        {
            this.id = id;
        }
    }
}
