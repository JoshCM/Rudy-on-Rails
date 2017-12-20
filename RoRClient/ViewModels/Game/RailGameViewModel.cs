using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    public class RailGameViewModel: CanvasGameViewModel
    {
        private Rail r;

        public RailGameViewModel(Rail rail) : base(rail.Id)
        {
            this.r = rail;
            this.SquarePosX = rail.Square.PosX;
            this.SquarePosY = rail.Square.PosY;
        }

        public Rail Rail
        {
            get
            {
                return r;
            }
        }
    }
}
