using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Commands.Base;
using RoRClient.Models.Session;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using Newtonsoft.Json.Linq;

namespace RoRClient.Commands.Game.Update

{
    /// <summary>
    /// CreateRailCommand für Editor
    /// </summary>
    public class CreateSignalsCommand : CreateSignalsCommandBase
    {
        public CreateSignalsCommand(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {

        }
    }
}
