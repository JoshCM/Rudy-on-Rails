
using System;
using System.Collections.Generic;
using System.ComponentModel;
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
        private Compass drivingDirection;
        private int speed;
        private bool isGhostCart;

        public Cart(Guid id, Compass drivingDirection, Square square) : base(square)
        {
            this.id = id;
            this.drivingDirection = drivingDirection;
        }

        public bool IsGhostCart
        {
            get
            {
                return isGhostCart;
            }
            set
            {
                if (isGhostCart != value)
                {
                    isGhostCart = value;
                    NotifyPropertyChanged("IsGhostCart");
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
                if (drivingDirection != value)
                {
                    drivingDirection = value;
                    NotifyPropertyChanged("DrivingDirection");
                }
            }
        }
      
        public int Speed
        {
            get
            {
                return speed;
            }
            set
            {
                if (speed != value)
                {
                    speed = value;
                    NotifyPropertyChanged("Speed");
                }
            }
        }
    }
}
