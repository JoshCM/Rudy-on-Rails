using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    /// <summary>
    /// Interface für die ModelBase um eine eindeutige Identifikation zu halten
    /// </summary>
    public interface IModel
    {
        Guid Id
        {
            get;
        }
    }
}
