using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Commands.Editor.Delete
{
    public class DeletePlaceableCommand : DeletePlaceableCommandBase
    {
        public DeletePlaceableCommand(EditorSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
        }
    }
}
