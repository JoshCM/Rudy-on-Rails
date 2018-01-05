using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
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
        private Compass drivingDirection;
        private Compass realDrivingDirection;

        public Loco(Guid id, Compass drivingDirection, Square square) : base(square)
        {
            this.id = id;
            this.drivingDirection = drivingDirection;
            this.realDrivingDirection = drivingDirection;

            base.PropertyChanged += OnBasePropertyChanged;
        }

        private void OnBasePropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            if(e.PropertyName == "Square")
            {
                RealDrivingDirection = DrivingDirection;
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
                if(speed != value)
                {
                    speed = value;
                    NotifyPropertyChanged("Speed");
                }
            }
        }

        public Compass RealDrivingDirection
        {
            get
            {
                return realDrivingDirection;
            }
            set
            {
                if (realDrivingDirection != value)
                {
                    realDrivingDirection = value;
                    NotifyPropertyChanged("RealDrivingDirection");
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
