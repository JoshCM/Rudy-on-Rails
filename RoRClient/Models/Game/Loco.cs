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
        private int angle;
        private Compass drivingDirection;
        private Compass realDrivingDirection;

        public Loco(Guid id, Compass drivingDirection, Square square) : base(square)
        {
            this.id = id;
            this.drivingDirection = drivingDirection;
            this.realDrivingDirection = drivingDirection;
            //Je nachdem wie der Zug auf der Schiene positioniert ist muss die Rotation gesetzt werden.
            switch(drivingDirection)
            {
                case Compass.EAST:
                    this.angle = 0;
                    break;
                case Compass.SOUTH:
                    this.angle = 90;
                    break;
                case Compass.WEST:
                    this.angle = 180;
                    break;
                case Compass.NORTH:
                    this.angle = 270;
                    break;
            }

            PropertyChanged += OnBasePropertyChanged;
        }

        private void OnBasePropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            if(e.PropertyName == "Square")
            {
                if (!RealDrivingDirection.Equals(DrivingDirection))
                {
                    switch (DrivingDirection)
                    {
                        case Compass.NORTH:
                            if (RealDrivingDirection.Equals(Compass.EAST))
                                Angle -= 90;
                            else if(RealDrivingDirection.Equals(Compass.WEST))
                                Angle += 90;

                            break;
                        case Compass.EAST:
                            if (RealDrivingDirection.Equals(Compass.SOUTH))
                                Angle -= 90;
                            else if (RealDrivingDirection.Equals(Compass.NORTH))
                                Angle += 90;
                            break;
                        case Compass.SOUTH:
                            if (RealDrivingDirection.Equals(Compass.WEST))
                                Angle -= 90;
                            else if (RealDrivingDirection.Equals(Compass.EAST))
                                Angle += 90;
                            break;
                        case Compass.WEST:
                            if (RealDrivingDirection.Equals(Compass.SOUTH))
                                Angle += 90;
                            else if (RealDrivingDirection.Equals(Compass.NORTH))
                                Angle -= 90;
                            break;
                        default:
                            Angle = 0;
                            break;
                    }
                    RealDrivingDirection = DrivingDirection;
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
        public int Angle
        {
            get
            {
                return angle;
            }
            set
            {
                if(angle != value)
                {
                    angle = value;
                    NotifyPropertyChanged("Angle");
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
