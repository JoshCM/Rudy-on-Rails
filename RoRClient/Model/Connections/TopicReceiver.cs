using Apache.NMS;
using Apache.NMS.ActiveMQ;
using Apache.NMS.ActiveMQ.Commands;
using RoRClient.Model.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Connections
{
    class TopicReceiver
    {
       
        protected string topicName;
        protected ITopic topic;
        private IMessageConsumer messageConsumer;
        ISession session;


        public TopicReceiver(string topicName)
        {
            this.topicName = topicName;
            StartConnection();
        }

        private void StartConnection()
        {
            session=ClientConnection.GetInstance().Session; 
            topic = new ActiveMQTopic(topicName);
            Console.WriteLine("startet messageconsumer (topicReceiver)");
            messageConsumer = session.CreateConsumer(topic);
            messageConsumer.Listener += OnMessageReceived;
            Console.WriteLine("startet connection(topicReceiver)");
        }

        public void OnMessageReceived(IMessage message)
        {
            ITextMessage textMessage = message as ITextMessage;

            Console.WriteLine("Folgende Änderung am Game erhalten: " + textMessage.Text+"(topicReceiver)");
            
        }
    }
}
