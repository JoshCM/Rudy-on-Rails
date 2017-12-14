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

namespace RoRClient.Commands.Editor.Create
{
    class CreateTrainstationCommand : CommandBase
    {
        Guid trainstationId;
        private int xPos;
        private int yPos;
        List<Rail> trainstationRails = new List<Rail>();

        public CreateTrainstationCommand(EditorSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            trainstationId = Guid.Parse(messageInformation.GetValueAsString("trainstationId"));
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");

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
            EditorSession editorSession = EditorSession.GetInstance();
            Square square = editorSession.Map.GetSquare(xPos, yPos);
            Trainstation trainstation = new Trainstation(trainstationId, square, trainstationRails);
            square.PlaceableOnSquare = trainstation;
        }
    }
}
