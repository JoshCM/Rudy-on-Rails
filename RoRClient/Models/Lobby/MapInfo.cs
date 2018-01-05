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
	    public MapInfo(string name)
	    {
		    this.name = name;
	    }

	    public string Name
	    {
		    get { return name; }
		    set { this.name = value; }
	    }
    }
}
