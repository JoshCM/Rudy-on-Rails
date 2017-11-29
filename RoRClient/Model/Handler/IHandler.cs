using Apache.NMS;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Handler
{
    interface IHandler
    {

        void handle(IMessage message);

    }
}
