using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Lobby
{
    public class PossibleMapSize
    {
        private int mapSize;
        private string description;

        public PossibleMapSize(int mapSize, string description)
        {
            this.mapSize = mapSize;
            this.description = description;
        }

        public int MapSize
        {
            get
            {
                return mapSize;
            }
        }

        public string Description
        {
            get
            {
                return description;
            }
        }
    }
}
