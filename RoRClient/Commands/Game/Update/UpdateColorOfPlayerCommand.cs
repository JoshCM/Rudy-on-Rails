using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static RoRClient.Models.Game.GamePlayer;

namespace RoRClient.Commands.Game.Update
{
    class UpdateColorOfPlayerCommand : CommandBase
    {
        private Guid playerId;
        private Color color;

        public UpdateColorOfPlayerCommand(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            playerId = messageInformation.GetValueAsGuid("playerId");
            color = (Color)messageInformation.GetValueAsInt("color");
        }

        public override void Execute()
        {
            GamePlayer player = (GamePlayer)GameSession.GetInstance().GetPlayerById(playerId);
            player.PlayerColor = color;
        }
    }
}
