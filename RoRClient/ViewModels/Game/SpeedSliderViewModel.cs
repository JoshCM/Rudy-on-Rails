﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Commands.Base;
using RoRClient.Models.Session;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Communication;

namespace RoRClient.ViewModels.Game
{
    class SpeedSliderViewModel
    {
        private ICommand updateSpeedCommand;//muss das sein?
        internal ICommand UpdateSpeedCommand(double newValue)
        {
            sendUpdateSpeedCommand(newValue);

            return updateSpeedCommand;
        }

        private void sendUpdateSpeedCommand(double newValue)
        {
            MessageInformation messageInformation = new MessageInformation();
            GameSession gameSession = GameSession.GetInstance();

            messageInformation.PutValue("locoSpeed", newValue);

            gameSession.QueueSender.SendMessage("ChangeSpeedOfLocomotive", messageInformation);
        }
    }
}
