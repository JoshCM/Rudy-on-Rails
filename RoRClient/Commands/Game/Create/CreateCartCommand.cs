using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Commands.Game.Create
{
    class CreateCartCommand : CommandBase
    {
        private Guid locoId;
        private Guid playerId;
        private int xPos;
        private int yPos;

        public CreateCartCommand(GameSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            locoId = Guid.Parse(messageInformation.GetValueAsString("locoId"));
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
        }

        public override void Execute()
        {
            Player player = session.GetPlayerById(playerId);
            Loco loco = player.Loco;

            //loco.addCart(new Cart(new Guid(), ));
        }
    }
}
