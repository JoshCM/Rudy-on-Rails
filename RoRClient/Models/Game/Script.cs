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
            GHOSTLOCO,
            SENSOR
             
        }

        private string description;
        private string filename;
        private ScriptTypes scriptType;

        public Script(Guid id, string description, ScriptTypes scriptType, string filename)
        {
            this.id = id;
            this.description = description;
            this.scriptType = scriptType;
            this.filename = filename;
        }

        public string Description
        {
            get
            {
                return description;
            }
            set
            {
                if(description != value)
                {
                    description = value;
                    NotifyPropertyChanged("Description");
                }
            }
        }

        public string Filename
        {
            get
            {
                return filename;
            }
            set
            {
                if (filename != value)
                {
                    filename = value;
                    NotifyPropertyChanged("Filename");
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
