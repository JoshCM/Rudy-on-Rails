using RoRClient.Communication.Dispatcher;
using RoRClient.Communication.Topic;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Session
{
    public class GameSession : RoRSession
    {
        private static GameSession session = null;
        private GameSession() : base()
        {
            
        }

        public static GameSession GetInstance()
        {
            if (session == null)
            {
                session = new GameSession();
            }
            return session;
        }

        public new void Init(string topicName)
        {
            base.Init(topicName);
            topicReceiver = new TopicReceiver(topicName, new TopicGameDispatcher());
        }
    }
}