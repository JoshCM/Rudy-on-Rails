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

        public Scripts()
        {

        }

        public void AddGhostLocoScript(Script script)
        {
            ghostLocoScripts.Add(script);
            NotifyPropertyChanged("GhostLocoScripts");
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
