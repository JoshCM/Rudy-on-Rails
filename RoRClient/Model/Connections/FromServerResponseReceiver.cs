using Apache.NMS;
using RoRClient.Model.Models;
using RoRClient.Model.DataTransferObject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Model.HandleResponse;

namespace RoRClient.Model.Connections
{
    class FromServerResponseReceiver : QueueBase
    {
        private IMessageConsumer messageConsumer;
        private TopicReceiver topic;

        public FromServerResponseReceiver(string queueName) : base(queueName)
        {
            init();
        }

        private void init()
        {
            Console.WriteLine("startet messageconsumer(queueReceiver)");
            messageConsumer = session.CreateConsumer(queue);
            messageConsumer.Listener += OnMessageReceived;
            Console.WriteLine("startet connection(queueReceiver)");
      
        }

        public void OnMessageReceived(IMessage message)
        {
			Console.WriteLine("from server: " + ((ITextMessage)message).Text);

            string messageTypeString = message.NMSType;
            string messageType = messageTypeString;
            ITextMessage textMessage = message as ITextMessage;
            ResponseDispatcher.getInstance().dispatch(messageType, textMessage);
        }

        /// <summary>
        /// findet den passenden MessageType Enum für einen String
        /// </summary>
        /// <param name="messageTypeString"></param>
        /// <returns></returns>
        internal MessageType findMessageType(String messageTypeString)
        {
            switch (messageTypeString)
            {
                case "CREATE":
                    return MessageType.CREATERESPONSES;

                case "ERROR":
                    return MessageType.ERRORRESPONSES;

                case "READ":
                    return MessageType.READRESPONSES;

                case "UPDATE":
                    return MessageType.UPDATERESPONSES;

                case "DELETE":
                    return MessageType.DELETERESPONSES;

                case "STATUSMESSAGE":
                    return MessageType.STATUSMESSAGES;

                default:
                    throw new InvalidOperationException("Unrecognized comparison mode");

            }

        }
    }
}
