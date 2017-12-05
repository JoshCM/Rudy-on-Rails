using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    public class Player: ModelBase
    {
        public Player(Guid id, string name)
        {
            this.name = name;
            this.id = id;
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
