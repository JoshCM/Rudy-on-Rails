using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Commands.Game.Update
{
    class UpdateNodesOfRailSectionCommand : CommandBase
    {
        Guid squareId;
        int xPos;
        int yPos;
        Guid railSectionId;
        Compass node1;
        Compass node2;

        public UpdateNodesOfRailSectionCommand(EditorSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            squareId = messageInformation.GetValueAsGuid("squareId");
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
            railSectionId = messageInformation.GetValueAsGuid("railSectionId");
            node1 = (Compass)Enum.Parse(typeof(Compass), messageInformation.GetValueAsString("node1"));
            node2 = (Compass)Enum.Parse(typeof(Compass), messageInformation.GetValueAsString("node2"));
        }

        public override void Execute()
        {
            EditorSession editorSession = EditorSession.GetInstance();
            Square square = editorSession.Map.GetSquare(xPos, yPos);
            Rail rail = (Rail)square.PlaceableOnSquare;

            RailSection railSection = rail.RailSections.Where(x => x.Id == railSectionId).First();
            rail.RailSections.Remove(railSection);
            rail.AddRailSection(new RailSection(railSectionId, node1, node2));
        }
    }
}
