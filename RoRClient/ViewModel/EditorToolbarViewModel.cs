using RoRClient.ViewModel.Helper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModel
{
    class EditorToolbarViewModel : ViewModelBase
    {
        private ActionCommand testCommand;
        public ActionCommand TestCommand
        {
            get
            {
                if (testCommand == null)
                {
                    // 1. Argument: Kommando-Effekt (Execute), 2. Argument: Bedingung "Kommando aktiv?" (CanExecute)
                    testCommand = new ActionCommand(dummy => this.TestMethode());
                }
                return testCommand;
            }
        }

        // Hilfsmethode für AddEinkaufCommand
        private void TestMethode()
        {

        }
    }
}
