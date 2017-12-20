using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Communication.Dispatcher
{
    public class TopicGameDispatcher : TopicDispatcherBase
    {
        public TopicGameDispatcher()
        {
            session = GameSession.GetInstance();
            COMMAND_TYPE_PREFIX = "RoRClient.Commands.Game.";
        }
    }
}
