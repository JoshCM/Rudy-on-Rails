using RoRClient.Models.Base;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    public class Scripts : ModelBase
    {
        private ObservableCollection<Script> ghostLocoScripts = new ObservableCollection<Script>();
        private TaskFactory taskFactory;

        public Scripts()
        {
            taskFactory = new TaskFactory(TaskScheduler.FromCurrentSynchronizationContext());
        }

        public void AddScript(Script script)
        {
            switch (script.ScriptType)
            {
                case "ghostloco":
                    taskFactory.StartNew(() => ghostLocoScripts.Add(script));
                    break;
                default:
                    break;
            }
        }

        public ObservableCollection<Script> GhostLocoScripts
        {
            get
            {
                return ghostLocoScripts;
            }
        }
    }
}
