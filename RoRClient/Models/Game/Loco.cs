﻿using System;
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
        private Compass drivingDirection;

        public Loco(Guid id, Compass drivingDirection, Square square) : base(square)
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
