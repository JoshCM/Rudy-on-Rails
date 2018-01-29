using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Commands.Base;
using RoRClient.Models.Session;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Communication;
using System.ComponentModel;

namespace RoRClient.ViewModels.Game
{
    class SpeedSliderViewModel: ViewModelBase
    {
        private int speed;

        public SpeedSliderViewModel()
        {
            
        }

        private void SendUpdateSpeedCommand(double newValue)
        {
            MessageInformation messageInformation = new MessageInformation();
            GameSession gameSession = GameSession.GetInstance();
            messageInformation.PutValue("locoSpeed", newValue);
            gameSession.QueueSender.SendMessage("ChangeSpeedOfLocomotive", messageInformation);
        }

       

        private ICommand updateSpeedCommand;
        internal ICommand UpdateSpeedCommand(double newValue)
        {
            SendUpdateSpeedCommand(newValue);
            return updateSpeedCommand;
        }

        private int Speed
        {
            get
            {
                return speed;
            }
            set
            {
                if (speed != value)
                    speed = value;
            }
        }
    }
}
