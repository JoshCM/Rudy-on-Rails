using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Models.Base;

namespace RoRClient.Models.Lobby
{
    class MapInfo : ModelBase
    {
	    private string name;
        private int availablePlayerSlots;
	    public MapInfo(string name, int availablePlayerSlots)
	    {
		    this.name = name;
            this.availablePlayerSlots = availablePlayerSlots;
	    }

	    public string Name
	    {
		    get { return name; }
		    set { this.name = value; }
	    }

        public int AvailablePlayerSlots
        {
            get
            {
                return availablePlayerSlots;
            }
            set
            {
                this.availablePlayerSlots = value;
            }
        }
    }
}
