using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Editor.Other
{
    public class LeaveEditorCommand : CommandBase
    {
        private Guid playerId;

        public LeaveEditorCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            playerId = message.GetValueAsGuid("playerId");
        }

        public override void Execute()
        {
            Boolean deleteEditorSession = false;
            Player player = EditorSession.GetInstance().GetPlayerById(playerId);

            // Wenn der Spieler Host ist oder kein anderer
            if (player == EditorSession.GetInstance().OwnPlayer || player.IsHost)
            {
                deleteEditorSession = true;
            }

            EditorSession.GetInstance().RemovePlayer(player);

            // Dann auch die GameSession löschen
            if (deleteEditorSession)
            {
                EditorSession.GetInstance().DeleteEditorSession();
            }
        }
    }
}
