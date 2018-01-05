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
    /// Command zum Ändern der Richtung der Mine
    /// </summary>
    class UpdateAlignmentOfMineCommand : CommandBase
    {
        private Compass alignment;
        private Guid mineId;
        private Guid railId;

        public UpdateAlignmentOfMineCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            mineId = message.GetValueAsGuid("mineId");
            railId = message.GetValueAsGuid("railId");
            alignment = (Compass)Enum.Parse(typeof(Compass), message.GetValueAsString("alignment"));
        }

        public override void Execute()
        {
            EditorSession session = EditorSession.GetInstance();
            Rail rail = (Rail)session.Map.GetPlaceableById(railId);
            Mine mine = (Mine)rail.PlaceableOnRail;
            mine.Alignment = alignment;
        }
    }
}
