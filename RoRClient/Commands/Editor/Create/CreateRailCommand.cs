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

namespace RoRClient.Commands.Editor.Create

{
    public class CreateRailCommand : CommandBase
    {
        private Guid railId;
        private int xPos;
        private int yPos;
        private List<RailSection> railSections = new List<RailSection>();

        public CreateRailCommand(EditorSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            railId = Guid.Parse(messageInformation.GetValueAsString("railId"));
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");

            List<JObject> railSectionList = messageInformation.GetValueAsJObjectList("railSections");
            foreach (JObject obj in railSectionList)
            {
                Guid railSectionId = Guid.Parse(obj.GetValue("railSectionId").ToString());
                Compass node1 = (Compass)Enum.Parse(typeof(Compass), obj.GetValue("node1").ToString());
                Compass node2 = (Compass)Enum.Parse(typeof(Compass), obj.GetValue("node2").ToString());
                RailSection section = new RailSection(railSectionId, node1, node2);
                railSections.Add(section);
            }
        }

        public override void Execute()
        {
            EditorSession editorSession = EditorSession.GetInstance();
            Square square = editorSession.Map.GetSquare(xPos, yPos);
            Rail rail = new Rail(railId, square, railSections);
            square.PlaceableOnSquare = rail;
        }
    }
}
