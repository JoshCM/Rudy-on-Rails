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
            MessageInformation messageInformation = MessageDeserializer.getInstance().Deserialize(content);

            if(messageType == "CreateEditorSession")
            {
                EditorSession editorSession = EditorSession.GetInstance();
                editorSession.Name = messageInformation.GetValueAsString("editorName");
                editorSession.Init(messageInformation.GetValueAsString("topicName"));

                Guid playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));
                string playerName = messageInformation.GetValueAsString("playerName");
                Player player = new Player(playerId, playerName);
                editorSession.AddPlayer(player);
                clientModel.Connected = true;
            }
            else if(messageType == "JoinEditorSession")
            {
                EditorSession editorSession = EditorSession.GetInstance();
                editorSession.Name = messageInformation.GetValueAsString("editorName");
                editorSession.Init(messageInformation.GetValueAsString("topicName"));

                // ToDo: Hier noch die Liste von PlayerIds bearbeiten und Player erstellen!
                clientModel.Connected = true;
            }
        }
    }
}
