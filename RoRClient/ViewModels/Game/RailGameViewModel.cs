using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
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
    
    }
}
