using RoRClient.Models.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Model für Sensor
    /// </summary>
    public class Sensor : InteractiveGameObject
    {
        private Guid railId;
        private Guid playerId;
        private bool active;
        private bool wasActive;

        public Sensor(Square square, Guid railId, Guid playerId) : base(square)
        {
            this.railId = railId;
            this.playerId = playerId;
            this.active = false;
            this.wasActive = false;
        }

        public bool WasActive
        {
            get
            {
                return wasActive;
            }
            set
            {
                wasActive = value;
                NotifyPropertyChanged("WasActive");
            }
             
        }

        public bool Active
        {
            get
            {
                return active;
            }
            set
            {
                active = value;
                NotifyPropertyChanged("Active");
            }
        }

        public Guid PlayerId
        {
            get
            {
                return playerId;
            }
        }
    }
}
