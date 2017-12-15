using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Commands.Editor.Update
{
    class UpdateNameOfMapCommand : CommandBase
    {
        private string mapName;

        public UpdateNameOfMapCommand(EditorSession session, MessageInformation message) : base(session, message)
        {
            mapName = message.GetValueAsString("mapName");
        }

        public override void Execute()
        {
            Map map = EditorSession.GetInstance().Map;
            map.Name = mapName;
        }
    }
}
