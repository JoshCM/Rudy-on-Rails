using Apache.NMS;
using RoRClient.Model.Models;
using RoRClient.Model.Helper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Connections
{
    class QueueReceiver : QueueBase
    {
        private IMessageConsumer messageConsumer;

        public QueueReceiver(string queueName) : base(queueName)
        {
            init();
        }

        private void init()
        {
            Console.WriteLine("startet messageconsumer(queueReceiver)");
            messageConsumer = session.CreateConsumer(queue);
            messageConsumer.Listener += OnMessageReceived;
            Console.WriteLine("startet connection(queueReceiver)");
            //connection.Start();
        }

        public void OnMessageReceived(IMessage message)
        {
            ITextMessage textMessage = message as ITextMessage;

            Console.WriteLine(textMessage.Text+"(queueReceiver)");
            TopicReceiver tR = new TopicReceiver(textMessage.Text);
        }
    }
}
