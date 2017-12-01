using RoRClient.ViewModel.Helper;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    public abstract class ModelBase : IModel, INotifyPropertyChanged
    {
        #region PropertyChanged
        public event PropertyChangedEventHandler PropertyChanged;
        public virtual void NotifyPropertyChanged(string propertyName)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
            }
        }

        public virtual void OnPropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            PropertyChanged?.Invoke(sender, e);
        }

        protected void NotifyPropertyChanged<T>(string propertyName, T oldvalue, T newvalue)
        {
            OnPropertyChanged(this, new PropertyChangedExtendedEventArgs<T>(propertyName, oldvalue, newvalue));
        }
        #endregion

        public ModelBase()
        {
            id = Guid.NewGuid();
        }

        private Guid id;
        public Guid Id
        {
            get
            {
                return id;
            }
        }
    }
}
