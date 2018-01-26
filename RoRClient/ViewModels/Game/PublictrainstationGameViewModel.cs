using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Commands;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModels.Game
{
    class PublictrainstationGameViewModel : TrainstationGameViewModel
    {
        public PublictrainstationGameViewModel(Trainstation trainstation) : base(trainstation)
        {

        }

        private ICommand activateTradeMenuCommand;
        public ICommand ActivateTradeMenuCommand
        {
            get
            {
                if (activateTradeMenuCommand == null)
                {
                    activateTradeMenuCommand = new ActionCommand(param => ActivateTradeMenu());
                }
                return activateTradeMenuCommand;
            }
        }

        public void ActivateTradeMenu()
        {
            MessageInformation message = new MessageInformation();
            message.PutValue("playerId", GameSession.GetInstance().OwnPlayer.Id);
            message.PutValue("publicTrainstationId", Id);
            GameSession.GetInstance().QueueSender.SendMessage("ActiveTradeReation", message);
        }
    }
}
