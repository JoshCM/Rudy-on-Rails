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
    class ShowTradeRelationCommand : CommandBase
    {
        private Guid playerId;
        private Guid trainstationId;
        private bool tradeable;

        public ShowTradeRelationCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            playerId = message.GetValueAsGuid("playerId");
            tradeable = message.GetValueAsBool("tradeable");
            trainstationId = message.GetValueAsGuid("trainstationId");
        }

        public override void Execute()
        {

            GameSession gameSession = (GameSession)session;

            if(gameSession.OwnPlayer.Id == playerId)
            {
                Trainstation trainstation = (Trainstation)gameSession.Map.GetPlaceableById(trainstationId);
                trainstation.Tradeable = tradeable;
            }
        }
    }
}
