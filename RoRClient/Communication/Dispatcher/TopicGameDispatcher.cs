using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Communication.Dispatcher
{
    class TopicGameDispatcher : TopicDispatcherBase
    {
        public TopicGameDispatcher()
        {
            COMMAND_TYPE_PREFIX = "RoRClient.Commands.Game.";
        }
    }
}
