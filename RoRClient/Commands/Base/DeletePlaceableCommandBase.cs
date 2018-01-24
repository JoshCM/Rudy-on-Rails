using RoRClient.Commands.Base;
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
    public class DeletePlaceableCommandBase : CommandBase
    {
        private int xPos;
        private int yPos;
        /// <summary>
        /// Daten werden ausgelesen und zugewiesen
        /// </summary>
        /// <param name="session"></param>
        /// <param name="messageInformation"></param>
        public DeletePlaceableCommandBase(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
        }
        /// <summary>
        /// Square wird genommen und gelöscht
        /// </summary>
        public override void Execute()
        {
            Map map = session.Map;
            Square square = map.GetSquare(xPos, yPos);
            IPlaceableOnSquare placeableOnSquare = square.PlaceableOnSquare;
            if(placeableOnSquare is Rail)
            {
                Rail rail = (Rail)placeableOnSquare;
                if(rail.PlaceableOnRail is Crane)
                    rail.PlaceableOnRail = null;
            }
            square.PlaceableOnSquare = null;
        }
    }
}
