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

namespace RoRClient.Commands.Editor.Other

{
    /// <summary>
    /// CreateRailCommand für Game
    /// </summary>
    public class StartCommand : CommandBase
    {
        public StartCommand(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {

        }
        public override void Execute()
        {
            EditorSession.GetInstance().Started = true;
        }
    }
}
