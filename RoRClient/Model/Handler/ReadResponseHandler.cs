using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Apache.NMS;

namespace RoRClient.Model.Handler
{
    class ReadResponseHandler : IHandler
    {

        private static ReadResponseHandler readResponseHandler;
        private ReadResponseHandler()
        {

        }

        public static ReadResponseHandler getInstance()
        {
            if (readResponseHandler == null)
            {
                readResponseHandler = new ReadResponseHandler();
            }
            return readResponseHandler;
        }
        public void handle(IMessage message)
        {
            throw new NotImplementedException();
        }
    }
}
