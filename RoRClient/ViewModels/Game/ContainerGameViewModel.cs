using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    class ContainerGameViewModel : CanvasGameViewModel
    {

        private Container c;

        public ContainerGameViewModel(Container container) : base(container.Id)
        {
            this.c = container;
            this.SquarePosX = container.Square.PosX;
            this.SquarePosY = container.Square.PosY;
        }

        public Container Container
        {
            get
            {
                return c;
            }
        }
    }
}
