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
    class UpdateCartToLocoCommand : CommandBase
    {
        private Guid playerId;
        private int xPos;
        private int yPos;
        private Guid currentLocoId;


        public UpdateCartToLocoCommand(GameSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
            playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));
            currentLocoId = Guid.Parse(messageInformation.GetValueAsString("currentLocoId"));

        }
        public override void Execute()
        {
            GameSession gameSession = GameSession.GetInstance();


            Square square = gameSession.Map.GetSquare(xPos, yPos);
            Player player = gameSession.GetPlayerById(playerId);

            Loco loco = gameSession.GetLocoById(currentLocoId);
            Rail rail = square.PlaceableOnSquare as Rail;
            Cart cart = rail.PlaceableOnRail as Cart;
            loco.AddCart(cart);
            //rail.PlaceableOnRail = null;



        }
    }
}
