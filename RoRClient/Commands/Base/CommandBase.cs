using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Models.Editor;
using RoRClient.Communication.DataTransferObject;

namespace RoRClient.Commands.Base
{
    public abstract class CommandBase : ICommand
    {

        protected EditorSession session;

        public CommandBase(EditorSession session, MessageInformation message)
        {
            this.session = session;
        }

        public void Execute()
        {
            throw new NotImplementedException();
        }
    }
}
