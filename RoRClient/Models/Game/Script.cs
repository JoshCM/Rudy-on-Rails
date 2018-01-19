using RoRClient.Models.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    public class Script : ModelBase
    {
        public enum ScriptTypes
        {
            GHOSTLOCO
        }

        private string name;
        private string scriptName;
        private ScriptTypes scriptType;

        public Script(Guid id, string name, ScriptTypes scriptType, string scriptName)
        {
            this.id = id;
            this.name = name;
            this.scriptType = scriptType;
            this.scriptName = scriptName;
        }

        public string Name
        {
            get
            {
                return name;
            }
            set
            {
                if(name != value)
                {
                    name = value;
                    NotifyPropertyChanged("Name");
                }
            }
        }

        public string ScriptName
        {
            get
            {
                return scriptName;
            }
            set
            {
                if (scriptName != value)
                {
                    scriptName = value;
                    NotifyPropertyChanged("ScriptName");
                }
            }
        }

        public ScriptTypes ScriptType
        {
            get
            {
                return scriptType;
            }
            set
            {
                if (scriptType != value)
                {
                    scriptType = value;
                    NotifyPropertyChanged("ScriptType");
                }
            }
        }
    }
}
