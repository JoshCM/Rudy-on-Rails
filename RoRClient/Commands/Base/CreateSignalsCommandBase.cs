using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Commands.Base;
using RoRClient.Models.Session;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using Newtonsoft.Json.Linq;

namespace RoRClient.Commands.Base
{
    /// <summary>
    /// Basis-Klasse für CreateRailCommand: Wird für Game und Editor gleichermaßen genutzt
    /// </summary>
    public class CreateSignalsCommandBase : CommandBase
    {
        protected Guid signalsId;
        protected int xPos;
        protected int yPos;

        protected int autoSwitchIntervalInSeconds;
        protected int penalty;
        protected int switchCost;

        protected bool northSignalActive;
        protected bool eastSignalActive;
        protected bool southSignalActive;
        protected bool westSignalActive;

        public CreateSignalsCommandBase(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            signalsId = messageInformation.GetValueAsGuid("signalsId");
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");

            autoSwitchIntervalInSeconds = messageInformation.GetValueAsInt("autoSwitchIntervalInSeconds");
            penalty = messageInformation.GetValueAsInt("penalty");
            switchCost = messageInformation.GetValueAsInt("switchCost");
            northSignalActive = messageInformation.GetValueAsBool("northSignalActive");
            eastSignalActive = messageInformation.GetValueAsBool("eastSignalActive");
            southSignalActive = messageInformation.GetValueAsBool("southSignalActive");
            westSignalActive = messageInformation.GetValueAsBool("westSignalActive");
        }

        public override void Execute()
        {
            Square square = session.Map.GetSquare(xPos, yPos);
            Rail rail = (Rail)square.PlaceableOnSquare;
            Signals signals = new Signals(signalsId)
            {
                AutoSwitchIntervalInSeconds = autoSwitchIntervalInSeconds,
                Penalty = penalty,
                SwitchCost = switchCost,
                NorthSignalActive = northSignalActive,
                EastSignalActive = eastSignalActive,
                SouthSignalActive = southSignalActive,
                WestSignalActive = westSignalActive
            };
            rail.Signals = signals;
        }
    }
}
