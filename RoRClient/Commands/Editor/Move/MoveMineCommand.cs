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
        private Compass alignment;

        public MoveMineCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            oldXPos = message.GetValueAsInt("oldXPos");
            oldYPos = message.GetValueAsInt("oldYPos");
            newXPos = message.GetValueAsInt("newXPos");
            newYPos = message.GetValueAsInt("newYPos");
            alignment = (Compass)Enum.Parse(typeof(Compass), message.GetValueAsString("alignment"));
        }

        public override void Execute()
        {
            // Mine merken und auf alter Rail löschen
            EditorSession editorSession = (EditorSession)session;
            Square oldSquare = editorSession.Map.GetSquare(oldXPos, oldYPos);
            Rail oldRail = (Rail)oldSquare.PlaceableOnSquare;
            Mine mine = (Mine)oldRail.PlaceableOnRail;

            // Square und Alignment der Mine setzen
            Square newSquare = editorSession.Map.GetSquare(newXPos, newYPos);
            mine.Square = newSquare;
            mine.Alignment = alignment;

            // Mine auf neue Rail setzen
            Rail newRail = (Rail)newSquare.PlaceableOnSquare;
            newRail.PlaceableOnRail = mine;

            oldRail.PlaceableOnRail = null;
        }
    }
}
