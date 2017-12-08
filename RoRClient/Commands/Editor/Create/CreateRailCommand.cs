using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Commands.Base;
using RoRClient.Models.Session;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Editor.Create

{
    public class CreateRailCommand : CommandBase, ICommand
    {

        private Guid railId;
        private int xPos;
        private int yPos;
        private RailSectionPosition node1;
        private RailSectionPosition node2;


        public CreateRailCommand(EditorSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            railId = Guid.Parse(messageInformation.GetValueAsString("railId"));
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
            node1 = (RailSectionPosition)Enum.Parse(typeof(RailSectionPosition), messageInformation.GetValueAsString("railSectionPositionNode1"));
            node2 = (RailSectionPosition)Enum.Parse(typeof(RailSectionPosition), messageInformation.GetValueAsString("railSectionPositionNode2"));
            
        }

        public new void Execute()
        {
            EditorSession editorSession = EditorSession.GetInstance();
            Square square = editorSession.Map.GetSquare(xPos, yPos);
            Rail rail = new Rail(railId, square, new RailSection(node1, node2));
            square.PlaceableOnSquare = rail;
        }
    }
}
