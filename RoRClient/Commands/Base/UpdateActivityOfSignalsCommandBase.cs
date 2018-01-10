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
    public class UpdateActivityOfSignalsCommandBase : CommandBase
    {
        protected Guid signalsId;
        protected int xPos;
        protected int yPos;

        protected bool northSignalActive;
        protected bool eastSignalActive;
        protected bool southSignalActive;
        protected bool westSignalActive;

        public UpdateActivityOfSignalsCommandBase(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {
            signalsId = messageInformation.GetValueAsGuid("signalsId");
            xPos = messageInformation.GetValueAsInt("xPos");
            yPos = messageInformation.GetValueAsInt("yPos");

            northSignalActive = messageInformation.GetValueAsBool("northSignalActive");
            eastSignalActive = messageInformation.GetValueAsBool("eastSignalActive");
            southSignalActive = messageInformation.GetValueAsBool("southSignalActive");
            westSignalActive = messageInformation.GetValueAsBool("westSignalActive");
        }

        public override void Execute()
        {
            Square square = session.Map.GetSquare(xPos, yPos);
            Rail rail = (Rail)square.PlaceableOnSquare;
            Signals signals = rail.Signals;

            signals.NorthSignalActive = northSignalActive;
            signals.SouthSignalActive = southSignalActive;
            signals.EastSignalActive = eastSignalActive;
            signals.WestSignalActive = westSignalActive;
        }
    }
}
