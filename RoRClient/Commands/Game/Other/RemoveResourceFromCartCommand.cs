using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Game.Other
{
    public class RemoveResourceFromCartCommand : CommandBase
    {
        private Guid locoId;
        private Guid cartId;

        public RemoveResourceFromCartCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            locoId = message.GetValueAsGuid("locoId");
            cartId = message.GetValueAsGuid("cartId");
        }

        public override void Execute()
        {
            GameSession gameSession = (GameSession)session;
            Loco loco = gameSession.GetLocoById(locoId);
            Cart cart = loco.GetCartById(cartId);
            cart.UpdateOnboardResource(null);
        }
    }
}
