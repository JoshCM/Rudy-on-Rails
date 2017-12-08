using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModels.Commands
{
    public static class EditorCommands
    {
        private static ICommand exit = new ExitCommand();
        public static ICommand Exit { get { return exit; } }
    }
}
