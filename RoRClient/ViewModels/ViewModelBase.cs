using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels
{
    /// <summary>
    /// Base Klasse für ViewModels, damit alle das INotifyPropertyChanged Interface besitzen
    /// </summary>
    public class ViewModelBase : NotifyPropertyChangedBase
    {
        private ViewConstants viewConstants;

        public ViewConstants ViewConstants
        {
            get
            {
                return ViewConstants.Instance;
            }
        }
    }
}
