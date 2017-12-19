using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Commands.Base;
using RoRClient.Models.Session;
using RoRClient.Communication.DataTransferObject;

namespace RoRClient.ViewModels.Game
{
    class SpeedSliderViewModel
    {
        private ICommand updateSpeedCommand;
        internal ICommand UpdateSpeedCommand(double newValue)
        {
            sendUpdateSpeedCommand(newValue);

            return updateSpeedCommand;
        }

        private void sendUpdateSpeedCommand(double newValue)
        {
            Console.WriteLine("neuer Wert:" + newValue);
            MessageInformation messageInformation = new MessageInformation();
            GameSession gameSession = GameSession.GetInstance();

            messageInformation.PutValue("locoSpeed", newValue);

            gameSession.QueueSender.SendMessage("ChangeSpeedOfLocomotive", messageInformation);
        }
    }
}
