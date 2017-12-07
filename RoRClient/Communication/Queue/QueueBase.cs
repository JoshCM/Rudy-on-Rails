using Apache.NMS;
using Apache.NMS.ActiveMQ;
using Apache.NMS.ActiveMQ.Commands;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Communication.Queue
{
    public class QueueBase
    {
        protected ISession session;
        protected string queueName;
        protected IQueue queue;

        public QueueBase(string queueName)
        {
            this.queueName = queueName;
            session = ClientConnection.GetInstance().Session;
            queue = new ActiveMQQueue(queueName);
        }
    }
}
