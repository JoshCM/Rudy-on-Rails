using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    class CartGameViewModel : CanvasGameViewModel
    {
        private Cart cart;

        public CartGameViewModel(Cart cart) : base(cart.Id)
        {
            this.cart = cart;
            this.SquarePosX = cart.Square.PosX;
            this.SquarePosY = cart.Square.PosY;

            cart.PropertyChanged += OnCartChanged;
        }

        private void OnCartChanged(object sender, PropertyChangedEventArgs e)
        {
            if (e.PropertyName == "Square")
            {
                this.SquarePosX = cart.Square.PosX;
                this.SquarePosY = cart.Square.PosY;
            }
        }

        public Cart Cart
        {
            get
            {
                return cart;
            }
        }
    }
}
