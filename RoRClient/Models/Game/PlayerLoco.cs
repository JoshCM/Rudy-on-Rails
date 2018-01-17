using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    public class PlayerLoco : Loco
    {
        public PlayerLoco(Guid id, Compass drivingDirection, Square square) : base(id, drivingDirection, square)
        {
        }
    }
}
