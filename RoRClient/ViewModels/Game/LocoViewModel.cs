using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    public class LocoViewModel : CanvasGameViewModel
    {
        private Loco l;

        public LocoViewModel(Loco loco) : base(loco.Id)
        {
            this.l = loco;
            this.SquarePosX = loco.Square.PosX;
            this.SquarePosY = loco.Square.PosY;
        }

        public Loco Loco
        {
            get
            {
                return l;
            }
        }
    }
}
