using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;

namespace RoRClient.Commands.Game.Other
{
    /// <summary>
    /// Command zum Deaktivieren/Aktivieren von Sensoren auf der Map
    /// </summary>
    public class SwitchSensorCommand : CommandBase
    {
        private bool active;
        private bool wasActive;
        private Guid railId;

        public SwitchSensorCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            active = message.GetValueAsBool("active");
            wasActive = message.GetValueAsBool("wasActive");
            railId = message.GetValueAsGuid("railId");

        }

        public override void Execute()
        {
            Rail rail = (Rail)session.Map.GetPlaceableById(railId);
            Sensor sensor = rail.Sensor;
            sensor.Active = active;
            sensor.WasActive = wasActive;
        }
    }
}
