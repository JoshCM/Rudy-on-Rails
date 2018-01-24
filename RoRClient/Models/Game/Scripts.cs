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

        public Scripts()
        {

        }

        public void AddScript(Script script)
        {
            switch (script.ScriptType)
            {
                case ScriptTypes.GHOSTLOCO:
                    ghostLocoScripts.Add(script);
                    break;
                default:
                    break;
            }
            NotifyPropertyChanged("GhostLocoScripts", null, script);
        }

        public List<Script> GhostLocoScripts
        {
            get
            {
                return ghostLocoScripts;
            }
        }
    }
}
