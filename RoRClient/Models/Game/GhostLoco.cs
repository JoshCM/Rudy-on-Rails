using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    public class GhostLoco : Loco
    {
        public GhostLoco(Guid id, Guid playerId, Compass drivingDirection, Square square) : base(id, playerId, drivingDirection, square)
        {
           
        }
    }
}
