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
    class UpdateConfigOfSignalsCommand : CommandBase
    {
        protected Guid signalsId;
        protected int xPos;
        protected int yPos;

        protected int autoSwitchIntervalInSeconds;
        protected int penalty;
        protected int switchCost;

        public UpdateConfigOfSignalsCommand(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            signalsId = messageInformation.GetValueAsGuid("signalsId");
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");

            autoSwitchIntervalInSeconds = messageInformation.GetValueAsInt("autoSwitchIntervalInSeconds");
            penalty = messageInformation.GetValueAsInt("penalty");
            switchCost = messageInformation.GetValueAsInt("switchCost");
        }

        public override void Execute()
        {
            Square square = session.Map.GetSquare(xPos, yPos);
            Rail rail = (Rail)square.PlaceableOnSquare;
            Signals signals = rail.Signals;

            signals.AutoSwitchIntervalInSeconds = autoSwitchIntervalInSeconds;
            signals.Penalty = penalty;
            signals.SwitchCost = switchCost;
        }
    }
}
