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

        public Sensor(Square square, Guid railId, Guid playerId) : base(square)
        {
            this.railId = railId;
            this.playerId = playerId;
            this.active = false;
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
