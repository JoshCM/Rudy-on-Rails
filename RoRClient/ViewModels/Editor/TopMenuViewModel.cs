using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Sound;
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
        private String sound = "Sound Aus";
        public String Sound
        {
            get
            {
                return sound;
            }
            set
            {
                sound = value;
                OnPropertyChanged("Sound");
            }
        }

        private ICommand openRulesPopUpCommand;
        public ICommand OpenRulesPopUpCommand
        {
            get
            {
                if (openRulesPopUpCommand == null)
                {
                    openRulesPopUpCommand = new ActionCommand(param => OpenRules());
                }
                return openRulesPopUpCommand;
            }
        }

        private void OpenRules()
        {
            RulesPopup popup = new RulesPopup();
            popup.ShowDialog();
        }

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

        private bool muteSounds;
        public bool MuteSounds
        {
            get
            {
                return muteSounds;
            }
            set
            {
                if (muteSounds == value) return;

                muteSounds = value;
                Sound = muteSounds ? "Sound An" : "Sound Aus";
                if (muteSounds)
                {
                    SoundManager.StopSounds();
                }
                else
                {
                    SoundManager.PlaySounds();
                }

                OnPropertyChanged("MuteSound");
            }
        }

        /// <summary>
        /// Wenn die Map schon einen Namen hat, dann wird ein einfacher SaveMapCommand geschickt
        /// Wenn nicht, dann wird der User nach einem Namen gefragt
        /// </summary>
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

        /// <summary>
        /// Fragt den User über ein Popup, welchen Namen die Map nach dem Speichern bekommen soll
        /// Sendet anschließend den Comand dafür an den Server
        /// </summary>
        private void SendSaveNewMapMessage()
        {
            string newMapName = PopupCreator.AskUserToInputString("Bitte gib einen Namen für die Map ein.").Trim();

            // Fenster wurde über Abbrechen geschlossen
            if(newMapName == "")
            {
                return;
            }

            if (RegexValidator.IsValidFilename(newMapName))
            {
                EditorSession editorSession = EditorSession.GetInstance();
                MessageInformation messageInformation = new MessageInformation();
                messageInformation.PutValue("name", newMapName);
                editorSession.QueueSender.SendMessage("SaveNewMap", messageInformation);
            }
            else
            {
                MessageBox.Show("Ungültiger Filename!");
            }
        }

        private void SendSaveMapMessage()
        {
            EditorSession editorSession = EditorSession.GetInstance();
            editorSession.QueueSender.SendMessage("SaveMap", new MessageInformation());
        }

        private ICommand leaveEditorCommand;
        public ICommand LeaveEditorCommand
        {
            get
            {
                if (leaveEditorCommand == null)
                {
                    leaveEditorCommand = new ActionCommand(param => LeaveEditor());
                }
                return leaveEditorCommand;
            }
        }

        private void LeaveEditor()
        {
            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("playerId", EditorSession.GetInstance().OwnPlayer.Id);
            messageInformation.PutValue("isHost", EditorSession.GetInstance().OwnPlayer.IsHost);
            EditorSession.GetInstance().QueueSender.SendMessage("LeaveEditor", messageInformation);
            SoundManager.StopSounds();
            SoundManager.DeleteSounds();
        }
    }
}
