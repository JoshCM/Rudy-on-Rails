using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    class Crane : InteractiveGameObject, IPlaceableOnRail
    {
        private Compass alignment;

        public Crane(Guid id, Square square, Compass alignment) : base(square)
        {
            this.id = id;
            this.alignment = alignment;
        }

 

        public Compass Alignment
        {
            get { return alignment; }
            set
            {
                if (alignment != value)
                {
                    alignment = value;
                }
            }
        }
    }
}
