using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    abstract class InteractiveGameObject
    {
        //Abstrakte Klasse für alle Objekte, mit denen interagiert werden kann

        protected Square square;

        public InteractiveGameObject(Square square)
        {
            this.square = square;
        }
    }
}
