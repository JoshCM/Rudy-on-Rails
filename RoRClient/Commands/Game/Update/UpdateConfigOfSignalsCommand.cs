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
    public class UpdateConfigOfSignalsCommand : UpdateConfigOfSignalsCommandBase
    {
        public UpdateConfigOfSignalsCommand(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {

        }
    }
}
