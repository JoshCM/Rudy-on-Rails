using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;
using Newtonsoft.Json.Linq;

namespace RoRClient.Commands.Game.Create
{
    class CreatePlayertrainstationCommand : CreatePlayertrainstationCommandBase
    {

        private int craneXPos;
        private int craneYPos;
        /// <summary>
        /// Setzt die PlayerTrainstation und ihre zugehörigen Rails
        /// </summary>
        /// <param name="session"></param>
        /// <param name="messageInformation"></param>
        public CreatePlayertrainstationCommand(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            
            craneXPos = messageInformation.GetValueAsInt("craneXPos");
            craneYPos = messageInformation.GetValueAsInt("craneYPos");


        }

        public override void Execute() 
        {
            base.Execute();

            Square craneSquare = session.Map.GetSquare(craneXPos, craneYPos);
            Rail rail = (Rail)craneSquare.PlaceableOnSquare;
            Crane crane = (Crane)rail.PlaceableOnRail;

            trainstation.Crane = crane;

        }
    }
}
