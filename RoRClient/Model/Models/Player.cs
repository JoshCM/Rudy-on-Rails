using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    class Player
    {
        public Player(string name)
        {
            this.name = name;
        }

        private string name;
        public String Name
        {
            get
            {
                return name;
            }
        }
    }
}
