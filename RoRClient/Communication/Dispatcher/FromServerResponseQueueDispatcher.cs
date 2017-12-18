using Newtonsoft.Json.Linq;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using RoRClient.Models.Lobby;

namespace RoRClient.Communication.Dispatcher
{
    class FromServerResponseQueueDispatcher : QueueDispatcherBase
    {
        private LobbyModel lobbyModel;

        public FromServerResponseQueueDispatcher(LobbyModel lobbyModel)
        {
            this.lobbyModel = lobbyModel;
        }

        public void HandleCreateEditorSession(MessageInformation messageInformation)
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

        public void HandleJoinEditorSession(MessageInformation messageInformation)
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

        public void HandleCreateGameSession(MessageInformation messageInformation)
        {
            GameSession gameSession = GameSession.GetInstance();
            gameSession.Name = messageInformation.GetValueAsString("gameName");
            gameSession.Init(messageInformation.GetValueAsString("topicName"));

            // DefaultMap beim Erstellen der Map laden
            gameSession.LoadDefaultMapAtStartup();

            Guid playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));
            string playerName = messageInformation.GetValueAsString("playerName");
            Player player = new Player(playerId, playerName);
            gameSession.AddPlayer(player);

            lobbyModel.Connected_Game = true;

            //TODO: hier soll ein Create Loco Command angestoßen werden
            SendCreateLocoCommand(playerId);
        }

        public void HandleJoinGameSession(MessageInformation messageInformation)
        {
            GameSession gameSession = GameSession.GetInstance();
            string gameName = messageInformation.GetValueAsString("gameName");
            gameSession.Name = gameName;
            string topicName = messageInformation.GetValueAsString("topicName");
            gameSession.Init(topicName);

            gameSession.LoadDefaultMapAtStartup();

            List<JObject> playersList = messageInformation.GetValueAsJObjectList("playerList");
            foreach (JObject obj in playersList)
            {
                Guid playerId = Guid.Parse(obj.GetValue("playerId").ToString());
                string playerName = obj.GetValue("playerName").ToString();
                Player player = new Player(playerId, playerName);
                gameSession.AddPlayer(player);
            }

            lobbyModel.Connected_Game = true;
            //TODO: hier soll ein Create Loco Command angestoßen werden
        }

        /// <summary>
        /// Methode, die eine Message an den Server schicken soll, dass eine Lok für den jeweiligen Player erstellt werden soll
        /// </summary>
        /// <param name="playerId"></param> playerId des Players, dem die Lok zugeordnet werden soll
        private void SendCreateLocoCommand(Guid playerId)
        {
            MessageInformation messageInformation = new MessageInformation();
            int xPos = 7;
            int yPos = 3;

            messageInformation.PutValue("xPos", xPos);
            messageInformation.PutValue("yPos", yPos);
            messageInformation.PutValue("playerId", playerId);
        
            GameSession gameSession = GameSession.GetInstance();
            gameSession.QueueSender.SendMessage("CreateLoco", messageInformation);
        }

        public void HandleReadEditorSessions(MessageInformation messageInformation)
        {
            lobbyModel.ClearEditorSessionInfos();

            List<JObject> editorSessionInfoList = messageInformation.GetValueAsJObjectList("editorSessionInfo");
            foreach (JObject obj in editorSessionInfoList)
            {
                string name = obj.GetValue("name").ToString();
                int amountOfPlayers = (int)obj.GetValue("amountOfPlayers");
                EditorSessionInfo editorSessionInfo = new EditorSessionInfo(name, amountOfPlayers);
                lobbyModel.AddEditorSessionInfo(editorSessionInfo);
            }
        }

        public void HandleReadGameSessions(MessageInformation messageInformation)
        {
            lobbyModel.ClearGameSessionInfos();

            List<JObject> gameSessionInfoList = messageInformation.GetValueAsJObjectList("gameSessionInfo");
            foreach (JObject obj in gameSessionInfoList)
            {
                string name = obj.GetValue("name").ToString();
                int amountOfPlayers = (int)obj.GetValue("amountOfPlayers");
                GameSessionInfo gameSessionInfo = new GameSessionInfo(name, amountOfPlayers);
                lobbyModel.AddGameSessionInfo(gameSessionInfo);
            }
        }
    }
}
