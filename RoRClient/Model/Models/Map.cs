using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    class Map
    {
        Map map = null;
        Square[,] squares;
        const int mapSize = 3;

        public Map()
        {
            squares = new Square[mapSize, mapSize];

            for (int i = 0; i < mapSize; i++)
            {
                for (int j = 0; j < mapSize; j++)
                {
                    Square s = new Square(i, j);
                    squares[i,j] = s;
                }
            }
        }

        public Map map
        {
            set
            {

            }
        }
    }
}
