using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Game.Delete
{
    class DeleteSensorCommand : CommandBase
    {
        private bool active;
        private Guid railId;
        public DeleteSensorCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            active = message.GetValueAsBool("active");
            railId = message.GetValueAsGuid("railId");
        }

        public override void Execute()
        {
            Rail rail = (Rail)session.Map.GetPlaceableById(railId);
            rail.RemoveSensor();
        }
    }
}
