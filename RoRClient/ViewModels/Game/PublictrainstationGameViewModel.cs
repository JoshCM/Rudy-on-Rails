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

        private ICommand selectPublictrainstationCommand;
        public ICommand SelectPublictrainstationCommand
        {
            get
            {
                if (selectPublictrainstationCommand == null)
                {
                    selectPublictrainstationCommand = new ActionCommand(param => SelectPublictrainstation());
                }
                return selectPublictrainstationCommand;
            }
        }

        public void SelectPublictrainstation()
        {
            MessageInformation message = new MessageInformation();
            message.PutValue("playerId", GameSession.GetInstance().OwnPlayer.Id);
            message.PutValue("publicTrainstationId", publictrainstation.Id);
            GameSession.GetInstance().QueueSender.SendMessage("SelectPublictrainstation", message);
        }
    }
}
