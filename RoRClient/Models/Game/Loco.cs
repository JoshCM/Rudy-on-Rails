using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Klasse für die Loco, die eine Liste von Carts enthält
    /// </summary>
    public abstract class Loco : InteractiveGameObject, IPlaceableOnRail
    {
        private int speed;
        private Compass drivingDirection;

        public Loco(Guid id, Compass drivingDirection, Square square) : base(square)
        {
            this.id = id;
            this.drivingDirection = drivingDirection;
        }

        public void AddCart(Cart cart)
        {
            carts.Add(cart);
            NotifyPropertyChanged("Carts", null, cart);
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
     
        public Compass DrivingDirection
        {
            get
            {
                return drivingDirection;
            }
            set
            {
                if(drivingDirection != value)
                {
                    drivingDirection = value;
                    NotifyPropertyChanged("DrivingDirection");
                }
            }
        }

        private ObservableCollection<Cart> carts = new ObservableCollection<Cart>();
        public ObservableCollection<Cart> Carts
        {
            get
            {
                return carts;
            }
        }

        public Cart GetCartById(Guid cartId)
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
