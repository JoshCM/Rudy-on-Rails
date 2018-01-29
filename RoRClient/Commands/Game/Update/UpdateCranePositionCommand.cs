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
        private Guid trainstationId;
        private Trainstation trainstation;
        private Crane crane;
        private Rail craneRail;


        public UpdateCranePositionCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            this.newXPos = message.GetValueAsInt("newXPos");
            this.newYPos = message.GetValueAsInt("newYPos");
            Guid railId = Guid.Parse(message.GetValueAsString("railId"));
            this.craneRail = (Rail)(session.Map.GetPlaceableById(railId));
            this.trainstationId = Guid.Parse(message.GetValueAsString("trainstationId"));
            this.trainstation = (Trainstation)(session.Map.GetPlaceableById(trainstationId));

        }

        public override void Execute()
        {
     
            GameSession game = (GameSession)session;
            Crane crane = trainstation.Crane;
            
            crane.Square = game.Map.GetSquare(newXPos, newYPos);
            Rail newRail = (Rail)crane.Square.PlaceableOnSquare;

            if (!newRail.Equals(craneRail))
            {
                newRail.PlaceableOnRail = crane;
                craneRail.PlaceableOnRail = null;
            }
        
        }
    }
}
