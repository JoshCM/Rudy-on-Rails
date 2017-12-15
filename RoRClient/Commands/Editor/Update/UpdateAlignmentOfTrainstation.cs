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
    class UpdateAlignmentOfTrainstation : CommandBase
    {
        private Guid trainstationId;
        private Compass alignment;
        public UpdateAlignmentOfTrainstation(EditorSession session, MessageInformation message) : base(session, message)
        {
            trainstationId = message.GetValueAsGuid("id");
            alignment = (Compass)Enum.Parse(typeof(Compass), message.GetValueAsString("alignment"));
        }

        public override void Execute()
        {
            throw new NotImplementedException();
        }
    }
}
