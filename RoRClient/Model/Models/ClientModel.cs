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
        private FromClientRequestSender fromClientRequestSender;
        private FromServerResponseReceiver queueReceiver;
		private Guid clientId;

        public ClientModel(){
			// Anmelden bei Queue, an die alle Clients ihre Anfragen schicken
			Console.Write("Anmelden bei ClientRequestQueue");
			fromClientRequestSender = new FromClientRequestSender("ClientRequestQueue");

            // Erstelle die eigene Queue, an die der Server etwas zurücksenden kannGuid id = Guid.NewGuid();
            clientId = ClientConnection.GetInstance().ClientId;
			Console.Write("Erstellt receiverQueue mit id:" + clientId.ToString());
			queueReceiver = new FromServerResponseReceiver(clientId.ToString());
		}

		public FromClientRequestSender getFromClientRequestSender()
		{
			return fromClientRequestSender;
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
