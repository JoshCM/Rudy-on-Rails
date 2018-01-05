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
        private Guid playerId;
        private int xPos;
        private int yPos;
        private Compass drivingDirection;

        public UpdateLocoPositionCommand(GameSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
            drivingDirection = (Compass)Enum.Parse(typeof(Compass), messageInformation.GetValueAsString("drivingDirection"));
            playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));
        }

        public override void Execute()
        {
            GameSession gameSession = GameSession.GetInstance();
            

            Square square = gameSession.Map.GetSquare(xPos, yPos);
            Player player = gameSession.GetPlayerById(playerId);
            Loco loco = player.Loco;

            loco.Square = square;
            loco.DrivingDirection = drivingDirection;
        }
    }
}
