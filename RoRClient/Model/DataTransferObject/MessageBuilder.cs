using Newtonsoft.Json;
using Apache.NMS;
using Apache.NMS.ActiveMQ.Commands;
using RoRClient.Model.Connections;
using RoRClient.Model.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.DataTransferObject
{
	static class MessageBuilder
	{
		public static IMessage build(String messageType, MessageInformation content)
		{

			ISession session = ClientConnection.GetInstance().Session;
            String contentString = JsonConvert.SerializeObject(content);
            
            IMessage message = session.CreateTextMessage(contentString);
            message.NMSType = messageType;

            return message;
		}
	}
}
