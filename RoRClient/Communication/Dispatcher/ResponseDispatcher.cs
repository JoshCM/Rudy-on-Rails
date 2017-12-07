using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Editor;
using RoRClient.Models.Game;
using System;

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
                editorSession.Name = messageInformation.GetValueAsString("Editorname");
                editorSession.Init(messageInformation.GetValueAsString("Topicname"));

                Guid playerId = Guid.Parse(messageInformation.GetValueAsString("Playerid"));
                string playerName = messageInformation.GetValueAsString("Playername");
                Player player = new Player(playerId, playerName);
                editorSession.AddPlayer(player);
                clientModel.Conncected = true;
            }
            else if(messageType == "JoinEditorSession")
            {
                EditorSession editorSession = EditorSession.GetInstance();
                editorSession.Name = messageInformation.GetValueAsString("Editorname");
                editorSession.Init(messageInformation.GetValueAsString("Topicname"));

                // ToDo: Hier noch die Liste von PlayerIds bearbeiten und Player erstellen!
                clientModel.Conncected = true;
            }
        }
    }
}
