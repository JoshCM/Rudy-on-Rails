using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    /// <summary>
    /// Abstrakte Klasse die jedes Model erbt
    /// </summary>
    public abstract class ModelBase : IModel
    {
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
