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
    class UpdateResourcesOfPlayerCommand : CommandBase
    {
        private Guid playerId;
        private int coalCount;
        private int goldCount;
        private int pointCount;

        public UpdateResourcesOfPlayerCommand(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            playerId = messageInformation.GetValueAsGuid("playerId");
            coalCount = messageInformation.GetValueAsInt("coalCount");
            goldCount = messageInformation.GetValueAsInt("goldCount");
            pointCount = messageInformation.GetValueAsInt("pointCount");
        }

        public override void Execute()
        {
            GamePlayer player = (GamePlayer)GameSession.GetInstance().GetPlayerById(playerId);
            player.CoalCount = coalCount;
            player.GoldCount = goldCount;
            player.PointCount = pointCount;
        }
    }
}
