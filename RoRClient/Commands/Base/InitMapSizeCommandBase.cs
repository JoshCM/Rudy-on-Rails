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
    public class InitMapSizeCommandBase : CommandBase
    {
        private int mapSize;
        public InitMapSizeCommandBase(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            mapSize = messageInformation.GetValueAsInt("mapSize");
        }
        public override void Execute()
        {
            session.Map = new Map(mapSize);
        }
    }
}
