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
    class UpdateLocoCommand : CommandBase
    {
        private Guid locoId;
        private Guid playerId;
        private int xPos;
        private int yPos;

        public UpdateLocoCommand(GameSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            locoId = Guid.Parse(messageInformation.GetValueAsString("locoId"));
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
            playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));

        }

        public override void Execute()
        {
            GameSession gameSession = GameSession.GetInstance();
            Square square = gameSession.Map.GetSquare(xPos, yPos);
            Player player = gameSession.GetPlayerById(playerId);
            Loco loco = player.Loco;
            loco.Square = square;
            square.PlaceableOnSquare = loco;
        }

    }
}
