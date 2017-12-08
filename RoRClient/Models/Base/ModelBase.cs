using RoRClient.ViewModels.Helper;
using System;
using System.ComponentModel;

namespace RoRClient.Models.Base
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

        protected Guid id;
        public Guid Id
        {
            get
            {
                return id;
            }
        }
    }
}
