using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    class TrainstationGameViewModel : CanvasGameViewModel
    {
        private Playertrainstation trainstation;

        public TrainstationGameViewModel(Playertrainstation trainstation) : base(trainstation.Id)
        {
            this.trainstation = trainstation;
            this.SquarePosX = trainstation.Square.PosX;
            this.SquarePosY = trainstation.Square.PosY;
        }

        public Playertrainstation Trainstation
        {
            get
            {
                return trainstation;
            }
        }
    }
}
