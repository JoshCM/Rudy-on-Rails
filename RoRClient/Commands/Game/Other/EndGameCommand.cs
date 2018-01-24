using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Game.Other
{
    class EndGameCommand : CommandBase
    {
        private Guid winningPlayerId;

        public EndGameCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            winningPlayerId = message.GetValueAsGuid("winningPlayerId");
        }

        public override void Execute()
        {
            GameSession gameSession = ((GameSession)session);
            gameSession.WinningPlayer = (GamePlayer)gameSession.GetPlayerById(winningPlayerId);
        }
    }
}
