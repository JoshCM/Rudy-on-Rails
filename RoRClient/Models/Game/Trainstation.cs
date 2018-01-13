﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Klasse für Trainstations die einem Square zugeordnet sind
    /// und eine Liste von Rails die zu der Trainstation gehören
    /// </summary>
    public class Trainstation : InteractiveGameObject, IPlaceableOnSquare
    {
        private List<Rail> trainstationRails;
        private Compass alignment;
        private Stock stock;
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
