using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Apache.NMS;

namespace RoRClient.Model.Handler
{

    class ErrorResponseHandler : IHandler
    {

        private static ErrorResponseHandler errorResponseHandler;
        private ErrorResponseHandler()
        {

        }

        public static ErrorResponseHandler getInstance()
        {
            if (errorResponseHandler == null)
            {
                errorResponseHandler = new ErrorResponseHandler();
            }
            return errorResponseHandler;
        }

        public void handle(IMessage message)
        {
            throw new NotImplementedException();
        }
    }
}
