using System;

namespace RoRClient.ViewModels.Helper
{
    class UiChangedEventArgs : EventArgs
    {
        public readonly string Statename;

        public UiChangedEventArgs(string Statename)
        {
            this.Statename = Statename;
        }
    }
    class UIState
    {
        public event EventHandler<UiChangedEventArgs> OnUiStateChanged;

        private string _CurrentStateName;
        public string State
        {
            get
            {
                return _CurrentStateName;
            }
            set
            {
                if (_CurrentStateName != value)
                {
                    Console.WriteLine("UIState: switch from " + _CurrentStateName + " to " + value);
                    _CurrentStateName = value;
                    OnUiStateChanged(this, new UiChangedEventArgs(_CurrentStateName));
                }
            }
        }
    }
}
