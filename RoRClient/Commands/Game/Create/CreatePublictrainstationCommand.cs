using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;
using Newtonsoft.Json.Linq;

namespace RoRClient.Commands.Game.Create
{
    class CreatePublictrainstationCommand : CreatePublictrainstationCommandBase
    {
        /// <summary>
        /// Setzt die PublicTrainstation und ihre zugehörigen Rails
        /// </summary>
        /// <param name="session"></param>
        /// <param name="messageInformation"></param>
        public CreatePublictrainstationCommand(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            
        }
    }
}
