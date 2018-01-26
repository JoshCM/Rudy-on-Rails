using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Commands;
using RoRClient.ViewModels.Helper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModels.Game
{
    class GameResultViewModel : ViewModelBase
    {
        private UIState uiState;
        private string winningPlayerName;

        public GameResultViewModel(UIState uiState)
        {
            this.uiState = uiState;
            WinningPlayerName = GameSession.GetInstance().WinningPlayer.Name;
        }

        public string WinningPlayerName
        {
            get
            {
                return winningPlayerName;
            }
            set
            {
                if(winningPlayerName != value)
                {
                    winningPlayerName = value;
                    OnPropertyChanged("WinningPlayerName");
                }
            }
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
        }
    }
}
