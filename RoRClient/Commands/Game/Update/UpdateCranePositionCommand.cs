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
        private int oldYPos;
        private int oldXPos;


        public UpdateCranePositionCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            this.newXPos = message.GetValueAsInt("newXPos");
            this.newYPos = message.GetValueAsInt("newYPos");
            this.oldXPos = message.GetValueAsInt("oldXPos");
            this.oldYPos = message.GetValueAsInt("oldYPos");
        }

        public override void Execute()
        {
            System.Console.WriteLine("newXPos: " + newXPos);
            System.Console.WriteLine("newYPos: " + newYPos);
            System.Console.WriteLine("oldXPos: " + oldXPos);
            System.Console.WriteLine("oldYPos: " + oldYPos);

            GameSession game = (GameSession)session;
            Square square = game.Map.GetSquare(oldXPos, oldYPos);
            Rail rail = (Rail)square.PlaceableOnSquare;



            Crane crane = (Crane)rail.PlaceableOnRail;
            crane.Square = game.Map.GetSquare(newXPos, newYPos);
            Rail newRail = (Rail)crane.Square.PlaceableOnSquare;

            rail.PlaceableOnRail = null;
            newRail.PlaceableOnRail = crane;


            Console.WriteLine("newX" + crane.Square.PosX + "newY:" + crane.Square.PosY);
        }
    }
}
