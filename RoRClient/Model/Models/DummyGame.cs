using RoRClient.Model.Connections;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    class DummyGame
    {
        private QueueSender queueSender;
        private TopicReceiver topicReceiver;

        public DummyGame(string gameName)
        {
            queueSender = new QueueSender(gameName);
            topicReceiver = new TopicReceiver(gameName);

            // Testweise wird hier eine Nachricht geschickt
            queueSender.SendMessage("Ändere die Geschwindigkeit von meinem Zug. Bitte.");
        }
    }
}
