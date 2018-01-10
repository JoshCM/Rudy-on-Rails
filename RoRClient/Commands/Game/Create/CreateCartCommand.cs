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
        private int xPos;
        private int yPos;
        private Guid cartId;
        private Compass drivingDirection;

        public CreateCartCommand(GameSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));
            cartId = Guid.Parse(messageInformation.GetValueAsString("cartId"));
            drivingDirection = (Compass)Enum.Parse(typeof(Compass), messageInformation.GetValueAsString("drivingDirection"));
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
        }

        public override void Execute()
        {
            Player player = session.GetPlayerById(playerId);
            Loco loco = player.Loco;
            Square square = ((GameSession)session).Map.GetSquare(xPos, yPos);
            Cart cart = new Cart(cartId, drivingDirection, square);
            loco.Carts.Add(cart);
            ((GameSession)session).AddCart(cart);

        }
    }
}
