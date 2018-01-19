using System;
using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;

namespace RoRClient.Communication.Dispatcher
{
    /// <summary>
    /// Klasse zum Verarbeiten der Messages des Topic-Receivers
    /// </summary>
    public class TopicDispatcherBase
    {
        // COMMAND_TYPE ist entweder Editor oder Game (siehe Unterklassen)
        protected string COMMAND_TYPE_PREFIX = "";
        protected const string COMMAND_CLASS_SUFFIX = "Command";
        protected const string CREATE = "Create.";
        protected const string UPDATE = "Update.";
        protected const string DELETE = "Delete.";
        protected const string OTHER = "Other.";
	    protected const string MOVE = "Move.";
		protected RoRSession session;
        
        public void Dispatch(string request, MessageInformation message)
        {
            // Gibt den kompletten Pfad zum Command an
            string pathToCommand = "";

            if (request.StartsWith("Create"))
            {
                pathToCommand = COMMAND_TYPE_PREFIX + CREATE + request + COMMAND_CLASS_SUFFIX;
            }
            else if (request.StartsWith("Update"))
            {
                pathToCommand = COMMAND_TYPE_PREFIX + UPDATE + request + COMMAND_CLASS_SUFFIX;
            }
            else if(request.StartsWith("Delete"))
            {
                pathToCommand = COMMAND_TYPE_PREFIX + DELETE + request + COMMAND_CLASS_SUFFIX;
            }
            else if (request.StartsWith("Move"))
            {
                pathToCommand = COMMAND_TYPE_PREFIX + MOVE + request + COMMAND_CLASS_SUFFIX;
            }
            else 
            {
                pathToCommand = COMMAND_TYPE_PREFIX + OTHER + request + COMMAND_CLASS_SUFFIX;
            }
            try
            {
                Type commandType = Type.GetType(pathToCommand);
                Console.WriteLine("Command von Server: " + pathToCommand);
                // nach commandType müssen die genauen Parameter für den Konstruktor mitgegeben werden (siehe CommandBase)
                ICommand command = (ICommand)Activator.CreateInstance(commandType, session, message);
                command.Execute();
            }
            catch(FormatException)
            {
                Console.WriteLine("Request entspricht nicht dem gueltigen Format: (beispielsweise \"CreateRail\")");
            }

        }
    }
}
