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
                EditorSession editorSession = EditorSession.GetInstance();
                editorSession.Name = messageInformation.GetValueAsString("Editorname");
                editorSession.Init(messageInformation.GetValueAsString("Topicname"));

                Guid playerId = Guid.Parse(messageInformation.GetValueAsString("Playerid"));
                string playerName = messageInformation.GetValueAsString("Playername");
                Player player = new Player(playerId, playerName);
                editorSession.AddPlayer(player);
                clientModel.Conncected = true;
            }
        }
    }
}
