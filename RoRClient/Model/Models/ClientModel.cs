using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Model.Connections;

namespace RoRClient.Model.Models
{
    class ClientModel
    {
        //"tcp://172.26.38.104:61616"
        public static string BROKER_URL = "tcp://localhost:8080";
        private FromClientRequestSender fromClientRequestSender;
        private FromServerResponseReceiver queueReceiver;
		private Guid clientId;
		private static ClientModel clientModel;
        private TopicReceiver topic;

        private ClientModel(){
			// Anmelden bei Queue, an die alle Clients ihre Anfragen schicken
			Console.Write("Anmelden bei ClientRequestQueue");
			fromClientRequestSender = new FromClientRequestSender("ClientRequestQueue");

			// Erstelle die eigene Queue, an die der Server etwas zurücksenden kannGuid id = Guid.NewGuid();
			clientId = Guid.NewGuid();
			Console.Write("Erstellt receiverQueue mit id:" + clientId.ToString());
			queueReceiver = new FromServerResponseReceiver(clientId.ToString());
            //initalen TopicReceiver erstellen zum test des EditorTopics
            if (topic == null)
            {
                topic = new TopicReceiver("BaseModelID");
            }
		}

		public static ClientModel getInstance()
		{
			if (clientModel == null)
			{
				clientModel = new ClientModel();
			}
			return clientModel;
		}

		public FromClientRequestSender getFromClientRequestSender()
		{
			return fromClientRequestSender;
		}

		public Guid getClientId()
		{
			return clientId;
		}
    }
}
