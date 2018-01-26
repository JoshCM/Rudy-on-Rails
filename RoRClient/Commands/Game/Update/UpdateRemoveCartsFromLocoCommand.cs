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
    class UpdateRemoveCartsFromLocoCommand : CommandBase
    {
        private MessageInformation message;
        public UpdateRemoveCartsFromLocoCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            this.message = message;
        }

        public override void Execute()
        {
            Guid locoId = message.GetValueAsGuid("locoId");
            Loco tempLoco=GameSession.GetInstance().GetLocoById(locoId);
            tempLoco.RemoveCartsExceptInitial();
            
        }
    }
}
