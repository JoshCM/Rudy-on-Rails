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

        public CreatePlayerCommand(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            playerId = messageInformation.GetValueAsGuid("playerId");
            playerName = messageInformation.GetValueAsString("playerName");
            isHost = messageInformation.GetValueAsBool("isHost");
        }

        public override void Execute()
        {
            Player player = new Player(playerId, playerName, isHost);
            GameSession.GetInstance().AddPlayer(player);
        }
    }
}
