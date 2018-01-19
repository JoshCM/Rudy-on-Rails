﻿using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Game.Update
{
    /// <summary>
    /// Command zum Erstellen von Sensoren auf einem Rail
    /// </summary>
    public class UpdateSensorCommand : CommandBase
    {
        private Guid railId;

        public UpdateSensorCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            this.railId = message.GetValueAsGuid("railId");
        }

        public override void Execute()
        {
            GameSession gameSession = GameSession.GetInstance();
            Rail rail = (Rail)gameSession.Map.GetPlaceableById(railId);
            rail.ActivateSensor();
        }
    }
}
