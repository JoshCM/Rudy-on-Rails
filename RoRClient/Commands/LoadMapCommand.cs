using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Commands
{
    class LoadMapCommand : CommandBase
    {

        public LoadMapCommand(RoRSession session, MessageInformation message) : base(session, message)
        {

        }


        public override void Execute()
        {

        }
    }
}
