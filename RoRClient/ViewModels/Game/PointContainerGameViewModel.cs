using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    public class PointContainerGameViewModel : CanvasGameViewModel
    {
        private PointContainer pointContainer;
        public PointContainerGameViewModel(PointContainer pointContainer) : base(pointContainer.Id)
        {
            this.pointContainer = pointContainer;
            this.SquarePosX = pointContainer.Square.PosX;
            this.SquarePosY = pointContainer.Square.PosY;
        }

        public PointContainer PointContainer
        {
            get
            {
                return pointContainer;
            }
        }
    }
}
