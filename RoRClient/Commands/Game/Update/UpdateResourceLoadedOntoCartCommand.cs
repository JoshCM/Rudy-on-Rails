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
        private MessageInformation message;

        public UpdateResourceLoadedOntoCartCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            this.message = message;
            
        }

        public override void Execute()
        {
            String res = message.GetValueAsString("Resource");
            Guid cartId = message.GetValueAsGuid("CartId");
            Guid locoId = message.GetValueAsGuid("LocoId");
            int xPos = message.GetValueAsInt("XPos");
            int yPos = message.GetValueAsInt("YPos");
            Mine mine = GameSession.GetInstance().getMineByPosition(xPos, yPos);
        
            Loco loco = GameSession.GetInstance().GetLocoById(locoId);
            Cart cart = loco.getCartById(cartId);      
            
            Resource onboardResource=null;
            String newImagePath = null;
            if (res == "Gold")
            {
                onboardResource = (Resource)mine.GetGolds().ElementAt(0);
                mine.GetGolds().RemoveAt(0);
                newImagePath = "/RoRClient;component/Resources/Images/container_gold.png";
            }
            if (res == "Coal")
            {
                onboardResource = (Resource)mine.GetCoals().ElementAt(0);
                mine.GetCoals().RemoveAt(0);
                newImagePath = "/RoRClient;component/Resources/Images/container_coal.png";
            }
            cart.UpdateOnboardResource(onboardResource);
            
            if (onboardResource == null)
            {
                newImagePath = "/ RoRClient;component/Resources/Images/cart.png";
            }
            cart.updateOnboardResourceImagePath(newImagePath);
            Console.WriteLine("..........."+res+"..."+cartId.ToString());
        }

        public override string ToString()
        {
            return base.ToString();
        }
    }
}
