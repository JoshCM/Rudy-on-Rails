using RoRClient.Models.Game;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Commands;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Commands;
using System.Windows.Input;
using Newtonsoft.Json.Linq;
using RoRClient.ViewModels.Helper;

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

        private ICommand changeSwitchCommand;
        public ICommand ChangeSwitchCommand
        {
            get
            {
                if (changeSwitchCommand == null)
                {
                    changeSwitchCommand = new ActionCommand(param => SendChangeSwitchCommand());
                }
                return changeSwitchCommand;
            }
        }

        private void SendChangeSwitchCommand()
        {
            MessageInformation messageInformation = new MessageInformation();
            GameSession gameSession = GameSession.GetInstance();

            MessageInformation messageInfo = new MessageInformation();
            messageInformation.PutValue("xPos", r.Square.PosX);
            messageInformation.PutValue("yPos", r.Square.PosY);
            messageInformation.PutValue("change", true);
            GameSession.GetInstance().QueueSender.SendMessage("ChangeSwitch", messageInformation);
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
            // Das Menü für den Sensor (Auswah eines Scripts) darf nur vom eigenen Spieler benutzt werden, und wenn der Sensor noch nicht aktiv ist
            if (Rail.Sensor.PlayerId == GameSession.GetInstance().OwnPlayer.Id && (!Rail.Sensor.Active) && (!Rail.Sensor.WasActive))
            {
                // Bei Klick auf vorhandenen Sensor, kann ein Script dafür ausgewählt werden
                MapViewModel.GameInteractionsViewModel.CanConfigureSensor = true;

                // Das Rail darunter wird als aktives CanvasViewModel gesetzt
                MapViewModel.SelectedGameCanvasViewModel = this;

                // DrownDown für Auswahl zurücksetzten
                MapViewModel.GameInteractionsViewModel.SelectedSensorScript = null;
            } else
            {
                // Ansonsten soll das Script des Sensor nicht geändert werden können
                MapViewModel.GameInteractionsViewModel.CanConfigureSensor = false;
            }

            // Es soll kein weiterer Sensor mehr platziert werden können und auch 
            MapViewModel.GameInteractionsViewModel.CanPlaceSensor = false;
        }
    }
}
