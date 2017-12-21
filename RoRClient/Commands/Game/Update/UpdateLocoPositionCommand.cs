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
    class UpdateLocoPositionCommand : CommandBase
    {
        private Guid playerId;
        private int xPos;
        private int yPos;

        public UpdateLocoPositionCommand(GameSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
            playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));

        }

        public override void Execute()
        {
            GameSession gameSession = GameSession.GetInstance();
            

            Square square = gameSession.Map.GetSquare(xPos, yPos);
            Player player = gameSession.GetPlayerById(playerId);

            Loco loco = player.Loco;
            int i = 0;
            Cart cartCache = null;
            foreach(Cart c in loco.Carts)
            {
                if (i == 0)
                    c.Square = loco.Square;
                else
                    c.Square = cartCache.Square;
                cartCache = c;
                i++;
            }
            loco.Square = square;
        }
    }
}
