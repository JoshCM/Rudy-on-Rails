using Newtonsoft.Json;
using Apache.NMS;
using System;

namespace RoRClient.Communication.DataTransferObject
{
    /// <summary>
    /// Kann anhand eines Strings(messageType) und einer MessageInformation(content)
    /// eine IMessage mit NMSType erzeugen
    /// </summary>
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
