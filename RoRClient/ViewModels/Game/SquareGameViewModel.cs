using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Models.Game;

namespace RoRClient.ViewModels.Game
{
    public class SquareGameViewModel : GameCanvasViewModel
    {
        private Square square;
        public SquareGameViewModel(Square square): base(square.Id)
        {
            this.square = square;
            this.SquarePosX = square.PosX;
            this.SquarePosY = square.PosY;
        }

        public Square Square
        {
            get { return square; }
        }
    }
}
