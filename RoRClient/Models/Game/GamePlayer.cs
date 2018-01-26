using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    public class GamePlayer : Player
    {
        public enum Color
        {
            GREEN = 0,
            ORANGE = 1,
            RED = 2,
            BLUE = 3
        };
        private int coalCount;
        private int goldCount;
        private int pointCount;
        private Color color;

        public GamePlayer(Guid id, String name, int coalCount, int goldCount, int pointCount, bool isHost) : base(id, name, isHost)
        {
            CoalCount = coalCount;
            GoldCount = goldCount;
            PointCount = pointCount;
        }

        public int CoalCount
        {
            get
            {
                return coalCount;
            }
            set
            {
                if(coalCount != value)
                {
                    this.coalCount = value;
                    NotifyPropertyChanged("CoalCount");
                }
            }
        }

        public int GoldCount
        {
            get
            {
                return goldCount;
            }
            set
            {
                if (goldCount != value)
                {
                    this.goldCount = value;
                    NotifyPropertyChanged("GoldCount");
                }
            }
        }

        public int PointCount
        {
            get
            {
                return pointCount;
            }
            set
            {
                if (pointCount != value)
                {
                    this.pointCount = value;
                    NotifyPropertyChanged("PointCount");
                }
            }
        }

        public Color PlayerColor
        {
            get
            {
                return color;
            }
            set
            {
                if(color != value)
                {
                    color = value;
                    NotifyPropertyChanged("Color");
                }
            }
        }
    }
}
