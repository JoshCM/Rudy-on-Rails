using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Models.Session;
using RoRClient.Communication.DataTransferObject;

namespace RoRClient.Commands.Base
{
    /// <summary>
    /// Basis-Klasse für alle Commands
    /// </summary>
    public abstract class CommandBase : ICommand
    {
        protected RoRSession session;

        public CommandBase(RoRSession session, MessageInformation message)
        {
            this.session = session;
        }

        public abstract void Execute();
    }
}
