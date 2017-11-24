using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModel.Helper
{
    class ViewModelBase : INotifyPropertyChanged
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
