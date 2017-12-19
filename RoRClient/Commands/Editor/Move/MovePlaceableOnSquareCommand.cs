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
    class MovePlaceableOnSquareCommand : CommandBase
    {
        private int oldXPos;
        private int oldYPos;
        private int newXPos;
        private int newYPos;

        public MovePlaceableOnSquareCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            oldXPos = message.GetValueAsInt("oldXPos");
            oldYPos = message.GetValueAsInt("oldYPos");
            newXPos = message.GetValueAsInt("newXPos");
            newYPos = message.GetValueAsInt("newYPos");
        }

        public override void Execute()
        {
            EditorSession editorSession = (EditorSession)session;
            InteractiveGameObject tempPlaceableOnSquare = (InteractiveGameObject)editorSession.Map.GetSquare(oldXPos, oldYPos).PlaceableOnSquare;
            editorSession.Map.GetSquare(oldXPos, oldYPos).PlaceableOnSquare = null;
            tempPlaceableOnSquare.Square = editorSession.Map.GetSquare(newXPos, newYPos);
            editorSession.Map.GetSquare(newXPos, newYPos).PlaceableOnSquare = (IPlaceableOnSquare)tempPlaceableOnSquare;
        }
    }
}
