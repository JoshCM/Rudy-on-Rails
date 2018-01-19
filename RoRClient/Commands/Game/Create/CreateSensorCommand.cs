using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;

namespace RoRClient.Commands.Game.Create
{
    public class CreateSensorCommand : CommandBase

    {
        public CreateSensorCommand(RoRSession session, MessageInformation message) : base(session, message)
        {

        }

        public override void Execute()
        {
            
        }
    }
}
