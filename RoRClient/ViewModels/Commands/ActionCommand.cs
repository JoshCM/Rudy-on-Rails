﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModels.Commands
{
    class ActionCommand : ICommand
    {
        private readonly Action<object> exec;
        private readonly Predicate<object> canExec;

        public ActionCommand(Action<object> exec) : this(exec, null) { }
        public ActionCommand(Action<object> exec, Predicate<object> canExec)
        {
            if (exec == null)
            {
                throw new ArgumentNullException("execute");
            }
            this.exec = exec;
            this.canExec = canExec;
        }

        public bool CanExecute(object param)
        {
            return canExec == null ? true : canExec(param);
        }

        public event EventHandler CanExecuteChanged
        {
            add { CommandManager.RequerySuggested += value; }
            remove { CommandManager.RequerySuggested -= value; }
        }

        public void Execute(object param)
        {
            exec(param);
        }
    }
}
