using Apache.NMS;
using Apache.NMS.ActiveMQ;
using Apache.NMS.ActiveMQ.Commands;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Communication
{
    /// <summary>
    /// Sorgt daf�r das es nur eine Session und eine Connection pro Client gibt
    /// H�lt eine Connection, eine Session, eine Session und eine ConnectionFactory
    /// </summary>
    class ClientConnection
    {

        protected IConnection connection;
        protected IConnectionFactory connectionFactory;
        protected ISession session;
        private Guid clientId;

        public static string BROKER_URL = Properties.Settings.Default.BrokerUrl;
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
            clientId = Guid.NewGuid();
            Console.WriteLine("erstellt connection(base)");
            connectionFactory = new ConnectionFactory(BROKER_URL);
            Setup();
        }

        /// <summary>
        /// Setzt eine von der ConnectionFactory gebaute Connection und eine daraus erzeugte Session
        /// </summary>
        public void Setup()
        {
            try
            {
                connection = connectionFactory.CreateConnection();
                Console.WriteLine("startet Session(base)");
                session = connection.CreateSession(AcknowledgementMode.AutoAcknowledge);
                Console.WriteLine("startet queue(base)");
                connection.Start();
            }
            catch(NMSConnectionException e)
            {
                Console.Write("Es konnte keine Verbindung zum Server aufgebaut werden. Programm f�hrt fort ohne Verbindung. (ExceptionMessage: {0})", e);
                
            }
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

        public Guid ClientId
        {
            get
            {
                return clientId;
            }
        }
    }
}