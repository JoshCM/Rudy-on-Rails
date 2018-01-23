using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    public class Mine : InteractiveGameObject, IPlaceableOnRail
    {
        private List<IPlaceableOnSquare> coals = new List<IPlaceableOnSquare>();
        private List<IPlaceableOnSquare> golds = new List<IPlaceableOnSquare>();
        private Compass alignment;
        private String coalCount;
        private String goldCount;

        public Mine(Guid id, Square square, Compass alignment) : base(square)
        {
            this.alignment = alignment;
            this.id = id;
        }

        public Compass Alignment
        {
            get
            {
                return alignment;
            }
            set
            {
                alignment = value;
                NotifyPropertyChanged("Alignment");
            }
        }
        public String GoldCount
        {
            get {
                goldCount = golds.Count.ToString();
                return goldCount;
            }
            set
            {
                goldCount = value;
                
            }
        }
        public String CoalCount
        {
            get {
                coalCount = coals.Count.ToString();
                return coalCount;
            }
            set
            {
                coalCount = value;
            }
        }
        public void AddCoal(IPlaceableOnSquare coal) {
            coals.Add(coal);
            NotifyPropertyChanged("CoalCount",coalCount,coals.Count.ToString());
        }
        public void AddGold(IPlaceableOnSquare gold)
        {
            golds.Add(gold);
            NotifyPropertyChanged("GoldCount",goldCount,golds.Count.ToString());
        }
        public void RemoveCoal(IPlaceableOnSquare coal)
        {
            coals.Remove(coal);
            NotifyPropertyChanged("CoalCount", coalCount, coals.Count.ToString());
        }
        public void RemoveGold(IPlaceableOnSquare gold)
        {
            golds.Remove(gold);
            NotifyPropertyChanged("GoldCount", goldCount, golds.Count.ToString());
        }

        public List<IPlaceableOnSquare> Coals
        {
            get
            {
                return coals;
            }
        }

        public List<IPlaceableOnSquare> Golds
        {
            get
            {
                return golds;
            }
        }
    }
}
