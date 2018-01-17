using RoRClient.Communication.Queue;
using RoRClient.Communication.Topic;
using RoRClient.Models.Base;
using RoRClient.Models.Game;
using System.Collections.ObjectModel;
using RoRClient.Communication.Dispatcher;

namespace RoRClient.Models.Session
{
    /// <summary>
    /// Hält alle nötigen Informationen für eine Session im Editor
    /// Initialisiert einen QueueSender und einen TopiReceiver
    /// </summary>
    public class EditorSession : RoRSession
    {
        private static EditorSession editorSession;

        private EditorSession() : base()
        {
        }

        public new void Init(string topicName)
        {
            base.Init(topicName);
            topicReceiver = new TopicReceiver(topicName, new TopicEditorDispatcher());
        }

        public static EditorSession GetInstance()
        {
            if(editorSession == null)
            {
                editorSession = new EditorSession();
            }
            return editorSession;
        }

        public void DeleteEditorSession()
        {
            if (topicReceiver != null)
            {
                topicReceiver.StopConnection();
            }
            editorSession = null;
            NotifyPropertyChanged("EditorSessionDeleted");
        }
    }
}