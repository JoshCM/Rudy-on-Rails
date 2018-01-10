using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

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
    }
}
