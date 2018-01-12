using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    class Crane : InteractiveGameObject, IPlaceableOnRail
    {
        public Crane(Guid id, Square square) : base(square)
        {
            this.id = id;
        }
    }
}
