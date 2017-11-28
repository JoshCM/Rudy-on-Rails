using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    class Map
    {
        //Map mit Squares gefüllt (Spielfeld)

        const int mapSize = 3;

        Square[,] squares;

        public Square[,] Squares
        {
            get
            {
                return squares;
            }
            set
            {
                squares = value;
            }
        }

        public Map ()
        {
            squares = new Square[mapSize, mapSize];

            //Jedes Square auf der Map braucht einen Index,
            //um jedem Objekt, das auf einem Square platziert, ein eindeutiges Objekt zuzuordnen
            for (int x = 0; x < mapSize; x++)
            {
                for (int y = 0; y < mapSize; y++)
                {
                    Square s = new Square(x, y);
                    squares[x, y] = s;
                }
            }
        }

        public Square GetSquare (int x, int y)
        {
            //Getter für ein Square auf der Map
            return this.squares[x, y];
        }
    }
}
