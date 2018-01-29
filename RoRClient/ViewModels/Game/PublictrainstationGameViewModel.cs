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
        Publictrainstation publictrainstation;

        public PublictrainstationGameViewModel(Publictrainstation trainstation) : base(trainstation)
        {
            this.publictrainstation = trainstation;
        }

        private ICommand showTradeRelationCommand;
        public ICommand ShowTradeRelationCommand
        {
            get
            {
                if (showTradeRelationCommand == null)
                {
                    showTradeRelationCommand = new ActionCommand(param => ShowTradeRelation());
                }
                return showTradeRelationCommand;
            }
        }

        public void ShowTradeRelation()
        {
            if (!this.Trainstation.Tradeable)
            {
                MapViewModel.GameInteractionsViewModel.CanExchangeResource = true;
            } else
            {
                MapViewModel.GameInteractionsViewModel.CanExchangeResource = false;
            }


            MessageInformation message = new MessageInformation();
            message.PutValue("playerId", GameSession.GetInstance().OwnPlayer.Id);
            message.PutValue("publicTrainstationId", Id);
            GameSession.GetInstance().QueueSender.SendMessage("ShowTradeRelation", message);
        }
    }
}
