using RoRClient.Models.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    public class Signals : ModelBase
    {
        private int autoSwitchIntervalInSeconds;
        private int penalty;
        private int switchCost;

        private bool northSignalActive;
        private bool eastSignalActive;
        private bool southSignalActive;
        private bool westSignalActive;

        private bool exists;

        public Signals(Guid id)
        {
            this.id = id;
            Exists = false;
        }

        #region Properties
        /// <summary>
        /// Wenn mindestens ein Signal auf der Rail aktiv ist, dann ist das hier true
        /// </summary>
        public bool Exists
        {
            get
            {
                return exists;
            }
            set
            {
                exists = value;
                NotifyPropertyChanged("Exists");
            }
        }

        public int AutoSwitchIntervalInSeconds
        {
            get
            {
                return autoSwitchIntervalInSeconds;
            }
            set
            {
                if (autoSwitchIntervalInSeconds != value)
                {
                    autoSwitchIntervalInSeconds = value;
                    NotifyPropertyChanged("AutoSwitchIntervalInSeconds");
                }
            }
        }

        public int Penalty
        {
            get
            {
                return penalty;
            }
            set
            {
                if (penalty != value)
                {
                    penalty = value;
                    NotifyPropertyChanged("Penalty");
                }
            }
        }

        public int SwitchCost
        {
            get
            {
                return switchCost;
            }
            set
            {
                if (switchCost != value)
                {
                    switchCost = value;
                    NotifyPropertyChanged("SwitchCost");
                }
            }
        }

        public bool NorthSignalActive
        {
            get
            {
                return northSignalActive;
            }
            set
            {
                if (northSignalActive != value)
                {
                    northSignalActive = value;
                    NotifyPropertyChanged("NorthSignalActive");
                }
            }
        }

        public bool SouthSignalActive
        {
            get
            {
                return southSignalActive;
            }
            set
            {
                if (southSignalActive != value)
                {
                    southSignalActive = value;
                    NotifyPropertyChanged("SouthSignalActive");
                }
            }
        }

        public bool EastSignalActive
        {
            get
            {
                return eastSignalActive;
            }
            set
            {
                if (eastSignalActive != value)
                {
                    eastSignalActive = value;
                    NotifyPropertyChanged("EastSignalActive");
                }
            }
        }

        public bool WestSignalActive
        {
            get
            {
                return westSignalActive;
            }
            set
            {
                if (westSignalActive != value)
                {
                    westSignalActive = value;
                    NotifyPropertyChanged("WestSignalActive");
                }
            }
        }
        #endregion
    }
}
