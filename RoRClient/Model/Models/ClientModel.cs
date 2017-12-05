using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Model.Connections;

namespace RoRClient.Model.Models
{
    class ClientModel : ModelBase
    {
        private QueueSender fromClientRequestSender;
        private FromServerResponseReceiver queueReceiver;
		private Guid clientId;
        private bool connected;

        public ClientModel(){
			// Anmelden bei Queue, an die alle Clients ihre Anfragen schicken
			Console.Write("Anmelden bei ClientRequestQueue");
			fromClientRequestSender = new QueueSender("ClientRequestQueue");

            // Erstelle die eigene Queue, an die der Server etwas zurücksenden kannGuid id = Guid.NewGuid();
            clientId = ClientConnection.GetInstance().ClientId;
			Console.Write("Erstellt receiverQueue mit id:" + clientId.ToString());
			queueReceiver = new FromServerResponseReceiver(clientId.ToString(),this);
		}

		public QueueSender getFromClientRequestSender()
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

        public bool Conncected
        {
            get
            {
                return connected;
            }
            set
            {
                if(connected != value)
                {
                    connected = value;
                    NotifyPropertyChanged("Connected");
                }
            }
        }
    }
}
