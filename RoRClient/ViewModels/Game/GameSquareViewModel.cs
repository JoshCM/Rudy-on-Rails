using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Models.Game;

namespace RoRClient.ViewModels.Game
{
    public class GameSquareViewModel : GameCanvasViewModel
    {
        private Square square;
        public GameSquareViewModel(Square square): base(square.Id)
        {
            this.square = square;
        }
    }
}
