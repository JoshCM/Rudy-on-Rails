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
    class UpdateColorNumberOfPlayerCommand : CommandBase
    {
        private Guid playerId;
        private int colorNumber;

        public UpdateColorNumberOfPlayerCommand(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            playerId = messageInformation.GetValueAsGuid("playerId");
            colorNumber = messageInformation.GetValueAsInt("colorNumber");
        }

        public override void Execute()
        {
            GamePlayer player = (GamePlayer)GameSession.GetInstance().GetPlayerById(playerId);
            player.ColorNumber = colorNumber;
        }
    }
}
