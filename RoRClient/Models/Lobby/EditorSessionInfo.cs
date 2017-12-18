using RoRClient.Models.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Lobby
{
    class EditorSessionInfo : ModelBase
    {
        private string name;
        private int amountOfPlayers;

        public EditorSessionInfo(string name, int amountOfPlayers)
        {
            this.name = name;
            this.amountOfPlayers = amountOfPlayers;
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
