﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    public abstract class Trainstation : InteractiveGameObject, IPlaceableOnSquare
    {
        private List<Rail> trainstationRails;
        private Compass alignment;
        private Stock stock;
        private Crane crane;

        public Trainstation(Guid id, Square square, List<Rail> trainstationRails, Compass alignment, Stock stock) : base(square)
        {
            this.id = id;
            this.trainstationRails = trainstationRails;
            this.alignment = alignment;
            this.stock = stock;
        }

        public List<Rail> TrainstationRails
        {
            get
            {
                return trainstationRails;
            }
        }

        public Stock Stock
        {
            get { return stock; }
        }


        public Crane Crane
        {
            get
            {
                return crane;
            }
            set
            {
                if (crane == null)
                {
                    crane = value;
                }
            }
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
