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
    class UpdateAlignmentOfStockCommand : CommandBase
    {
        private Guid stockId;
        private Compass alignment;
        public UpdateAlignmentOfStockCommand(EditorSession session, MessageInformation message) : base(session, message)
        {
            stockId = message.GetValueAsGuid("id");
            alignment = (Compass)Enum.Parse(typeof(Compass), message.GetValueAsString("alignment"));
        }

        public override void Execute()
        {
            EditorSession editorSession = (EditorSession)session;
            Stock stock = (Stock)editorSession.Map.GetPlaceableById(stockId);
            stock.Alignment = alignment;
        }
    }
}
