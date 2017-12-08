using Newtonsoft.Json.Linq;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Editor;
using RoRClient.Models.Game;
using System;
using System.Collections.Generic;

namespace RoRClient.Communication.Dispatcher
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
                string editorName = messageInformation.GetValueAsString("editorName");
                editorSession.Name = editorName;
                string topicName = messageInformation.GetValueAsString("topicName");
                editorSession.Init(topicName);

                List<JObject> playersList = messageInformation.GetValueAsJObjectList("playerList");
                foreach(JObject obj in playersList)
                {
                    Guid playerId = Guid.Parse(obj.GetValue("playerId").ToString());
                    string playerName = obj.GetValue("playerName").ToString();
                    Player player = new Player(playerId, playerName);
                    editorSession.AddPlayer(player);
                }

                clientModel.Connected = true;
            }
        }
    }
}
