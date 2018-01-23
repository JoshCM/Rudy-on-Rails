using RoRClient.Models.Game;
using RoRClient.ViewModels.Commands;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModels.Game
{
    public class RailGameViewModel: CanvasGameViewModel
    {
        private Rail r;

        public RailGameViewModel(Rail rail) : base(rail.Id)
        {
            this.r = rail;
            this.SquarePosX = rail.Square.PosX;
            this.SquarePosY = rail.Square.PosY;
        }

        public Rail Rail
        {
            get
            {
                return r;
            }
        }

        private void SetScriptForSensor()
        {
            // set script for Sensor
        }

        private ICommand activateMenuForScriptCommand;
        public ICommand ActivateMenuForScriptCommand
        {
            get
            {
                if (activateMenuForScriptCommand == null)
                {
                    activateMenuForScriptCommand = new ActionCommand(param => ActivateMenuForScript());
                }
                return activateMenuForScriptCommand;
            }
        }

        private void ActivateMenuForScript()
        {
            // Bei Klick auf vorhandenen Sensor, kann ein Script dafür ausgewählt werden
            MapViewModel.GameInteractionsViewModel.CanConfigureSensor = true;

            // Das Rail darunter wird als aktives CanvasViewModel gesetzt
            MapViewModel.SelectedGameCanvasViewModel = this;

            // Es soll kein weiterer Sensor mehr platziert werden können
            MapViewModel.GameInteractionsViewModel.CanPlaceSensor = false;

            // DrownDown für Auswahl zurücksetzten
            MapViewModel.GameInteractionsViewModel.SelectedSensorScript = null;
        }
    }
}
