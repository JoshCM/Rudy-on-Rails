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

        public void AddGhostLocoScript(Script script)
        {
            taskFactory.StartNew(() => ghostLocoScripts.Add(script));
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
