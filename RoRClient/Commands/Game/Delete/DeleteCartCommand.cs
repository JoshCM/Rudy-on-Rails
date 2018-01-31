using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Game.Delete
{
    class DeleteCartCommand : CommandBase
    {
        private MessageInformation message;
        public DeleteCartCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            this.message = message;
        }

        public override void Execute()
        {
            Guid locoId = message.GetValueAsGuid("locoId");
            Guid railId = message.GetValueAsGuid("railId");
            Guid cartId = message.GetValueAsGuid("cartId");
            
            Loco tempLoco = GameSession.GetInstance().GetLocoById(locoId);
            Cart tempCart = tempLoco.GetCartById(cartId);
            Rail rail = (Rail)session.Map.GetPlaceableById(railId);

            tempLoco.RemoveCart(tempCart);
        }
    }
}
