using RoRClient.Models.Game;
using RoRClient.ViewModels.Commands;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModels.Editor
{
    /// <summary>
    /// Hält die zugehörige Rail und die Position (SquarePosX, SquarePosY) des Rails
    /// </summary>
    public class RailViewModel : CanvasViewModel
    {
        private Rail rail;
        public RailViewModel(Rail rail) : base(rail.Id)
        {
            this.rail = rail;
            this.SquarePosX = rail.Square.PosX;
            this.SquarePosY = rail.Square.PosY;
        }

        public Rail Rail
        {
            get
            {
                return rail;
            }
        }

        public override void Delete()
        {
            // Zu implementieren
            Console.WriteLine("DELETE");
        }

        public override void RotateLeft()
        {
            // Zu implementieren
            Console.WriteLine("ROTATE LEFT");
        }

        public override void RotateRight()
        {
            // Zu implementieren
            Console.WriteLine("ROTATE RIGHT");
        }
    }
}
