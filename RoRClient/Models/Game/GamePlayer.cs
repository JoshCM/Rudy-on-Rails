using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    class GamePlayer : Player
    {
        private int coalCount;
        private int goldCount;
        private int pointCount;

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
                this.coalCount = value;
                NotifyPropertyChanged("CoalCount");
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
                this.goldCount = value;
                NotifyPropertyChanged("GoldCount");
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
                this.pointCount = value;
                NotifyPropertyChanged("PointCount");
            }
        }
    }
}
