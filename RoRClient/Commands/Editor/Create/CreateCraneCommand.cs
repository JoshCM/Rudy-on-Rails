using RoRClient.Models.Session;
using RoRClient.Communication.DataTransferObject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Commands.Base;

namespace RoRClient.Commands.Editor.Create
{
    class CreateCraneCommand : CreateCraneCommandBase
    {
        public CreateCraneCommand(RoRSession session, MessageInformation messageInformation) : base (session, messageInformation)
        {

        }
    }
}
