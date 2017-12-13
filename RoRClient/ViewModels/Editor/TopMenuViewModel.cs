using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Commands;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModels.Editor
{
    class TopMenuViewModel : ViewModelBase
    {
        private ICommand saveNewMapCommand;
        public ICommand SaveNewMapCommand
        {
            get
            {
                if (saveNewMapCommand == null)
                {
                    saveNewMapCommand = new ActionCommand(param => SendSaveNewMapMessage());
                }
                return saveNewMapCommand;
            }
        }

        private ICommand saveMapCommand;
        public ICommand SaveMapCommand
        {
            get
            {
                if (saveMapCommand == null)
                {
                    saveMapCommand = new ActionCommand(param => SendSaveMapMessage());
                }
                return saveMapCommand;
            }
        }

        private void SaveMap()
        {
            if(EditorSession.GetInstance().Map.Name == "")
            {
                // ToDo: Hier sollte ein Name abgefragt werden!
                SendSaveNewMapMessage();
            }
            else
            {
                SendSaveMapMessage();
            }
        }

        private void SendSaveNewMapMessage()
        {
            // ToDo: Wo kommt der Name her?
            string newMapName = "NeueMap";

            EditorSession editorSession = EditorSession.GetInstance();
            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("name", newMapName);
            editorSession.QueueSender.SendMessage("SaveNewMap", messageInformation);
        }

        private void SendSaveMapMessage()
        {
            EditorSession editorSession = EditorSession.GetInstance();
            editorSession.QueueSender.SendMessage("SaveMap", new MessageInformation());
        }
    }
}
