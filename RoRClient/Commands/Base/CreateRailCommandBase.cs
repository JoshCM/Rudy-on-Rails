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
        protected int xPos;
        protected int yPos;
        protected List<RailSection> railSections = new List<RailSection>();

        public CreateRailCommandBase(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            railId = Guid.Parse(messageInformation.GetValueAsString("railId"));
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");

            List<JObject> railSectionList = messageInformation.GetValueAsJObjectList("railSections");
            foreach (JObject obj in railSectionList)
            {
                Guid railSectionId = Guid.Parse(obj.GetValue("railSectionId").ToString());
                RailSectionPosition node1 = (RailSectionPosition)Enum.Parse(typeof(RailSectionPosition), obj.GetValue("node1").ToString());
                RailSectionPosition node2 = (RailSectionPosition)Enum.Parse(typeof(RailSectionPosition), obj.GetValue("node2").ToString());
                RailSection section = new RailSection(railSectionId, node1, node2);
                railSections.Add(section);
            }
        }

        public override void Execute()
        {
            Square square = session.Map.GetSquare(xPos, yPos);
            Rail rail = new Rail(railId, square, railSections);
            square.PlaceableOnSquare = rail;
            Console.WriteLine("PlaceableOnSquare wurde gesetzt an Square mit " + square.PosX+ ", " + square.PosY);
        }
        
    }
}
