using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static RoRClient.Models.Game.Script;

namespace RoRClient.Commands.Game.Create
{
    class CreateScriptCommand : CommandBase
    {
        private Guid id;
        private Guid playerId;
        private string description;
        private ScriptTypes scriptType;
        private string filename;

        public CreateScriptCommand(GameSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            id = Guid.Parse(messageInformation.GetValueAsString("id"));
            playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));
            description = messageInformation.GetValueAsString("description");
            scriptType = (ScriptTypes)Enum.Parse(typeof(ScriptTypes), messageInformation.GetValueAsString("scriptType"));
            filename = messageInformation.GetValueAsString("filename");
        }

        public override void Execute()
        {
            GameSession gameSession = (GameSession)session;

            // Nur ein Script erzeugen, falls Default Script (leere Guid) oder ein Script des eigenen Spielers
            if (playerId == Guid.Empty || playerId == gameSession.OwnPlayer.Id)
            {
                Script script = new Script(id, description, scriptType, filename);
                gameSession.Scripts.AddScript(script);
            }
        }
    }
}
