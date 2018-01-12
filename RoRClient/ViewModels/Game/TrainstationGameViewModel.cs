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
        private PlayerTrainstation trainstation;

        public TrainstationGameViewModel(PlayerTrainstation trainstation) : base(trainstation.Id)
        {
            this.trainstation = trainstation;
            this.SquarePosX = trainstation.Square.PosX;
            this.SquarePosY = trainstation.Square.PosY;
        }

        public PlayerTrainstation Trainstation
        {
            get
            {
                return trainstation;
            }
        }
    }
}
