using RoRClient.ViewModels.Helper;
using System;
using System.ComponentModel;

namespace RoRClient.Models.Base
{
    /// <summary>
    /// Abstrakte Klasse für alle Models
    /// Stellt den PropertyChangedEventHandler bereit
    /// </summary>
    public abstract class ModelBase : ObservableBase, IModel
    {
        

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
