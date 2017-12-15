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
    class MovePlaceableCommand : CommandBase
    {
        private int oldXPos;
        private int newXPos;
        private int oldYPos;
        private int newYPos;
        private Guid placeableId;
        /// <summary>
        /// Nachricht zum ändern eines Square.PlaceableOnSquare wird ausgelesen
        /// </summary>
        /// <param name="session"></param>
        /// <param name="messageInformation"></param>
        public MovePlaceableCommand(EditorSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            placeableId = messageInformation.GetValueAsGuid("placeableId");
            oldXPos = messageInformation.GetValueAsInt("oldXPos");
            newXPos = messageInformation.GetValueAsInt("newXPos");
            oldYPos = messageInformation.GetValueAsInt("oldYPos");
            newYPos = messageInformation.GetValueAsInt("newYPos");
        }
        /// <summary>
        /// Square wird neues PlaceableOnSquare gesetzt
        /// </summary>
        public override void Execute()
        {
            EditorSession editorSession = EditorSession.GetInstance();
            Square oldSquare = editorSession.Map.GetSquare(oldXPos, oldYPos);
            Square newSquare = editorSession.Map.GetSquare(newXPos, newYPos);
            IPlaceableOnSquare placeable = editorSession.Map.GetPlaceableById(placeableId);
            newSquare.PlaceableOnSquare = placeable;
            oldSquare.PlaceableOnSquare = null;
            //TODO: das geht irgendwie noch nicht
        }
    }
}
