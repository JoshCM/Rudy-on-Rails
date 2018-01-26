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
    class StockGameViewModel : CanvasGameViewModel
    {
        Stock stock;
        public StockGameViewModel(Stock stock) : base(stock.Id)
        {
            this.stock = stock;
            this.SquarePosX = stock.Square.PosX;
            this.SquarePosY = stock.Square.PosY;
        }

        public Stock Stock
        {
            get { return stock; }
        }

        /// <summary>
        /// Auswählen/Selektieren von ViewModels
        /// </summary>
        private ICommand selectedStockObjectCommand;
        public ICommand SelectedStockObjectCommand
        {
            get
            {
                if (selectedStockObjectCommand == null)
                {
                    selectedStockObjectCommand = new ActionCommand(param => SelectStockObject());
                }
                return selectedStockObjectCommand;
            }
        }

        /// <summary>
        /// EditorObject (Rail etc.) ausgewählt + Quicknavigation anzeigen
        /// </summary>
        public void SelectStockObject()
        {
            RoRSession gameSession = GameSession.GetInstance();
            MessageInformation messageInformation = new MessageInformation();

            messageInformation.PutValue("posX", stock.Square.PosX);
            messageInformation.PutValue("posY", stock.Square.PosY);
            messageInformation.PutValue("stockId", stock.Id);
            gameSession.QueueSender.SendMessage("UpdateCranePosition", messageInformation);
      

        }

    }
}
