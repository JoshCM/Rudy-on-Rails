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
        private Guid squareId;
        private GameSession gameSession;
        private const String prefix = "RoRClient.Models.Game.";

        public CreateResourceCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            this.gameSession = (GameSession)session;
            resourceId = Guid.Parse(message.GetValueAsString("resourceId"));
            squareId = Guid.Parse(message.GetValueAsString("squareId"));
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
            Mine mine = gameSession.getMineByPosition(xPos, yPos);
            if (mine!=null)
            {
                if (resourceName == "Gold")
                {
                    mine.AddGold(resourceObject);
                }
                if(resourceName == "Coal")
                {
                    mine.AddCoal(resourceObject);
                }
                Console.WriteLine("Position x "+xPos+" Position y "+yPos+"ist mine");
            }
            square.PlaceableOnSquare = resourceObject;
            Console.WriteLine("Neue Resource erstellt: " + resourceObject.ToString()+"..........x   "+square.PosX+"..........y "+square.PosY);
        }
    }
}
