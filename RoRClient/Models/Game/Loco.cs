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
        private Guid playerId;
        private string changeSmokeVisibility = "Hidden";
        private string changeExplosionVisibility = "Hidden";

        public Loco(Guid id, Guid playerId, Compass drivingDirection, Square square) : base(square)
        {
            this.id = id;
            this.drivingDirection = drivingDirection;
            this.playerId = playerId;
        }

        public void RemoveCartsExceptInitial()
        {
            for (int i = 1; i < this.carts.Count; i++)
            {
                carts.RemoveAt(i);
            }
            NotifyPropertyChanged("Carts");
        }

        public void AddCart(Cart cart)
        {
            carts.Add(cart);
            NotifyPropertyChanged("Carts", null, cart);
        }


        public String ChangeSmokeVisibility
        {
            get
            {
                return changeSmokeVisibility;
            }
            set
            {
                changeSmokeVisibility = value;
            }

        }

        public void UpdateSmokeVisibility(String newVisibility)
        {
            changeSmokeVisibility = newVisibility;
            NotifyPropertyChanged("ChangeSmokeVisibility", this.changeSmokeVisibility, newVisibility);
        }

        public String ChangeExplosionVisibility
        {
            get
            {
                Console.WriteLine("should get " + changeExplosionVisibility);
                return changeExplosionVisibility;
            }
            set
            {
                Console.WriteLine("should set " + changeExplosionVisibility);
                changeExplosionVisibility = value;
            }

        }

        public void UpdateExplosionVisibility(String newVisibility)
        {
            Console.WriteLine("should update " + newVisibility);
            changeExplosionVisibility = newVisibility;
            NotifyPropertyChanged("ChangeExplosionVisibility", this.changeExplosionVisibility, newVisibility);
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
        public Guid PlayerId
        {
            get
            {
                return playerId;
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
            return carts.Where(x => x.Id == cartId).First();
        }
    }
}
