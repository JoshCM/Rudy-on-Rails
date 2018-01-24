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
    class UpdateCoalOfPlayer:CommandBase
    {
        private int newCoalCount = 0;
        private GamePlayer player;
        private GameSession session;
        public UpdateCoalOfPlayer(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            this.newCoalCount = (int)messageInformation.GetValueAsDouble("coalCount");
            this.session = (GameSession)session;
            this.player = (GamePlayer)session.GetPlayerById(messageInformation.GetValueAsGuid("playerId"));

        }

        public override void Execute()
        {
            if(newCoalCount != player.CoalCount)
            {
                player.CoalCount = newCoalCount;
            }
        }
    }
}
