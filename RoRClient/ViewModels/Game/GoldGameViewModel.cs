using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    class GoldGameViewModel : CanvasGameViewModel
    {
        private Gold g;
        public GoldGameViewModel(Gold gold) : base(gold.Id)
        {
            this.g = gold;
            this.SquarePosX = gold.Square.PosX;
            this.SquarePosY = gold.Square.PosY;
        }


        public Gold gold
        {
            get
            {
                return g;
            }
        }
    }
}
