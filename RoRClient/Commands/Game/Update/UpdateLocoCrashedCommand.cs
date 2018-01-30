using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Game.Update
{
    class UpdateLocoCrashedCommand : CommandBase
    {
        private MessageInformation message;
        public UpdateLocoCrashedCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            this.message = message;
        }

        public override void Execute()
        {
            Guid locoId = message.GetValueAsGuid("currentLocoId");
            Loco crashedLoco = GameSession.GetInstance().GetLocoById(locoId);
            crashedLoco.UpdateSmokeVisibility("Hidden");
            crashedLoco.UpdateExplosionVisibility("Visible");
        }
    }
}
