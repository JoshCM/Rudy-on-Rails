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
            for (int i = 0; i < mapSize; i++)
            {
                for (int j = 0; j < mapSize; j++)
                {
                    Square s = new Square(i, j);
                    squares[i,j] = s;
                }
            }
        }

        public Square getSquare (int i, int j)
        {
            //Getter für ein Square auf der Map
            return this.squares[i,j];
        }
    }
}
