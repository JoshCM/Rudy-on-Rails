using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;

namespace RoRClient.Commands.Editor.Create
{
    /// <summary>
    /// Command zum Erstellen einer Mine im Editor
    /// </summary>
    public class CreateMineCommand : CreateMineCommandBase
    {
        public CreateMineCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
        }
    }
}
