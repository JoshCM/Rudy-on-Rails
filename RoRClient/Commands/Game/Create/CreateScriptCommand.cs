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
    class CreateScriptCommand : CommandBase
    {
        private Guid id;
        private Guid playerId;
        private string name;
        private string scriptType;
        private string scriptName;

        public CreateScriptCommand(GameSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            id = Guid.Parse(messageInformation.GetValueAsString("id"));
            playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));
            name = messageInformation.GetValueAsString("name");
            scriptType = messageInformation.GetValueAsString("scriptType");
            scriptName = messageInformation.GetValueAsString("scriptName");
        }

        public override void Execute()
        {
            GameSession gameSession = (GameSession)session;

            // Nur ein Script erzeugen, falls Default Script (leere Guid) oder ein Script des eigenen Spielers
            if (playerId == Guid.Empty || playerId == gameSession.OwnPlayer.Id)
            {
                Script script = new Script(id, name, scriptType, scriptName);
                gameSession.Scripts.AddScript(script);
            }
        }
    }
}
