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

namespace RoRClient.ViewModels.Game
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



        private ICommand leaveGameCommand;
        public ICommand LeaveGameCommand
        {
            get
            {
                if (leaveGameCommand == null)
                {
                    leaveGameCommand = new ActionCommand(param => LeaveGame());
                }
                return leaveGameCommand;
            }
        }

        private void LeaveGame()
        {
            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("playerId", GameSession.GetInstance().OwnPlayer.Id);
            messageInformation.PutValue("isHost", GameSession.GetInstance().OwnPlayer.IsHost);
            GameSession.GetInstance().QueueSender.SendMessage("LeaveGame", messageInformation);
            SoundManager.StopSounds();
            SoundManager.DeleteSounds();

        }


    }
}
