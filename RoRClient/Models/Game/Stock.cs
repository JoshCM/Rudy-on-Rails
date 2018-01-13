using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    public class Stock : InteractiveGameObject, IPlaceableOnSquare
    {
        private Compass alignment;
        public Stock(Guid id, Square square, Compass alignment) : base(square)
        {
            this.id = id;
            this.alignment = alignment;
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
    }
}
