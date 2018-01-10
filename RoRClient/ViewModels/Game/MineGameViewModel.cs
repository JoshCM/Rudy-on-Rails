using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    public class MineGameViewModel : CanvasGameViewModel
    {

        private Mine mine;

        public MineGameViewModel(Mine mine) : base(mine.Id)
        {
            this.mine = mine;
            this.SquarePosX = mine.Square.PosX;
            this.SquarePosY = mine.Square.PosY;
        }

        public Mine Mine
        {
            get
            {
                return mine;
            }
        }
    }
}
