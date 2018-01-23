using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Commands.Base;
using RoRClient.Models.Session;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using Newtonsoft.Json.Linq;

namespace RoRClient.Commands.Base
{
    /// <summary>
    /// Basis-Klasse für CreateRailCommand: Wird für Game und Editor gleichermaßen genutzt
    /// </summary>
    public class CreateRailCommandBase : CommandBase
    {
        protected Guid railId;
        protected Guid trainstationId;
        protected int xPos;
        protected int yPos;
        protected List<RailSection> railSections = new List<RailSection>();

        public CreateRailCommandBase(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            if (messageInformation.attributes.ContainsKey("trainstationId"))
                trainstationId = Guid.Parse(messageInformation.GetValueAsString("trainstationId"));

            railId = Guid.Parse(messageInformation.GetValueAsString("railId"));
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");

            List<JObject> railSectionList = messageInformation.GetValueAsJObjectList("railSections");
            foreach (JObject obj in railSectionList)
            {
                Guid railSectionId = Guid.Parse(obj.GetValue("railSectionId").ToString());
                Compass node1 = (Compass)Enum.Parse(typeof(Compass), obj.GetValue("node1").ToString());
				Compass node2 = (Compass)Enum.Parse(typeof(Compass), obj.GetValue("node2").ToString());
                RailSectionStatus railSectionStatus = (RailSectionStatus)Enum.Parse(typeof(RailSectionStatus), obj.GetValue("railSectionStatus").ToString());
                RailSection section = new RailSection(railSectionId, node1, node2, railSectionStatus);
                railSections.Add(section);
            }
        }

        public override void Execute()
        {
            Square square = session.Map.GetSquare(xPos, yPos);
            Rail rail = new Rail(railId, square, railSections, trainstationId);
            square.PlaceableOnSquare = rail;
            Console.WriteLine("PlaceableOnSquare wurde gesetzt an Square mit " + square.PosX+ ", " + square.PosY);
        }
        
    }
}
