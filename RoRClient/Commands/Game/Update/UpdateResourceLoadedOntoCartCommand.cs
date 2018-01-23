using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Game.Update
{
    class UpdateResourceLoadedOntoCartCommand : CommandBase
    {
        private Guid cartId;
        private Guid locoId;
        private int xPos;
        private int yPos;
        private string resourceType;
        private Guid resourceId;

        public UpdateResourceLoadedOntoCartCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            resourceType = message.GetValueAsString("resourceType");
            resourceId = message.GetValueAsGuid("resourceId");
            cartId = message.GetValueAsGuid("cartId");
            locoId = message.GetValueAsGuid("locoId");
            xPos = message.GetValueAsInt("xPos");
            yPos = message.GetValueAsInt("yPos");
        }

        public override void Execute()
        {
            Mine mine = GameSession.GetInstance().getMineByPosition(xPos, yPos);
        
            Loco loco = GameSession.GetInstance().GetLocoById(locoId);
            Cart cart = loco.GetCartById(cartId);      
            
            String newImagePath = null;

            if (resourceType == "Gold")
            {
                cart.UpdateOnboardResource(new Gold(resourceId, null));
                newImagePath = "/RoRClient;component/Resources/Images/container_gold.png";
            }
            else if (resourceType == "Coal")
            {
                cart.UpdateOnboardResource(new Coal(resourceId, null));
                newImagePath = "/RoRClient;component/Resources/Images/container_coal.png";
            }

            cart.updateOnboardResourceImagePath(newImagePath);
        }
    }
}
