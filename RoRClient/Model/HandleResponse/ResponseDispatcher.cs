using Apache.NMS;
using RoRClient.Model.Connections;
using RoRClient.Model.DataTransferObject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.HandleResponse
{
    class ResponseDispatcher
    {
        private TopicReceiver topic;

        public ResponseDispatcher()
        {

        }

        /// <summary>
        /// Aufgabe die Message zu "dispatchen" um zu entscheiden, was damit passieren soll, je nach MessageType
        /// </summary>
        /// <param name="message"></param>
		internal void dispatch(string messageType, String content)
        {
            MessageInformation messageInformation = MessageDeserializer.getInstance().deserialize(content);

            if(messageType == "CreateEditorSession")
            {
                // initalen TopicReceiver erstellen zum test des EditorTopics
                if (topic == null)
                {
                    topic = new TopicReceiver(messageInformation.GetValueAsString("Topicname"));
                }

                // EditorSession erstellen 
                    // Player erstellen
                // EditorViewModel wechseln
            }
        }
    }
}
