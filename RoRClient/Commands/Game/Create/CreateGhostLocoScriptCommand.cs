using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Commands.Game.Create
{
    class CreateGhostLocoScriptCommand : CommandBase
    {
        private Guid id;
        private string name;
        private string scriptName;

        public CreateGhostLocoScriptCommand(GameSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            id = Guid.Parse(messageInformation.GetValueAsString("id"));
            name = messageInformation.GetValueAsString("name");
            scriptName = messageInformation.GetValueAsString("scriptName");
        }

        public override void Execute()
        {
            GameSession gameSession = (GameSession)session;
            Script script = new Script(id, name, scriptName);
            gameSession.Scripts.AddGhostLocoScript(script);
        }
    }
}
