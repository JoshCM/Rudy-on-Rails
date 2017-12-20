using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Commands.Base;

namespace RoRClient.Commands.Game.Update
{
    class UpdateLocoSpeedCommand : CommandBase
    {
        public UpdateLocoSpeedCommand(RoRSession session, MessageInformation message) : base (session, message)
        {

        }
        public override void Execute()
        {
            throw new NotImplementedException();
        }
    }
}
