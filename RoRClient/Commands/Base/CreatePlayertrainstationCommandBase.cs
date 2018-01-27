using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;
using Newtonsoft.Json.Linq;

namespace RoRClient.Commands.Base
{
    class CreatePlayertrainstationCommandBase : CommandBase
    {
        protected Guid trainstationId;
        protected Guid stockId;
        protected Guid playerId;
        protected int xPos;
        protected int yPos;
        protected int stockXPos;
        protected int stockYPos;
        protected Compass alignment;
        protected List<Rail> trainstationRails = new List<Rail>();
        protected Playertrainstation trainstation;

        /// <summary>
        /// Setzt die PlayerTrainstation und ihre zugehörigen Rails
        /// </summary>
        /// <param name="session"></param>
        /// <param name="messageInformation"></param>
        public CreatePlayertrainstationCommandBase(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));
            trainstationId = Guid.Parse(messageInformation.GetValueAsString("trainstationId"));
            stockId = Guid.Parse(messageInformation.GetValueAsString("stockId"));

            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
            alignment = (Compass)Enum.Parse(typeof(Compass), messageInformation.GetValueAsString("alignment"));

            // über railids rails nutzen
            List<JObject> rails = messageInformation.GetValueAsJObjectList("trainstationRails");
            foreach (JObject obj in rails)
            {
                Guid railId = Guid.Parse(obj.GetValue("railId").ToString());
                Rail rail = (Rail)EditorSession.GetInstance().Map.GetPlaceableById(railId);
                trainstationRails.Add(rail);
            }
        }

        public override void Execute()
        {

            Square square = session.Map.GetSquare(xPos, yPos);
            Stock stock = (Stock)session.Map.GetPlaceableById(stockId);

            trainstation = new Playertrainstation(trainstationId, square, trainstationRails, alignment, stock, session.GetPlayerById(playerId));
            square.PlaceableOnSquare = (Playertrainstation)trainstation;

        }


    }
}
