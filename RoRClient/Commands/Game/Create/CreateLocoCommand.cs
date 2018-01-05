using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Commands.Game.Create
{
    class CreateLocoCommand : CommandBase
    {
        private Guid locoId;
        private Guid playerId;
        private int xPos;
        private int yPos;
        private Compass drivingDirection;

        public CreateLocoCommand(GameSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            locoId = Guid.Parse(messageInformation.GetValueAsString("locoId"));
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
            drivingDirection = (Compass)Enum.Parse(typeof(Compass), messageInformation.GetValueAsString("drivingDirection"));
            playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));
        }

        public override void Execute()
        {
            Player player = session.GetPlayerById(playerId);
            Square square = session.Map.GetSquare(xPos, yPos);
            
            Loco loco = new Loco(locoId, drivingDirection, square);
            player.Loco = loco;

            ((GameSession)session).AddLoco(loco);
        }
    }
}
