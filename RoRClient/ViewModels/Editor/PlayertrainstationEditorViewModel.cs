using Newtonsoft.Json.Linq;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
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
    public class PlayertrainstationEditorViewModel : TrainstationEditorViewModel
    {
        private Playertrainstation playertrainstation;
        public PlayertrainstationEditorViewModel(Trainstation trainstation) : base(trainstation)
        {
            this.playertrainstation = (Playertrainstation)trainstation;
        }

        public Playertrainstation Playertrainstation
        {
            get
            {
                return playertrainstation;
            }
        }
    }
}
