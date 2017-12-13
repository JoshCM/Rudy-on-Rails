using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Communication.Dispatcher
{
    public class TopicEditorDispatcher : TopicDispatcherBase
    {
        public TopicEditorDispatcher()
        {
            COMMAND_TYPE_PREFIX = "RoRClient.Commands.Editor.";
        }
    }
}
