﻿using RoRClient.Commands.Base;
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
    class UpdateCartPositionCommand : CommandBase
    {
        private Guid playerId;
        private Guid currentLocoId;
        private int xPos;
        private int yPos;
        private Guid cartId;
        private Compass drivingDirection;


        public UpdateCartPositionCommand(GameSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
            playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));
            drivingDirection = (Compass)Enum.Parse(typeof(Compass), messageInformation.GetValueAsString("drivingDirection"));
            cartId = Guid.Parse(messageInformation.GetValueAsString("cartId"));
            currentLocoId = Guid.Parse(messageInformation.GetValueAsString("currentLocoId"));
        }
        public override void Execute()
        {
            GameSession gameSession = GameSession.GetInstance();

            Square square = gameSession.Map.GetSquare(xPos, yPos);
            Player player = gameSession.GetPlayerById(playerId);

            Loco loco = gameSession.GetLocoById(currentLocoId);

            Cart cart = loco.GetCartById(cartId);
            cart.Speed=loco.Speed;
            cart.Square = square;
            cart.DrivingDirection = drivingDirection;
        }
    }
}
