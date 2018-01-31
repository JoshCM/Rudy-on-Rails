using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Game.Update
{
    class UpdateLocoForRespawnCommand : CommandBase
    {
        private Guid playerId;
        private Guid locoId;
        private int xPos;
        private int yPos;
        private Compass drivingDirection;
        public UpdateLocoForRespawnCommand(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {

            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
            drivingDirection = (Compass)Enum.Parse(typeof(Compass), messageInformation.GetValueAsString("drivingDirection"));
            locoId = Guid.Parse(messageInformation.GetValueAsString("locoId"));
        }

        public override void Execute()
        {
            GameSession gameSession = GameSession.GetInstance();
            Square square = gameSession.Map.GetSquare(xPos, yPos);
            Loco loco = gameSession.GetLocoById(locoId);
            loco.Square = square;
            loco.UpdateDrivingDirectionAfterRespawn(drivingDirection);
            loco.UpdateExplosionVisibility("Hidden");
        }
    }
}
