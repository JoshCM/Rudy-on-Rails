using System;
using RoRClient.Models.Base;
using RoRClient.Communication.Queue;
using RoRClient.Communication;

namespace RoRClient.Models.Game
{
    class LobbyModel : ModelBase
    {
        private QueueSender fromClientRequestSender;
        private FromServerResponseReceiver queueReceiver;
		private Guid clientId;
        private bool connected_Editor;
        private bool connected_Game;

        public LobbyModel() {
			
		}

        public void StartConnection()
        {
            ClientConnection.GetInstance().Setup();

            // Anmelden bei Queue, an die alle Clients ihre Anfragen schicken
            Console.Write("Anmelden bei ClientRequestQueue");
            fromClientRequestSender = new QueueSender("ClientRequestQueue");

            // Erstelle die eigene Queue, an die der Server etwas zurücksenden kann
            clientId = ClientConnection.GetInstance().ClientId;
            Console.Write("Erstellt receiverQueue mit id:" + clientId.ToString());
            queueReceiver = new FromServerResponseReceiver(clientId.ToString(), this);
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

        public bool Connected_Editor
        {
            get
            {
                return connected_Editor;
            }
            set
            {
                if(connected_Editor != value)
                {
                    connected_Editor = value;
                    NotifyPropertyChanged("Connected_Editor");
                }
            }
        }
        public bool Connected_Game
        {
            get
            {
                return connected_Game;
            }
            set
            {
                if (connected_Game != value)
                {
                    connected_Game = value;
                    NotifyPropertyChanged("Connected_Game");
                }
            }
        }
    }
}
