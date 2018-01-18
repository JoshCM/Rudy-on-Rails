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
    class CreateCartCommand : CommandBase
    {
        private Guid playerId;
        private Guid currentLocoId;
        private int xPos;
        private int yPos;
        private Guid cartId;
        private Compass drivingDirection;

        public CreateCartCommand(GameSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));
            currentLocoId = Guid.Parse(messageInformation.GetValueAsString("currentLocoId"));
            cartId = Guid.Parse(messageInformation.GetValueAsString("cartId"));
            drivingDirection = (Compass)Enum.Parse(typeof(Compass), messageInformation.GetValueAsString("drivingDirection"));
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
        }

        public override void Execute()
        {
            GameSession gameSession = (GameSession)session;
            Player player = session.GetPlayerById(playerId);
            Loco loco = gameSession.GetLocoById(currentLocoId);
            Square square = session.Map.GetSquare(xPos, yPos);
            Cart cart = new Cart(cartId, playerId, drivingDirection, square);

            if (loco is GhostLoco)
            {
                cart.IsGhostCart = true;
            }

            loco.AddCart(cart);
        }
    }
}
