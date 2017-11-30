using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    public class Map : ModelBase
    {
        //Map mit Squares gefüllt (Spielfeld)

        private const int mapSize = 25;
        public static int MapSize => mapSize;

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


        public Map () : base()
        {
            squares = new Square[mapSize, mapSize];
            InitSquares();
            // For testing purposes
            CreateRandomRailsForTest();
        }

        /// <summary>
        /// Jedes Square auf der Map braucht einen Index,
        /// um jedem Objekt, das auf einem Square platziert, ein eindeutiges Objekt zuzuordnen
        /// </summary>
        private void InitSquares()
        {
            for (int x = 0; x < mapSize; x++)
            {
                for (int y = 0; y < mapSize; y++)
                {
                    Square s = new Square(x, y);
                    squares[x, y] = s;
                }
            }
        }

        private void CreateRandomRailsForTest()
        {
            for (int x = 0; x < mapSize; x++)
            {
                for (int y = 0; y < mapSize; y++)
                {
                    Random rand = new Random();
                    Rail rail = new Rail(squares[x, y], new RailSection(RailSectionPosition.NORTH, RailSectionPosition.SOUTH));
                    squares[x, y].PlaceableOnSquare = rail;
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
