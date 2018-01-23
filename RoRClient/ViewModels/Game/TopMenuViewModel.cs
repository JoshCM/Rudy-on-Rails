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

namespace RoRClient.ViewModels.Game
{
    class TopMenuViewModel : ViewModelBase
    {
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
        }
    }
}
