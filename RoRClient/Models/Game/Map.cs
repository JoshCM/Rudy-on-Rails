using RoRClient.Models.Base;
using System;
using System.Collections.Generic;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Map mit Squares gefüllt (Spielfeld)
    /// </summary>
    public class Map : ModelBase
    {
        private int mapSize;
        private string name = "";
        private Square[,] squares;

        public string Name
        {
            get
            {
                return name;
            }
            set
            {
                if(name != value)
                {
                    name = value;
                    NotifyPropertyChanged("Name");
                }
            }
        }

        public int MapSize
        {
            get
            {
                return mapSize;
            }
            set
            {
                if(mapSize != value)
                {
                    mapSize = value;
                    NotifyPropertyChanged("MapSize");
                }
            }
        }

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

        public Map (int mapSize) : base()
        {
            this.mapSize = mapSize;
            squares = new Square[mapSize, mapSize];
            InitSquares();
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
        /// Gibt ein placableOnSquare für eine ID zurück
        /// </summary>
        /// <param name="railId"></param>
        /// <returns></returns>
        public IPlaceableOnSquare GetPlaceableById(Guid id)
        {
            foreach(Square square in squares)
            {
                IPlaceableOnSquare placeableOnSquare = square.PlaceableOnSquare;
                if (placeableOnSquare != null)
                {
                    if (placeableOnSquare.Id.Equals(id))
                    {
                        return placeableOnSquare;
                    }
                }
            }
            return null;
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
                    RailSection section = new RailSection(Guid.NewGuid(), Compass.NORTH, Compass.SOUTH);
                    List<RailSection> railSections = new List<RailSection>();
                    railSections.Add(section);
                    Rail rail = new Rail(Guid.NewGuid(), squares[x, y], railSections);
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
