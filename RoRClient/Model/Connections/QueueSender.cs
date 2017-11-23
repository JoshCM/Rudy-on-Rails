using Apache.NMS;
using Apache.NMS.ActiveMQ;
using Apache.NMS.ActiveMQ.Commands;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Connections
{
    class QueueSender : QueueBase
    {
        private IMessageProducer messageProducer;

        public QueueSender(string queueName) : base(queueName)
        {
            Console.WriteLine("startet messageproducer(queueSender)");
            messageProducer = session.CreateProducer(queue);
            //connection.Start();
        }

        public void SendMessage(string text)
        {
            try
            {
                Console.WriteLine("sendet message(queueSender)");
                IMessage message = session.CreateTextMessage(text);
                messageProducer.Send(message);
            }
            catch (Exception e)
            {
                Console.Write(e);
            }
        }
    }
}
