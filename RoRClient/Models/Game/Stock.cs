using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    public class Stock : InteractiveGameObject, IPlaceableOnSquare
    {
        private Compass alignment;
        private Guid trainstationId;
        private TrainstationType trainstationType;

        public Stock(Guid id, Square square, Compass alignment, Guid trainstationId, TrainstationType trainstationType) : base(square)
        {
            this.id = id;
            this.alignment = alignment;
            this.trainstationId = trainstationId;
            this.trainstationType = trainstationType;
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

        public Guid TrainstationId
        {
            get
            {
                return trainstationId;
            }
        }

        public TrainstationType TrainstationType
        {
            get
            {
                return trainstationType;
            }
        }
    }

    public enum TrainstationType
    {
        PLAYER, PUBLIC
    }
}
