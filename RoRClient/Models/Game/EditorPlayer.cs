using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    class EditorPlayer : Player
    {

        public EditorPlayer(Guid id, String name, bool isHost) : base(id, name, isHost)
        { 
        }
    }
}
