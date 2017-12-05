using Apache.NMS;
using RoRClient.Model.Connections;
using RoRClient.Model.DataTransferObject;
using RoRClient.Model.Models;
using RoRClient.Model.Models.Editor;
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
        private ClientModel clientModel;

        public ResponseDispatcher(ClientModel clientModel)
        {
            this.clientModel = clientModel;
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

                EditorSession.GetInstance().Name = messageInformation.GetValueAsString("Editorname");
                Player player = new Player(Guid.Parse(messageInformation.GetValueAsString("Playerid")), messageInformation.GetValueAsString("Playername"));
                EditorSession.GetInstance().AddPlayer(player);
                clientModel.Conncected = true;

                // EditorSession erstellen 
                    // Player erstellen
                // EditorViewModel wechseln
            }
        }
    }
}
