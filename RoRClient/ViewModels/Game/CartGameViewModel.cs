using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    class CartGameViewModel : CanvasGameViewModel
    {
        private Cart cart;
        private Compass realDrivingDirection;
        private int angle;

        public CartGameViewModel(Cart cart) : base(cart.Id)
        {
            this.cart = cart;
            this.SquarePosX = cart.Square.PosX;
            this.SquarePosY = cart.Square.PosY;
            this.realDrivingDirection = cart.DrivingDirection;
            SetAngleAccordingToDrivingDirection();

            cart.PropertyChanged += OnCartChanged;
        }

        private void SetAngleAccordingToDrivingDirection()
        {
            switch (cart.DrivingDirection)
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
        }

        private void OnCartChanged(object sender, PropertyChangedEventArgs e)
        {
            if (e.PropertyName == "Square")
            {
                this.SquarePosX = cart.Square.PosX;
                this.SquarePosY = cart.Square.PosY;

                if (!RealDrivingDirection.Equals(cart.DrivingDirection))
                {
                    switch (cart.DrivingDirection)
                    {
                        case Compass.NORTH:
                            if (RealDrivingDirection.Equals(Compass.EAST))
                                Angle -= 90;
                            else if (RealDrivingDirection.Equals(Compass.WEST))
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
                    RealDrivingDirection = cart.DrivingDirection;
                }
            }
            if(e.PropertyName == "Speed")
            {
                InvertDrivingDirectionIfDrivingDirectionHasChanged(cart.Speed);

                if (cart.Speed != 0)
                {
                    lastSpeedValueGreaterOrLessThanZero = cart.Speed;
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
                if (angle != value)
                {
                    angle = value;
                    OnPropertyChanged("Angle");
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
                    OnPropertyChanged("RealDrivingDirection");
                }
            }
        }
        int lastSpeedValueGreaterOrLessThanZero = 0;

        private void InvertDrivingDirectionIfDrivingDirectionHasChanged(int value)
        {
            if ((value > 0 && lastSpeedValueGreaterOrLessThanZero < 0) || (value < 0 && lastSpeedValueGreaterOrLessThanZero > 0)) // falls er die Fahrtrichtung ändert
            {
                InvertDrivingDirection();
            }
        }

        private void InvertDrivingDirection()
        {
            switch (RealDrivingDirection)
            {
                case Compass.NORTH:
                    RealDrivingDirection = Compass.SOUTH;
                    cart.DrivingDirection = Compass.SOUTH;
                    break;
                case Compass.EAST:
                    RealDrivingDirection = Compass.WEST;
                    cart.DrivingDirection = Compass.WEST;
                    break;
                case Compass.SOUTH:
                    RealDrivingDirection = Compass.NORTH;
                    cart.DrivingDirection = Compass.NORTH;
                    break;
                case Compass.WEST:
                    RealDrivingDirection = Compass.EAST;
                    cart.DrivingDirection = Compass.EAST;
                    break;
                default:
                    Angle = 0;
                    break;
            }
        }

        public Cart Cart
        {
            get
            {
                return cart;
            }
        }
    }
}
