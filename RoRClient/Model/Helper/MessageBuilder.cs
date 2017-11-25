using Apache.NMS;
using Apache.NMS.ActiveMQ.Commands;
using RoRClient.Model.Connections;
using RoRClient.Model.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Helper
{
	static class MessageBuilder
	{
		public static IMessage build(MessageType messageType, RequestType requestType)
		{
			Guid clientId = ClientModel.getInstance().getClientId();
			ISession session = ClientConnection.GetInstance().Session;
			String content = "{\"clientid\":\"" + clientId.ToString() + "\" ,\"request\":\"" + requestType.ToString() + "\",\"attributes\": {\"Playername\": \"Wie gehts du Fisch\"}}";

			IMessage message = session.CreateTextMessage(content);
			message.NMSType = messageType.ToString();
			return message;
		}
	}
}
