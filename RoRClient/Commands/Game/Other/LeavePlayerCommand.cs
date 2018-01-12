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
    public class LeavePlayerCommand : CommandBase
    {
        private Guid playerId;

        public LeavePlayerCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            playerId = message.GetValueAsGuid("playerId");
        }

        public override void Execute()
        {
            Boolean deleteGameSession = false;
            Player player = GameSession.GetInstance().GetPlayerById(playerId);

            // Wenn der Spieler Host ist oder kein anderer
            if (player == GameSession.GetInstance().OwnPlayer || player.IsHost)
            {
                deleteGameSession = true;
            }

            GameSession.GetInstance().RemovePlayer(player);

            // Dann auch die GameSession löschen
            if (deleteGameSession)
            {
                GameSession.GetInstance().DeleteGameSession();
            }
        }
    }
}
