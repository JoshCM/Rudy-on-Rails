using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Commands.Base
{
    class CreateCraneCommandBase : CommandBase
    {
        Guid craneId;
        private Compass alignment;
        private int xPos;
        private int yPos;

        public CreateCraneCommandBase(RoRSession session, MessageInformation messageInformation): base (session, messageInformation)
        {

            craneId = messageInformation.GetValueAsGuid("craneId");
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
            alignment = (Compass)Enum.Parse(typeof(Compass), messageInformation.GetValueAsString("alignment"));
        }

        public override void Execute()
        {
            Square square = session.Map.GetSquare(xPos, yPos);
            Crane crane = new Crane(craneId, square, alignment);
            Rail rail = (Rail)square.PlaceableOnSquare;
            rail.PlaceableOnRail = null;
            rail.PlaceableOnRail = crane;
        }
    }
}
