using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    public class CoalGameViewModel : CanvasGameViewModel
    {
        private Coal c;
        public CoalGameViewModel(Coal coal) : base(coal.Id)
        {

            this.c = coal;
            this.SquarePosX = coal.Square.PosX;
            this.SquarePosY = coal.Square.PosY;

        }

        public Coal Coal
        {
            get
            {
                return c;
            }
        }
    }
}
