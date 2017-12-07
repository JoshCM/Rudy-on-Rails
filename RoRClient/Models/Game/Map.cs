using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    /// <summary>
    /// Map mit Squares gefüllt (Spielfeld)
    /// </summary>
    public class Map : ModelBase
    {
        private const int mapSize = 3;
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

            // nur zum testen der GUI
            //CreateRandomRailsForTest();
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

        /// <summary>
        /// (Testweise)
        /// Erzeugt für zufällige Squares auf der Map eine Rail mit einer RailSection
        /// </summary>
        private void CreateRandomRailsForTest()
        {
            for (int x = 0; x < mapSize; x++)
            {
                for (int y = 0; y < mapSize; y++)
                {
                    Random rand = new Random();
                    Rail rail = new Rail(Guid.NewGuid(), squares[x, y], new RailSection(RailSectionPosition.NORTH, RailSectionPosition.SOUTH));
                    squares[x, y].PlaceableOnSquare = rail;
                }
            }
        }

        /// <summary>
        /// Getter für ein Square auf der Map
        /// </summary>
        /// <param name="x"> X-Position des Squares</param>
        /// <param name="y"> Y-Position des Squares</param>
        /// <returns></returns>
        public Square GetSquare (int x, int y)
        {
            return this.squares[x, y];
        }
    }
}
