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
    class UpdateDroppedResourcePositionCommand : CommandBase
    {
        private MessageInformation message;
        private const String prefix = "RoRClient.Models.Game.";
        public UpdateDroppedResourcePositionCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            this.message = message;
        }

        public override void Execute()
        {
            Guid cartId = message.GetValueAsGuid("cartId");
            Guid currentLocoId = message.GetValueAsGuid("currentLocoId");
            int droppedToSquareX = message.GetValueAsInt("droppedToSquareX");
            int droppedToSquareY = message.GetValueAsInt("droppedToSquareY");
            String resourceName = message.GetValueAsString("resource");
            Guid resourceId = message.GetValueAsGuid("resourceId");

            Loco crashedLoco = GameSession.GetInstance().GetLocoById(currentLocoId);
            Cart resourceDroppingCart = crashedLoco.GetCartById(cartId);
            resourceDroppingCart.UpdateOnboardResource(null);
            Square square = session.Map.GetSquare(droppedToSquareX, droppedToSquareY);

            Type resourceType = Type.GetType(prefix + resourceName);
            IPlaceableOnSquare resourceObject = (IPlaceableOnSquare)Activator.CreateInstance(resourceType, resourceId, square);
            square.PlaceableOnSquare = resourceObject;
        }
    }
}
