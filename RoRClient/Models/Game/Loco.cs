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
    public class Loco : InteractiveGameObject, IPlaceableOnRail
    {
        private int speed;

        public Loco(Guid id, Square square) : base(square)
        {
            this.id = id;
        }

        public int Speed
        {
            get
            {
                return speed;
            }
            set
            {
                if(speed != value)
                {
                    speed = value;
                    NotifyPropertyChanged("Speed");
                }
            }
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

        public Cart getCartById(Guid cartId)
        {
            foreach(Cart c in Carts){
                if (c.Id.Equals(cartId))
                {
                    return c;
                }
            }

            return null;

        }
    }
}
