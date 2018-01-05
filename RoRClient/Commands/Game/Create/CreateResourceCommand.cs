using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Game.Create
{
    /// <summary>
    /// Command zum Erstellen von Resourcen auf der GameMap (Aktuell: Kohle und Gold)
    /// </summary>
    class CreateResourceCommand : CommandBase
    {
        private Guid resourceId;
        private int xPos;
        private int yPos;
        private int quantity;
        private String resourceName;
        private const String prefix = "RoRClient.Models.Game.";

        public CreateResourceCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            resourceId = Guid.Parse(message.GetValueAsString("resourceId"));
            quantity = message.GetValueAsInt("quantity");
            xPos = message.GetValueAsInt("xPos");
            yPos = message.GetValueAsInt("yPos");
            resourceName = message.GetValueAsString("resource");
        }

        public override void Execute()
        {
            Square square = session.Map.GetSquare(xPos, yPos);
            Type resourceType = Type.GetType(prefix + resourceName);
            IPlaceableOnSquare resourceObject = (IPlaceableOnSquare)Activator.CreateInstance(resourceType, resourceId, square);
            square.PlaceableOnSquare = resourceObject;
            Console.WriteLine("Neue Resource erstellt: " + resourceObject.ToString());
        }
    }
}
