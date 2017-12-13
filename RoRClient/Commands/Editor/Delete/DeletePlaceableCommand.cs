using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Commands.Editor.Delete
{
    public class DeletePlaceableCommand : CommandBase
    {

        private Guid squareId;
        private int xPos;
        private int yPos;

        public DeletePlaceableCommand(GameSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
        }

        public override void Execute()
        {
            GameSession editorSession = GameSession.GetInstance();
            Square square = editorSession.Map.GetSquare(xPos, yPos);
            square.PlaceableOnSquare = null;
        }
    }
}
