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
        private string bildstring;
        private ActionCommand testCommand;
        public ActionCommand TestCommand
        {
            get
            {
                if (testCommand == null)
                {
                    // 1. Argument: Kommando-Effekt (Execute), 2. Argument: Bedingung "Kommando aktiv?" (CanExecute)
                    testCommand = new ActionCommand(dummy => this.TestMethode(dummy));
                }
                return testCommand;
            }
        }

        public string BildString {get; set;}

        // Hilfsmethode für AddEinkaufCommand
        private void TestMethode(object dummy)
        {
            Console.WriteLine(dummy.ToString());
        }
    }
}
