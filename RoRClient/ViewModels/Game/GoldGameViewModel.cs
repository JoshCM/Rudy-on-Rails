using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    public class GoldGameViewModel : CanvasGameViewModel
    {
        private Gold g;
        public GoldGameViewModel(Gold gold) : base(gold.Id)
        {
            this.g = gold;
            this.SquarePosX = gold.Square.PosX;
            this.SquarePosY = gold.Square.PosY;
        }


        public Gold Gold
        {
            get
            {
                return g;
            }
        }
    }
}
