﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Commands.Base;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Game.Update
{
    class UpdateLocoSpeedCommand : CommandBase
    {
        private Guid locoId;
        private int speed;

        public UpdateLocoSpeedCommand(RoRSession session, MessageInformation message) : base (session, message)
        {
            locoId = message.GetValueAsGuid("locoId");
            speed = message.GetValueAsInt("speed");
        }
        public override void Execute()
        {
            GameSession gameSession = (GameSession)session;
            Loco loco = gameSession.GetLocoById(locoId);
            loco.Speed = speed;
            
            if (speed == 0)
            {
                loco.UpdateSmokeVisibility("Hidden");
            }
            else
            {
                loco.UpdateSmokeVisibility("Visible");
            }
        }
    }
}
