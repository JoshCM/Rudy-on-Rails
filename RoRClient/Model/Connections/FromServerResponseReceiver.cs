using Apache.NMS;
using RoRClient.Model.Models;
using RoRClient.Model.Helper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Model.Handler;

namespace RoRClient.Model.Connections
{
    class FromServerResponseReceiver : QueueBase
    {
        private IMessageConsumer messageConsumer;

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
            //connection.Start();
        }

        public void OnMessageReceived(IMessage message)
        {
			Console.WriteLine("from server: " + ((ITextMessage)message).Text);
			ResponseHandler.getInstance().handle(message);
        }
    }
}
