using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel;
using RoRClient.ViewModels.Helper;
using RoRClient.Models.Base;

namespace RoRClient.ViewModels.Game
{
    public abstract class LocoGameViewModel : CanvasGameViewModel
    {
        private Loco loco;
        private int angle;
        private Compass realDrivingDirection;

        public LocoGameViewModel(Loco loco) : base(loco.Id)
        {
            this.loco = loco;
            this.SquarePosX = loco.Square.PosX;
            this.SquarePosY = loco.Square.PosY;
            this.realDrivingDirection = loco.DrivingDirection;
            SetAngleAccordingToDrivingDirection();

            loco.PropertyChanged += OnLocoChanged;
        }

        /// <summary>
        /// setzt den Winkel der Ausrichtung der Lok, je nach Fahrrichtung der Lok
        /// </summary>
        private void SetAngleAccordingToDrivingDirection()
        {
            Console.WriteLine("\n\n SETANGLE:" + loco.DrivingDirection + "\n\n");
            switch (loco.DrivingDirection)
            {
                case Compass.EAST:
                    angle = 0;
                    break;
                case Compass.SOUTH:
                    angle = 90;
                    break;
                case Compass.WEST:
                    angle = 180;
                    break;
                case Compass.NORTH:
                    angle = 270;
                    break;
            }
        }

        private void OnLocoChanged(object sender, PropertyChangedEventArgs e)
        {
            if (e.PropertyName == "Square")
            {
                SquarePosX = loco.Square.PosX;
                SquarePosY = loco.Square.PosY;

                if (RealDrivingDirection != loco.DrivingDirection)
                {
                    switch (loco.DrivingDirection)
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
                            if (RealDrivingDirection.Equals(Compass.NORTH))
                                Angle -= 90;
                            else if (RealDrivingDirection.Equals(Compass.SOUTH))
                                Angle += 90;
                            break;
                        default:
                            Angle = 0;
                            break;
                    }

                    RealDrivingDirection = loco.DrivingDirection;
                }
            }

            if(e.PropertyName == "Speed")
            {
                InvertDrivingDirectionIfDrivingDirectionHasChanged(loco.Speed);

                if (loco.Speed != 0)
                {
                    lastSpeedValueGreaterOrLessThanZero = loco.Speed;
                }
            }
            if(e.PropertyName == "UpdateDrivingDirection")
            {
                Console.WriteLine("\n\n DRIVING DIRECTION: " + loco.DrivingDirection + "\n\n REAL DRIVING DIRECTION: " + RealDrivingDirection + "\n\n");
                PropertyChangedExtendedEventArgs<Compass> eventArgs = (PropertyChangedExtendedEventArgs<Compass>)e;
                Compass model = eventArgs.NewValue;
                switch (model)
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
                        if (RealDrivingDirection.Equals(Compass.NORTH))
                            Angle -= 90;
                        else if (RealDrivingDirection.Equals(Compass.SOUTH))
                            Angle += 90;
                        break;
                    default:
                        Angle = 0;
                        break;
                }

                RealDrivingDirection = eventArgs.NewValue;
            }
        }

        private void InvertDrivingDirection()
        {
            switch (RealDrivingDirection)
            {
                case Compass.NORTH:
                    RealDrivingDirection = Compass.SOUTH;
                    loco.DrivingDirection = Compass.SOUTH;
                    break;
                case Compass.EAST:
                    RealDrivingDirection = Compass.WEST;
                    loco.DrivingDirection = Compass.WEST;
                    break;
                case Compass.SOUTH:
                    RealDrivingDirection = Compass.NORTH;
                    loco.DrivingDirection = Compass.NORTH;
                    break;
                case Compass.WEST:
                    RealDrivingDirection = Compass.EAST;
                    loco.DrivingDirection = Compass.EAST;
                    break;
                default:
                    Angle = 0;
                    break;
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

        public Loco Loco
        {
            get
            {
                return loco;
            }
        }
    }
}
