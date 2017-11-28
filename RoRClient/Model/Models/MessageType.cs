using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
	enum MessageType
	{
		CREATE,
		READ,
		UPDATE,
		DELETE,
        LEAVE,
		ERROR
	};
}
