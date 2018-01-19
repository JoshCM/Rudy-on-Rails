using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Commands.Editor.Update
{
    class UpdateAlignmentOfCrane : CommandBase
    {
        private int xPos;
        private int yPos;
        Compass newAlignment;
        public UpdateAlignmentOfCrane(RoRSession session, MessageInformation message) : base(session, message)
        {
            xPos = message.GetValueAsInt("XPos");
            yPos = message.GetValueAsInt("YPos");
            newAlignment = (Compass)Enum.Parse(typeof(Compass), message.GetValueAsString("newAlignment"));
        }

        public override void Execute()
        {
            EditorSession editorSession = (EditorSession) session;
            Square square = editorSession.Map.GetSquare(xPos, yPos);
            Rail rail = (Rail)square.PlaceableOnSquare;
            Crane crane = (Crane)rail.PlaceableOnRail;
            crane.Alignment = newAlignment;
        }
    }
}
