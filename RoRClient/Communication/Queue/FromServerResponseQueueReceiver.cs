using Apache.NMS;
using RoRClient.Communication.Dispatcher;
using RoRClient.Models.Game;
using System;

namespace RoRClient.Communication.Queue
{
    class FromServerResponseReceiver : QueueBase
    {
        private ResponseDispatcher responseDispatcher;
        private IMessageConsumer messageConsumer;
        private ClientModel clientModel;

        public FromServerResponseReceiver(string queueName, ClientModel clientModel) : base(queueName)
        {
            this.clientModel = clientModel;
            init();
        }

        private void init()
        {
            responseDispatcher = new ResponseDispatcher(clientModel);
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
            responseDispatcher.dispatch(messageType, textMessage.Text);
        }
    }
}
