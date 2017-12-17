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
        private int xPos;
        private int yPos;

        public CreateLocoCommand(GameSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            locoId = Guid.Parse(messageInformation.GetValueAsString("locoId"));
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
        }

        public override void Execute()
        {
            GameSession gameSession = GameSession.GetInstance();
            Square square = gameSession.Map.GetSquare(xPos, yPos);
            Rail rail = square.PlaceableOnSquare as Rail;
            Loco loco = new Loco(locoId, square);
            rail.setPlaceableOnRail(loco);
        }
    }
}
