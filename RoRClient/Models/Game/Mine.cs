using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    public class Mine : InteractiveGameObject, IPlaceableOnRail
    {

        private Compass alignment;

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
    }
}
