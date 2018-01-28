﻿using RoRClient.Commands.Base;
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
    class CreatePublictrainstationCommandBase : CommandBase
    {
        Guid trainstationId;
        Guid stockId;
        private int xPos;
        private int yPos;
        private Compass alignment;
        List<Rail> trainstationRails = new List<Rail>();
        GameSession gameSession;
        protected Publictrainstation trainstation;

        /// <summary>
        /// Setzt die PublicTrainstation und ihre zugehörigen Rails
        /// </summary>
        /// <param name="session"></param>
        /// <param name="messageInformation"></param>
        public CreatePublictrainstationCommandBase(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
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

            trainstation = new Publictrainstation(trainstationId, square, trainstationRails, alignment, stock);
            if (session.GetType() == typeof(GameSession))
            {
                this.gameSession = (GameSession)session;
                gameSession.addPublictrainstation(trainstation);
            }
            square.PlaceableOnSquare = (Publictrainstation)trainstation;
        }
    }
}
