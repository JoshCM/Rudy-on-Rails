﻿using RoRClient.Model.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModel
{
    class RailViewModel : CanvasViewModel
    {
        private Rail rail;
        public RailViewModel(Rail rail) : base(rail.Id)
        {
            this.rail = rail;
            this.SquarePosX = rail.Square.PosX;
            this.SquarePosY = rail.Square.PosY;
        }

        public Rail Rail
        {
            get
            {
                return rail;
            }
        }
    }
}
