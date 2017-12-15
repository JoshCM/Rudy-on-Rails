using Newtonsoft.Json.Linq;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;
using System;
using System.Collections.Generic;

namespace RoRClient.Communication.Dispatcher
{
    class FromServerResponseQueueDispatcher : QueueDispatcherBase
    {
        private LobbyModel lobbyModel;

        public FromServerResponseQueueDispatcher(LobbyModel lobbyModel)
        {
            this.lobbyModel = lobbyModel;
        }

        public void handleCreateEditorSession(MessageInformation messageInformation)
        {
            EditorSession editorSession = EditorSession.GetInstance();
            editorSession.Name = messageInformation.GetValueAsString("editorName");
            editorSession.Init(messageInformation.GetValueAsString("topicName"));

            Guid playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));
            string playerName = messageInformation.GetValueAsString("playerName");
            Player player = new Player(playerId, playerName);
            editorSession.AddPlayer(player);
            lobbyModel.Connected_Editor = true;
        }

        public void handleJoinEditorSession(MessageInformation messageInformation)
        {
            EditorSession editorSession = EditorSession.GetInstance();
            string editorName = messageInformation.GetValueAsString("editorName");
            editorSession.Name = editorName;
            string topicName = messageInformation.GetValueAsString("topicName");
            editorSession.Init(topicName);

            List<JObject> playersList = messageInformation.GetValueAsJObjectList("playerList");
            foreach (JObject obj in playersList)
            {
                Guid playerId = Guid.Parse(obj.GetValue("playerId").ToString());
                string playerName = obj.GetValue("playerName").ToString();
                Player player = new Player(playerId, playerName);
                editorSession.AddPlayer(player);
            }

            lobbyModel.Connected_Editor = true;
        }

        public void handleCreateGameSession(MessageInformation messageInformation)
        {
            GameSession gameSession = GameSession.GetInstance();
            gameSession.Name = messageInformation.GetValueAsString("gameName");
            gameSession.Init(messageInformation.GetValueAsString("topicName"));

            // DefaultMap beim Erstellen der Map laden
            gameSession.loadDefaultMapAtStartup();

            Guid playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));
            string playerName = messageInformation.GetValueAsString("playerName");
            Player player = new Player(playerId, playerName);
            gameSession.AddPlayer(player);
            lobbyModel.Connected_Game = true;
        }

        public void handleJoinGameSession(MessageInformation messageInformation)
        {
            GameSession gameSession = GameSession.GetInstance();
            string gameName = messageInformation.GetValueAsString("gameName");
            gameSession.Name = gameName;
            string topicName = messageInformation.GetValueAsString("topicName");
            gameSession.Init(topicName);

            List<JObject> playersList = messageInformation.GetValueAsJObjectList("playerList");
            foreach (JObject obj in playersList)
            {
                Guid playerId = Guid.Parse(obj.GetValue("playerId").ToString());
                string playerName = obj.GetValue("playerName").ToString();
                Player player = new Player(playerId, playerName);
                gameSession.AddPlayer(player);
            }

            lobbyModel.Connected_Game = true;
        }
    }
}
