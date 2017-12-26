using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Editor.Move
{
    class MoveRailCommand : CommandBase
    {
        private int oldXPos;
        private int oldYPos;
        private int newXPos;
        private int newYPos;

        public MoveRailCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            oldXPos = message.GetValueAsInt("oldXPos");
            oldYPos = message.GetValueAsInt("oldYPos");
            newXPos = message.GetValueAsInt("newXPos");
            newYPos = message.GetValueAsInt("newYPos");
        }

        public override void Execute()
        {
            EditorSession editorSession = (EditorSession)session;
            Rail rail = (Rail)editorSession.Map.GetSquare(oldXPos, oldYPos).PlaceableOnSquare;
            editorSession.Map.GetSquare(oldXPos, oldYPos).PlaceableOnSquare = null;
			rail.Square = editorSession.Map.GetSquare(newXPos, newYPos);
            editorSession.Map.GetSquare(newXPos, newYPos).PlaceableOnSquare = rail;
        }
    }
}
