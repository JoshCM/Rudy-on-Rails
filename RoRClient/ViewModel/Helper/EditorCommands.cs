﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModel.Helper
{
    public static class EditorCommands
    {
        private static ICommand exit = new Exit();
        public static ICommand Exit { get { return exit; } }
    }
}
