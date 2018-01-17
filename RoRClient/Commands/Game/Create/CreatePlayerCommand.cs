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

namespace RoRClient.Commands.Game.Create

{
    /// <summary>
    /// CreateRailCommand für Game
    /// </summary>
    public class CreatePlayerCommand : CommandBase
    {
        private Guid playerId;
        private string playerName;
        private bool isHost;
        private int coalCount;
        private int goldCount;
        private int pointCount;

        public CreatePlayerCommand(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            playerId = messageInformation.GetValueAsGuid("playerId");
            playerName = messageInformation.GetValueAsString("playerName");
            isHost = messageInformation.GetValueAsBool("isHost");
            coalCount = messageInformation.GetValueAsInt("coalCount");
            goldCount = messageInformation.GetValueAsInt("goldCount");
            pointCount = messageInformation.GetValueAsInt("pointCount");
        }

        public override void Execute()
        {
            GamePlayer player = new GamePlayer(playerId, playerName, coalCount, goldCount, pointCount, isHost);
            GameSession.GetInstance().AddPlayer(player);
        }
    }
}
