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
    class UpdateUnloadCartCommand : CommandBase
    {

        private Guid cartId;
        private Guid locoId;
        private int xPos;
        private int yPos;

        public UpdateUnloadCartCommand(RoRSession session, MessageInformation message) : base(session, message){

            cartId = message.GetValueAsGuid("cartId");
            locoId = message.GetValueAsGuid("locoId");
            xPos = message.GetValueAsInt("xPos");
            yPos = message.GetValueAsInt("yPos");

        }


        public override void Execute()
        {
            Loco loco = GameSession.GetInstance().GetLocoById(locoId);
            Cart cart = loco.GetCartById(cartId);

            cart.UpdateOnboardResource(null);
           
        }
    }
}
