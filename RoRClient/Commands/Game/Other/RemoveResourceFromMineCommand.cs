using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Game.Other
{
    class RemoveResourceFromMineCommand : CommandBase
    {
        private int xPos;
        private int yPos;
        private string resourceType;

        public RemoveResourceFromMineCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            xPos = message.GetValueAsInt("xPos");
            yPos = message.GetValueAsInt("yPos");
            resourceType = message.GetValueAsString("resourceType");
        }

        public override void Execute()
        {
            Map map = session.Map;
            Mine mine = ((GameSession)session).getMineByPosition(xPos, yPos);
               
            if (resourceType == "Gold")
            {
                mine.RemoveGold(mine.Golds.ElementAt(0));
            }
            else if (resourceType == "Coal")
            {
                mine.RemoveCoal(mine.Coals.ElementAt(0));
            }
        }
    }
}
