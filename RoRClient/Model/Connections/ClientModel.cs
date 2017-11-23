using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Model.Helper;

namespace RoRClient.Model.Connections
{
    class ClientModel
    {
        //"tcp://172.26.38.104:61616"
        public static string BROKER_URL = "tcp://localhost:61616";
        private QueueSender queueSender;
        private QueueReceiver queueReceiver;
        public ClientModel(){
            // Anmelden bei Queue, an die alle Clients ihre Anfragen schicken
            Console.Write("Anmelden bei ClientRequestQueue");
            queueSender = new QueueSender("ClientRequestQueue");

            // Erstelle die eigene Queue, an die der Server etwas zurücksenden kannGuid id = Guid.NewGuid();
            Guid id = Guid.NewGuid();
            Console.Write("Erstellt receiverQueue mit id:"+id.ToString());
            queueReceiver = new QueueReceiver(id.ToString());

            //ruft sende-test-methode auf
            DoTheTest(id);
        }

        public void DoTheTest(Guid id)
        {
            // Sende den eigenen Queue-Namen, damit der Server etwas daran zurückschicken kann
            Console.WriteLine("Nachricht sollte jetzt gesendet werden");
            Command cmd = new Command();
            Dictionary<String, String> attributes = new Dictionary<String, String>();
            attributes.Add("id", id.ToString());
            cmd.attributes = attributes;
            cmd.cEnum = Command.CommandEnum.CREATE;
            Console.WriteLine(Serializer.serialize(cmd));
            queueSender.SendMessage(Serializer.serialize(cmd));
        }
    }
}
