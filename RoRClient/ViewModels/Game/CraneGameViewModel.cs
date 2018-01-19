using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    class CraneGameViewModel:CanvasGameViewModel
    {

        private Crane crane;

        public CraneGameViewModel(Crane crane) : base(crane.Id)
        {
            this.crane = crane;
            this.SquarePosX = crane.Square.PosX;
            this.SquarePosY = crane.Square.PosY;
        }

        public Crane Crane
        {
            get
            {
                return crane;
            }
        }
    }
}
