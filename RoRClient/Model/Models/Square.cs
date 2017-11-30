﻿using RoRClient.ViewModel.Helper;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    public class Square : ModelBase
    {
        /// <summary>
        /// Klasse für ein Feld, dass auf der Map liegt und eine Position hat.
        /// Auf dem Feld platzierte Objekte können auf die jeweilige Position zugreifen
        /// </summary>

        public Square() : base()
        {

        }

        private int posX;
        public int PosX
        {
            get
            {
                return posX;
            }
            set
            {
                if(posX != value)
                {
                    int temp = posX;
                    posX = value;
                    NotifyPropertyChanged("XPos", temp, posX);
                }
            }
        }

        private int posY;

        public int PosY
        {
            get
            {
                return posY;
            }
            set
            {
                if(posY != value)
                {
                    int temp = posY;
                    posY = value;
                    NotifyPropertyChanged("YPos", temp, posY);
                }
            }
        }

        public Square (int xPos, int yPos)
        {
            this.posX = xPos;
            this.posY = yPos;
        }

        IPlaceableOnSquare placeableOnSquare = null;
        public IPlaceableOnSquare PlaceableOnSquare
        {
            get
            {
                return placeableOnSquare;
            }
            set
            {
                if(placeableOnSquare != value)
                {
                    IPlaceableOnSquare temp = placeableOnSquare;
                    placeableOnSquare = value;
                    NotifyPropertyChanged("PlaceableOnSquare", temp, placeableOnSquare);
                }
            }
        }
    }
}
