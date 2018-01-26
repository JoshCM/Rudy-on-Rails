using Newtonsoft.Json.Linq;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Commands.Base
{
    class CreateStockCommandBase : CommandBase
    {
        Guid stockId;
        Guid trainstationId;
        private int xPos;
        private int yPos;

        /// <summary>
        /// Setzt den Stock einer Trainstation
        /// </summary>
        /// <param name="session"></param>
        /// <param name="messageInformation"></param>
        public CreateStockCommandBase(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            stockId = Guid.Parse(messageInformation.GetValueAsString("stockId"));
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");
            trainstationId = Guid.Parse(messageInformation.GetValueAsString("trainstationId"));
        }

        public override void Execute()
        {
            Square square = session.Map.GetSquare(xPos, yPos);
            Stock stock = new Stock(stockId, square, Compass.EAST, trainstationId);
            square.PlaceableOnSquare = stock;
        }
    }
}
