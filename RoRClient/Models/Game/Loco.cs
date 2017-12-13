using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Klasse für die Loco, die eine Liste von Carts enthält
    /// </summary>
    class Loco : InteractiveGameObject, IPlaceableOnRail
    {
        public Loco(Guid id, Square square) : base(square)
        {
            this.id = id;
        }

        private List<Cart> carts = new List<Cart>();

        public List<Cart> Carts
        {
            get
            {
                return carts;
            }
            set
            {
                carts = value;
            }
        }
    }
}
