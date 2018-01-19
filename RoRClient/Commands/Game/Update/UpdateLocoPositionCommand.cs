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
    class UpdateLocoPositionCommand : CommandBase
    {
        private Guid locoId;
        private int xPos;
        private int yPos;
        private Compass drivingDirection;

        public UpdateLocoPositionCommand(GameSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
            drivingDirection = (Compass)Enum.Parse(typeof(Compass), messageInformation.GetValueAsString("drivingDirection"));
            locoId = Guid.Parse(messageInformation.GetValueAsString("locoId"));
        }

        public override void Execute()
        {
            System.Console.WriteLine(drivingDirection);
            GameSession gameSession = GameSession.GetInstance();
            Square square = gameSession.Map.GetSquare(xPos, yPos);
            Loco loco = gameSession.GetLocoById(locoId);
            loco.Square = square;
            loco.DrivingDirection = drivingDirection;
        }
    }
}
