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
            ITextMessage textMessage = (ITextMessage)message;

            Console.WriteLine("from server: " + textMessage.Text);

            string messageType = message.NMSType;
            ResponseDispatcher.getInstance().dispatch(messageType, textMessage.Text);
        }
    }
}
