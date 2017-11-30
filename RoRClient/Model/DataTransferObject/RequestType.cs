using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.DataTransferObject
{
	enum RequestType
	{
		PLAYER,
		GAMESESSION,
		EDITORSESSION,
		JOIN_GAMESESSION,
		JOIN_EDITORSESSION,
		DISCONNECT_PLAYER,
		LEAVE_GAMESESSION,
		LEAVE_EDITORSESSION,
		PLAYERSTATUS,

        CREATE,
        UPDATE,
        DELETE,
        MAP_SAVE,
        MAP_LOAD,
        GET_INITIAL_LOAD
	}
    
}
