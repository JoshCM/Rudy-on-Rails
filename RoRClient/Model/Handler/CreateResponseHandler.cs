using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Apache.NMS;

namespace RoRClient.Model.Handler
{
    class CreateResponseHandler : IHandler
    {
        private static CreateResponseHandler createResponseHandler;
        private CreateResponseHandler()
        {

        }

        public static CreateResponseHandler getInstance()
        {
            if (createResponseHandler == null)
            {
                createResponseHandler = new CreateResponseHandler();
            }
            return createResponseHandler;
        }
        public void handle(IMessage message)
        {
            throw new NotImplementedException();
        }
    }
}
