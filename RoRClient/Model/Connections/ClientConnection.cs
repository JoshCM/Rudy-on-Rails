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
    class ClientConnection
    {

        protected IConnection connection;
        protected IConnectionFactory connectionFactory;
        protected ISession session;

        //public static string BROKER_URL = "tcp://172.26.39.100:61616";
        public static string BROKER_URL = "tcp://localhost:61616";
        private static ClientConnection instance;


        public ISession Session
        {

            get
            {
                return session;
            }
        }
        private ClientConnection()
        {
            
            Console.WriteLine("erstellt connection(base)");
            connectionFactory = new ConnectionFactory(BROKER_URL);
            connection = connectionFactory.CreateConnection();
            Console.WriteLine("startet Session(base)");
            session = connection.CreateSession(AcknowledgementMode.AutoAcknowledge);
            Console.WriteLine("startet queue(base)");
            connection.Start();

        }

        public static ClientConnection GetInstance()
        {
            if (instance == null)
            {
                instance = new ClientConnection();
            }

            return instance;
        }

        public void CloseConnection(){
            connection.Close();
        }

    }
}