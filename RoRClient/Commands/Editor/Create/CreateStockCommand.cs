using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Commands.Editor.Create
{
    class CreateStockCommand : CreateStockCommandBase
    {
        public CreateStockCommand(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation) { 
        }
    }
}
