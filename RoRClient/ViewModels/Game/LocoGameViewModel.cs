using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    public class LocoGameViewModel : CanvasGameViewModel
    {
        private Loco l;

        public LocoGameViewModel(Loco loco) : base(loco.Id)
        {
            Console.WriteLine("loco wird locogameviewmodel zugeordnet");
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
