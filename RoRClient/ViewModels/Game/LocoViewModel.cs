using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    class LocoViewModel : CanvasViewModel
    {
        private Loco loco;
        public LocoViewModel(Loco loco) : base(loco.Id)
        {
            this.loco = loco;
            this.SquarePosX = loco.Square.PosX;
            this.SquarePosY = loco.Square.PosY;
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
