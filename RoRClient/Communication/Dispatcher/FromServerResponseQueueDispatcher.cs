using Newtonsoft.Json.Linq;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using RoRClient.Models.Lobby;
using System.Windows;
using System.ComponentModel;

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
            editorSession.PropertyChanged += OnEditorSessionChanged;

            Guid playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));
            string playerName = messageInformation.GetValueAsString("playerName");
            EditorPlayer player = new EditorPlayer(playerId, playerName, true);
            editorSession.AddPlayer(player);
            EditorInfo editorInfo = new EditorInfo(player);
            lobbyModel.AddEditorInfo(editorInfo);
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
            gameSession.PropertyChanged += OnGameSessionChanged;

            Guid playerId = Guid.Parse(messageInformation.GetValueAsString("playerId"));
            string playerName = messageInformation.GetValueAsString("playerName");
            int coalCount = messageInformation.GetValueAsInt("coalCount");
            int goldCount = messageInformation.GetValueAsInt("goldCount");
            int pointCount = messageInformation.GetValueAsInt("pointCount");
            GamePlayer player = new GamePlayer(playerId, playerName, coalCount, goldCount, pointCount, true);

	        gameSession.AddPlayer(player);

	        GameInfo gameInfo = new GameInfo(player);
	        lobbyModel.AddGameInfo(gameInfo);

			lobbyModel.Connected_Game = true;
        }

        private void OnGameSessionChanged(object sender, PropertyChangedEventArgs e)
        {
            if (e.PropertyName == "GameSessionDeleted")
            {
                lobbyModel.Connected_Game = false;
            }
        }

        private void OnEditorSessionChanged(object sender, PropertyChangedEventArgs e)
        {
            if (e.PropertyName == "EditorSessionDeleted")
            {
                lobbyModel.Connected_Editor = false;
            }
        }

        public void HandleJoinGameSession(MessageInformation messageInformation)
        {
            GameSession gameSession = GameSession.GetInstance();
            string gameName = messageInformation.GetValueAsString("gameName");
            gameSession.Name = gameName;
            string topicName = messageInformation.GetValueAsString("topicName");
            gameSession.PropertyChanged += OnGameSessionChanged;

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

            gameSession.Init(topicName);
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
                int availablePlayerSlots = (int)obj.GetValue("availablePlayerSlots");
                GameSessionInfo gameSessionInfo = new GameSessionInfo(name, amountOfPlayers, availablePlayerSlots);
                lobbyModel.AddGameSessionInfo(gameSessionInfo);
            }
        }

		/// <summary>
		/// Setzt die ankommenden Players mithilfe der PlayerIds in die 
		/// ObservableCollection von Players im LobbyModel
		/// </summary>
		/// <param name="messageInformation"></param>
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

	    /// <summary>
	    /// Setzt die ankommenden Players mithilfe der PlayerIds in die 
	    /// ObservableCollection von Players im LobbyModel
	    /// </summary>
	    /// <param name="messageInformation"></param>
	    public void HandleReadEditorInfos(MessageInformation messageInformation)
	    {
		    lobbyModel.ClearEditorInfos();

		    EditorSession editorSession = EditorSession.GetInstance();

		    List<JObject> gameInfoList = messageInformation.GetValueAsJObjectList("editorInfo");
		    foreach (JObject obj in gameInfoList)
		    {
			    Guid playerId = Guid.Parse(obj.GetValue("playerId").ToString());
			    Player player = editorSession.GetPlayerById(playerId);
			    EditorInfo editorInfo = new EditorInfo(player);
			    lobbyModel.AddEditorInfo(editorInfo);
		    }
	    }

		/// <summary>
		/// Setzt die ankommenden MapNames in die ObservableCollection von MapNames
		/// im LobbyModel
		/// </summary>
		/// <param name="messageInformation"></param>
		public void HandleReadMapInfos(MessageInformation messageInformation)
	    {
		    lobbyModel.ClearMapNames();

		    GameSession gameSession = GameSession.GetInstance();

		    List<JObject> mapInfoList = messageInformation.GetValueAsJObjectList("mapInfo");
		    foreach (JObject obj in mapInfoList)
		    {
			    string mapName = obj.GetValue("mapName").ToString();
			    lobbyModel.AddMapName(mapName);
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
