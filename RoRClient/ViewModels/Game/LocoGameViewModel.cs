using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ComponentModel;

namespace RoRClient.ViewModels.Game
{
    public class LocoGameViewModel : CanvasGameViewModel
    {
        private Loco loco;

        public LocoGameViewModel(Loco loco) : base(loco.Id)
        {
            this.loco = loco;
            this.SquarePosX = loco.Square.PosX;
            this.SquarePosY = loco.Square.PosY;

            loco.PropertyChanged += OnLocoChanged;
        }

        private void OnLocoChanged(object sender, PropertyChangedEventArgs e)
        {
            if (e.PropertyName == "Square")
            {
                this.SquarePosX = loco.Square.PosX;
                this.SquarePosY = loco.Square.PosY;
            }
        }

        public Loco Loco
        {
            get
            {
                return loco;
            }
        }
    }
}
