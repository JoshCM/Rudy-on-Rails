using RoRClient.Models.Base;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static RoRClient.Models.Game.Script;

namespace RoRClient.Models.Game
{
    public class Scripts : ModelBase
    {
        private List<Script> ghostLocoScripts = new List<Script>();
        private List<Script> sensorScripts = new List<Script>();

        public Scripts()
        {

        }

        public void AddScript(Script script)
        {
            switch (script.ScriptType)
            {
                case ScriptTypes.GHOSTLOCO:
                    ghostLocoScripts.Add(script);
                    NotifyPropertyChanged("GhostLocoScripts", null, script);
                    break;
                case ScriptTypes.SENSOR:
                    sensorScripts.Add(script);
                    NotifyPropertyChanged("SensorScripts", null, script);
                    break;
                default:
                    break;
            }
            
            
        }

        public List<Script> GhostLocoScripts
        {
            get
            {
                return ghostLocoScripts;
            }
        }

        public List<Script> SensorScripts
        {
            get
            {
                return sensorScripts;
            }
        }

    }
}
