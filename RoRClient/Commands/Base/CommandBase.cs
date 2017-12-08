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
        // Hier muss noch auf generelle Session umgestellt werden (Game oder Editor), siehe auch Konstruktor
        protected EditorSession session;

        public CommandBase(EditorSession session, MessageInformation message)
        {
            this.session = session;
        }

        public void Execute()
        {

        }
    }
}
