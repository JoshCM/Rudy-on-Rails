using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Editor
{
    class StockEditorViewModel : CanvasEditorViewModel
    {
        Stock stock;
        public StockEditorViewModel(Stock stock) : base(stock.Id)
        {
            this.stock = stock;
            this.SquarePosX = stock.Square.PosX;
            this.SquarePosY = stock.Square.PosY;
        }

        public Stock Stock
        {
            get { return stock; }
        }

        public override void Delete()
        {
            throw new NotImplementedException();
        }

        public override void Move()
        {
            throw new NotImplementedException();
        }

        public override void RotateLeft()
        {
            throw new NotImplementedException();
        }

        public override void RotateRight()
        {
            throw new NotImplementedException();
        }
    }
}
