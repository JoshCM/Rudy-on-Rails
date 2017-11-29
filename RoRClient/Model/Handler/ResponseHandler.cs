using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Apache.NMS;
using RoRClient.ViewModel;

namespace RoRClient.Model.Handler
{
	class ResponseHandler
	{
		private static ResponseHandler responseHandler;
		private ResponseHandler()
		{

		}

		public static ResponseHandler getInstance()
		{
			if (responseHandler == null)
			{
				responseHandler = new ResponseHandler();
			}
			return responseHandler;
		}

		internal void handle(IMessage message)
		{
            /*PlayerViewModel playerViewModel = new PlayerViewModel();
			ITextMessage textMessage = message as ITextMessage;
			playerViewModel.PlayerLabel = textMessage.Text;
           
	        */
            Console.WriteLine("ResponseHandler macht was");
            String messageType = message.NMSType;
            switch (messageType)
            {
                case "CREATERESPONSE":
                    CreateResponseHandler.getInstance().handle(message);
                    break;
                case "ERRORRESPONSE":
                    ErrorResponseHandler.getInstance().handle(message);
                    break;

                case "READRESPONSE":
                    ReadResponseHandler.getInstance().handle(message);
                    break;

                case "UPDATERESPONSE":
                    UpdateResponseHandler.getInstance().handle(message);
                    break;

                case "DELETE":
                   
                    break;

                case "STATUSMESSAGE":
                 
                    break;



            }

        }
	}
}
