using Newtonsoft.Json.Linq;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Commands.Base
{
    class CreateStockCommandBase : CommandBase
    {
        Guid stockId;
        private int xPos;
        private int yPos;
        List<Resource> resources;

        /// <summary>
        /// Setzt den Stock einer Trainstation
        /// </summary>
        /// <param name="session"></param>
        /// <param name="messageInformation"></param>
        public CreateStockCommandBase(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            stockId = Guid.Parse(messageInformation.GetValueAsString("stockId"));
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");

            // über resourceId resources nutzen
            // TODO: Resources müssen auch ohne auf einem Square zu liegen existieren können,
            //       da der Stock eine Liste von ihnen hat, die nicht auf dem Spielfeld liegen sollen
            /*
            List<JObject> jsonResources = messageInformation.GetValueAsJObjectList("resourceIds");
            foreach (JObject obj in jsonResources)
            {
                Guid resourceId = Guid.Parse(obj.GetValue("resourceId").ToString());
                Resource resource = (Resource)EditorSession.GetInstance().Map.GetPlaceableById(resourceId);
                resources.Add(resource);
            }
            */
        }

        public override void Execute()
        {
            Square square = session.Map.GetSquare(xPos, yPos);
            Stock stock = new Stock(stockId, square, Compass.EAST, resources);
            square.PlaceableOnSquare = stock;
        }
    }
}
