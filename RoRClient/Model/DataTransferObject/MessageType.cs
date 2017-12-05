using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.DataTransferObject
{
	enum MessageType
	{
        CREATE,
        READ,
        UPDATE,
        DELETE,
        LEAVE,
        ERROR,

        //EDITOR
        MODIFYOBJECT,
        SESSIONREQUEST,


        //RESPONSE
        CREATERESPONSES,
        READRESPONSES,
        UPDATERESPONSES,
        ERRORRESPONSES,
        STATUSMESSAGES,
        DELETERESPONSES

    };
}
