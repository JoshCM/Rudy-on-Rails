using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Apache.NMS;

namespace RoRClient.Model.Handler
{
    class UpdateResponseHandler : IHandler
    {

        private static UpdateResponseHandler updateResponseHandler;
        private UpdateResponseHandler()
        {

        }

        public static UpdateResponseHandler getInstance()
        {
            if (updateResponseHandler == null)
            {
                updateResponseHandler = new UpdateResponseHandler();
            }
            return updateResponseHandler;
        }

        public void handle(IMessage message)
        {
            throw new NotImplementedException();
        }
    }
}
