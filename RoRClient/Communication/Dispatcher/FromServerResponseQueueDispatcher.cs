using Newtonsoft.Json.Linq;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using RoRClient.Models.Lobby;
using System.Windows;

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
            Player player = new Player(playerId, playerName, true);
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
                bool isHost = Boolean.Parse(obj.GetValue("isHost").ToString());
                Player player = new Player(playerId, playerName, isHost);
                editorSession.AddPlayer(player);
            }

            lobbyModel.Connected_Editor = true;
        }

        public void HandleCreateGameSession(MessageInformation messageInformation)
        {
            GameSession gameSession = GameSession.GetInstance();
            gameSession.Name = messageInformation.GetValueAsString("gameName");
            gameSession.Init(messageInformation.GetValueAsString("topicName"));

            Guid playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));
            string playerName = messageInformation.GetValueAsString("playerName");
            Player player = new Player(playerId, playerName, true);

	        gameSession.AddPlayer(player);

	        GameInfo gameInfo = new GameInfo(player);
	        lobbyModel.AddGameInfo(gameInfo);

			lobbyModel.Connected_Game = true;
        }

        public void HandleJoinGameSession(MessageInformation messageInformation)
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
                bool isHost = Boolean.Parse(obj.GetValue("isHost").ToString());
                Player player = new Player(playerId, playerName, isHost);
	            gameSession.AddPlayer(player);
	            GameInfo gameInfo = new GameInfo(player);
	            lobbyModel.AddGameInfo(gameInfo);

			}
			lobbyModel.Connected_Game = true;
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

	    public void HandleReadGameInfos(MessageInformation messageInformation)
	    {
			lobbyModel.ClearGameInfos();

		    GameSession gameSession = GameSession.GetInstance();

		    List<JObject> gameInfoList = messageInformation.GetValueAsJObjectList("gameInfo");
		    foreach (JObject obj in gameInfoList)
		    {
			    Guid playerId = Guid.Parse(obj.GetValue("playerId").ToString());
			    Player player = gameSession.GetPlayerById(playerId);
				GameInfo gameInfo = new GameInfo(player);
			    lobbyModel.AddGameInfo(gameInfo);
		    }
		}

	    public void HandleReadMapInfos(MessageInformation messageInformation)
	    {
		    lobbyModel.ClearMapInfos();

		    GameSession gameSession = GameSession.GetInstance();

		    List<JObject> mapInfoList = messageInformation.GetValueAsJObjectList("mapInfo");
		    foreach (JObject obj in mapInfoList)
		    {
			    string name = obj.GetValue("mapName").ToString();
			    MapInfo mapInfo = new MapInfo(name);
			    lobbyModel.AddMapInfo(mapInfo);
		    }
	    }

        /// <summary>
        /// Für Fehler auf dem Server wird erst einmal eine MessageBox aufgerufen mit dem Fehlertyp als Inhalt
        /// ToDo: Dies sollte noch eleganter gelöst werden! Für den Moment erstmal ausreichend.
        /// </summary>
        /// <param name="messageInformation"></param>
        public void HandleError(MessageInformation messageInformation)
        {
            String type = messageInformation.GetValueAsString("type");

            switch (type)
            {
                case "SessionNotFound":
                    MessageBox.Show(type);
                    break;
                case "SessionAlreadyStarted":
                    MessageBox.Show(type);
                    break;
                default:
                    MessageBox.Show(type);
                    break;
            }
        }
	}
}
