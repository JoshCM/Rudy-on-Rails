using RoRClient.ViewModel.Helper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModel
{
   class StartViewModel:ViewModelBase
    {
        public UIState uiState;

        public StartViewModel(UIState uiState)
        {
            this.uiState = uiState;
        }

        private ICommand _start2EditorCmd;
        public ICommand startToEditorCommand
        {
            get
            {
                if(_start2EditorCmd == null)
                {
                    _start2EditorCmd = new ActionCommand(e => { uiState.State = "editor"; });
                }
                return _start2EditorCmd;
            }
        }

    }
}
