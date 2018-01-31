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
    class UpdateCartToLocoAfterRespawnCommand : CommandBase

    {
        private Guid playerId;
        private int xPos;
        private int yPos;
        private Guid currentLocoId;
        private Guid cartId;
        public UpdateCartToLocoAfterRespawnCommand(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
            cartId = messageInformation.GetValueAsGuid("cartId");
            playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));
            currentLocoId = Guid.Parse(messageInformation.GetValueAsString("currentLocoId"));
        }

        public override void Execute()
        {
            GameSession gameSession = GameSession.GetInstance();
            Square square = gameSession.Map.GetSquare(xPos, yPos);
            Player player = gameSession.GetPlayerById(playerId);
            Loco loco = gameSession.GetLocoById(currentLocoId);
            Cart cart = loco.GetCartById(cartId);
            loco.AddCart(cart);
           
        }
    }
}
