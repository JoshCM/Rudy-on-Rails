using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Commands;
using RoRClient.Views.Editor.Helper;
using RoRClient.Views.Popup;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows;
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
                    saveMapCommand = new ActionCommand(param => SaveMap());
                }
                return saveMapCommand;
            }
        }

        private void SaveMap()
        {
            if(EditorSession.GetInstance().Map.Name == "")
            {
                SendSaveNewMapMessage();
            }
            else
            {
                SendSaveMapMessage();
            }
        }

        private void SendSaveNewMapMessage()
        {
            string newMapName = PopupCreator.AskUserToInputString("Bitte gib einen Namen für die Map ein.").Trim();

            if (RegexValidator.IsValidFilename(newMapName))
            {
                EditorSession editorSession = EditorSession.GetInstance();
                MessageInformation messageInformation = new MessageInformation();
                messageInformation.PutValue("name", newMapName);
                editorSession.QueueSender.SendMessage("SaveNewMap", messageInformation);
            }
            else
            {
                MessageBox.Show("Ungültiger Filename du Opfer!");
            }
        }

        private void SendSaveMapMessage()
        {
            EditorSession editorSession = EditorSession.GetInstance();
            editorSession.QueueSender.SendMessage("SaveMap", new MessageInformation());
        }
    }
}
