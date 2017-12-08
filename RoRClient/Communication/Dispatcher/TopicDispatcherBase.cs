using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Commands.Base;

namespace RoRClient.Communication.Dispatcher
{
    class TopicDispatcherBase
    {
        protected const string COMMAND_TYPE_PREFIX = "";
        protected const string COMMAND_CLASS_SUFFIX = "Command";
        protected const string CREATE = "Create";
        protected const string UPDATE = "Update";
        protected const string DELETE = "Delete";
        
        public void Dispatch(string request, string message)
        {
            string pathToCommand = "";

            if (request.StartsWith("Create"))
            {
                pathToCommand = COMMAND_TYPE_PREFIX + CREATE + request + COMMAND_CLASS_SUFFIX;
            }else
            if (request.StartsWith("Update"))
            {
                pathToCommand = COMMAND_TYPE_PREFIX + UPDATE + request + COMMAND_CLASS_SUFFIX;
            }else
            if(request.StartsWith("Delete"))
            {
                pathToCommand = COMMAND_TYPE_PREFIX + DELETE + request + COMMAND_CLASS_SUFFIX;
            }
            try
            {
                Type commandType = Type.GetType(pathToCommand);
                ICommand command = (ICommand)Activator.CreateInstance(commandType, message);
                command.execute();
            }
            catch(FormatException)
            {
                Console.WriteLine("Request entspricht nicht dem gueltigen Format: (beispielsweise \"CreateRail\")");
            }

        }
    }
}
