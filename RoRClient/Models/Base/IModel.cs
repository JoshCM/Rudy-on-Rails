using System;

namespace RoRClient.Models.Base
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
