using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModel.Helper
{
    /// <summary>
    /// Extended Klasse um beim PropertyChanged den alten und neuen Wert mitzugeben
    /// </summary>
    /// <typeparam name="T"></typeparam>
    public interface INotifyPropertyChangedExtended<T>
    {
        event PropertyChangedExtendedEventHandler<T> PropertyChanged;
    }

    public delegate void PropertyChangedExtendedEventHandler<T>(object sender, PropertyChangedExtendedEventArgs<T> e);
}
