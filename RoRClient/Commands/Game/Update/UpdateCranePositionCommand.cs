using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Commands.Game.Update
{
    class UpdateCranePositionCommand : CommandBase
    {
        private int newXPos;
        private int newYPos;


        public UpdateCranePositionCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            this.newXPos = message.GetValueAsInt("newXPos");
            this.newYPos = message.GetValueAsInt("newYPos");
        }

        public override void Execute()
        {
            GameSession game = (GameSession)session;
            Square square = game.Map.GetSquare(newXPos, newYPos);
            Rail rail = (Rail)square.PlaceableOnSquare;

            Crane crane = (Crane)rail.PlaceableOnRail;
            crane.Square = game.Map.GetSquare(newXPos, newYPos);
            rail.PlaceableOnRail = null;
            rail.PlaceableOnRail = crane;
            Console.WriteLine("newX" + crane.Square.PosX + "newY:" + crane.Square.PosY);
        }
    }
}
