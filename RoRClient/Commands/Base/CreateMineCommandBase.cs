using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Base
{
    /// <summary>
    /// Command zum Erstellen einer Mine als Base-Command für Editor und Game
    /// </summary>
    public class CreateMineCommandBase : CommandBase
    {
        private Guid mineId;
        private int xPos;
        private int yPos;
        private Compass alignment;
        private GameSession gameSession;

        public CreateMineCommandBase(RoRSession session, MessageInformation message) : base(session, message)
        {
            this.gameSession = (GameSession)session;
            mineId = message.GetValueAsGuid("mineId");
            xPos = message.GetValueAsInt("xPos");
            yPos = message.GetValueAsInt("yPos");
            alignment = (Compass)Enum.Parse(typeof(Compass), message.GetValueAsString("alignment"));

        }

        public override void Execute()
        {
            Square square = session.Map.GetSquare(xPos, yPos);
            Mine mine = new Mine(mineId, square, alignment);
            gameSession.addMine(mine);
            Rail rail = (Rail)square.PlaceableOnSquare;
            rail.PlaceableOnRail = mine;

        }
    }
}
