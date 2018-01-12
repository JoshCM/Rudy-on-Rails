using RoRClient.Models.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Lobby
{
    class GameSessionInfo : ModelBase
    {
        private string name;
        private int amountOfPlayers;
        private int availablePlayerSlots;

        public GameSessionInfo(string name, int amountOfPlayers, int availablePlayerSlots)
        {
            this.name = name;
            this.amountOfPlayers = amountOfPlayers;
            this.availablePlayerSlots = availablePlayerSlots;
        }

        public int AvailablePlayerSlots
        {
            get
            {
                return availablePlayerSlots;
            }
        }

        public string Name
        {
            get
            {
                return name;
            }
            set
            {
                name = value;
                NotifyPropertyChanged("Name");
            }
        }

        public int AmountOfPlayers
        {
            get
            {
                return amountOfPlayers;
            }
            set
            {
                amountOfPlayers = value;
                NotifyPropertyChanged("AmountOfPlayers");
            }
        }
    }
}
