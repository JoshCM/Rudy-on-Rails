using RoRClient.Models.Game;
using RoRClient.ViewModels.Helper;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    public class ScriptsViewModel : ViewModelBase
    {
        private TaskFactory taskFactory;
        private Scripts scripts;
        private ObservableCollection<Script> ghostLocoScripts = new ObservableCollection<Script>();
        private ObservableCollection<Script> sensorScripts = new ObservableCollection<Script>();

        public ScriptsViewModel(Scripts scripts, TaskFactory taskFactory)
        {
            this.taskFactory = taskFactory;
            this.scripts = scripts;
            scripts.PropertyChanged += OnScriptsChanged;
        }

        public void OnScriptsChanged(object sender, PropertyChangedEventArgs e)
        {
            PropertyChangedExtendedEventArgs<Script> eventArgs = (PropertyChangedExtendedEventArgs<Script>)e;
            if (e.PropertyName == "GhostLocoScripts")
            {
                taskFactory.StartNew(() => ghostLocoScripts.Add(eventArgs.NewValue));
            }

            if (e.PropertyName == "SensorScripts")
            {
                taskFactory.StartNew(() => sensorScripts.Add(eventArgs.NewValue));
            }
        }

        public ObservableCollection<Script> GhostLocoScripts
        {
            get
            {
                return ghostLocoScripts;
            }
        }

        public ObservableCollection<Script> SensorScripts
        {
            get
            {
                return sensorScripts;
            }
        }
    }
}
