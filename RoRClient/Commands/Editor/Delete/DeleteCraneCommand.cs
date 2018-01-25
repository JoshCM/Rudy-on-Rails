using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Editor.Delete
{
    public class DeleteCraneCommand : CommandBase
    {

        private Guid railId;

        public DeleteCraneCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            railId = message.GetValueAsGuid("railId");
        }

        public override void Execute()
        {
            Rail rail = (Rail)session.Map.GetPlaceableById(railId);
            rail.PlaceableOnRail = null;

        }
    }
}
