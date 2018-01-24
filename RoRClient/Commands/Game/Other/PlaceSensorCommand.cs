using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Game.Other
{
    /// <summary>
    /// Command zum Platzieren von Sensoren auf einem Rail
    /// </summary>
    public class PlaceSensorCommand : CommandBase
    {
        private Guid railId;
        private Guid playerId;

        public PlaceSensorCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            this.railId = message.GetValueAsGuid("railId");
            this.playerId = message.GetValueAsGuid("playerId");
        }

        public override void Execute()
        {
            GameSession gameSession = GameSession.GetInstance();
            Rail rail = (Rail)gameSession.Map.GetPlaceableById(railId);
            rail.PlaceSensor(playerId);
        }
    }
}
