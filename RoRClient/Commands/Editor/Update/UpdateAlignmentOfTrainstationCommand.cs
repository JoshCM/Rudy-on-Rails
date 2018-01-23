using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Editor.Update
{
    /// <summary>
    /// Nach Rotation einer Trainstation wird deren Ausrichtung angepasst
    /// </summary>
    class UpdateAlignmentOfTrainstationCommand : CommandBase
    {
        private Guid trainstationId;
        private Compass alignment;
        public UpdateAlignmentOfTrainstationCommand(EditorSession session, MessageInformation message) : base(session, message)
        {
            trainstationId = message.GetValueAsGuid("id");
            alignment = (Compass)Enum.Parse(typeof(Compass), message.GetValueAsString("alignment"));
        }

        public override void Execute()
        {
            EditorSession editorSession = (EditorSession)session;
            Trainstation trainstation = (Trainstation)editorSession.Map.GetPlaceableById(trainstationId);
            trainstation.Alignment = alignment;
        }
    }
}
