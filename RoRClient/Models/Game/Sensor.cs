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
    public class Sensor : ModelBase
    {
        private Guid railId;
        private bool active;

        public Sensor(Guid railId)
        {
            this.railId = railId;
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
    }
}
