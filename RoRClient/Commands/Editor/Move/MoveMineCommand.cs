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
    public class MoveMineCommand : CommandBase
    {
        private int oldXPos;
        private int oldYPos;
        private int newXPos;
        private int newYPos;

        public MoveMineCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            oldXPos = message.GetValueAsInt("oldXPos");
            oldYPos = message.GetValueAsInt("oldYPos");
            newXPos = message.GetValueAsInt("newXPos");
            newYPos = message.GetValueAsInt("newYPos");
        }

        public override void Execute()
        {
            // Mine merken und auf alter Rail löschen
            EditorSession editorSession = (EditorSession)session;
            Rail rail = (Rail)editorSession.Map.GetSquare(oldXPos, oldYPos).PlaceableOnSquare;
            Mine mine = (Mine)rail.PlaceableOnRail;
            rail.PlaceableOnRail = null;

            mine.Square = editorSession.Map.GetSquare(newXPos, newYPos);

            // Mine auf neue Rail setzen
            Rail newRail = (Rail)editorSession.Map.GetSquare(newXPos, newYPos).PlaceableOnSquare;
            newRail.PlaceableOnRail = mine;
        }
    }
}
