using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Editor
{
    /// <summary>
    /// Hält die zugehörige Trainstation und die Position (SquarePosX, SquarePosY) der Trainstation
    /// </summary>
    public class TrainstationViewModel : CanvasViewModel
    {
        private Trainstation trainstation; 

        public TrainstationViewModel(Trainstation trainstation) : base(trainstation.Id)
        {
            this.trainstation = trainstation;
            this.SquarePosX = trainstation.Square.PosX;
            this.SquarePosY = trainstation.Square.PosY;
        }

        public Trainstation Trainstation
        {
            get
            {
                return trainstation;
            }
        }
    }
}
