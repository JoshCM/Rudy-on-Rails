using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Apache.NMS;
using RoRClient.ViewModel;

namespace RoRClient.Model.Connections
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
			switch (message.GetType())
			{
				case RequestType.
			}
			PlayerViewModel playerViewModel = new PlayerViewModel();
			ITextMessage textMessage = message as ITextMessage;
			playerViewModel.PlayerLabel = textMessage.Text;
		}
	}
}
