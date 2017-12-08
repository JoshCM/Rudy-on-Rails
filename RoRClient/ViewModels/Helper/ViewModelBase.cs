using System.ComponentModel;
using System.Runtime.CompilerServices;

namespace RoRClient.ViewModels.Helper
{
    public class ViewModelBase : INotifyPropertyChanged
    {
        /* 
         * Standard-Basisklasse für ViewModels 
         *  
         */

        public event PropertyChangedEventHandler PropertyChanged;

        //[CallerMemberName] erlaubt uns im ViewModel OnPropertyChanged();  
        //anstatt von OnPropertyChanged("SomeProperty"); zu schreiben 
        //so werden String Konstanten im Code vermieden! 
        protected virtual void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
