using Apache.NMS;
using RoRClient.Communication.DataTransferObject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Communication.Queue
{
    public class QueueSender : QueueBase
    {
        private IMessageProducer messageProducer;

        public QueueSender(string queueName) : base(queueName)
        {
            Console.WriteLine("startet messageproducer(queueSender)");
            messageProducer = session.CreateProducer(queue);
            //connection.Start();
        }

        public void SendMessage(string messageType, MessageInformation messageInformation)
        {
            IMessage message = MessageBuilder.build(messageType, messageInformation);
            SendMessage(message);
        }

        private void SendMessage(IMessage message)
        {
            try
            {
                Console.WriteLine("sendet message(queueSender)");
                messageProducer.Send(message);
            }
            catch (Exception e)
            {
                Console.Write(e);
            }
        }
    }
}
