using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    public class Container : InteractiveGameObject, IPlaceableOnSquare
    {
        private Square square;
        private Resource ressource;


        public Container(Guid id, Square square, Resource ressource) : base(square)
        {
            this.id = id;
            this.square = square;
            this.ressource = ressource;
        }
    }
}
