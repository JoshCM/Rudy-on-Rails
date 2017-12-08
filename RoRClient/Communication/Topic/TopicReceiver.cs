﻿using Apache.NMS;
using Apache.NMS.ActiveMQ;
using Apache.NMS.ActiveMQ.Commands;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Editor;
using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.Dispatcher;

namespace RoRClient.Communication.Topic
{
    /// <summary>
    /// Annahme der Messages für Game/Editor
    /// </summary>
    class TopicReceiver
    {
       
        protected string topicName;
        protected ITopic topic;
        private IMessageConsumer messageConsumer;
        ISession session;
        TopicDispatcherBase dispatcher;


        public TopicReceiver(string topicName, TopicDispatcherBase dispatcher)
        {
            this.topicName = topicName;
            this.dispatcher = dispatcher;
            StartConnection();
        }

        private void StartConnection()
        {
            session = ClientConnection.GetInstance().Session; 
            topic = new ActiveMQTopic(topicName);
            Console.WriteLine("startet messageconsumer (topicReceiver)");
            messageConsumer = session.CreateConsumer(topic);
            messageConsumer.Listener += OnMessageReceived;
            Console.WriteLine("startet connection(topicReceiver)");
        }

        public void OnMessageReceived(IMessage message)
        {
            ITextMessage textMessage = message as ITextMessage;
            string messageType = message.NMSType;
            MessageInformation messageInformation = MessageDeserializer.getInstance().Deserialize(textMessage.Text);
            // Der Dispatcher (Game/Editor) wird bei Erstellung mitgegeben
            dispatcher.Dispatch(messageType, messageInformation);

            Console.WriteLine("Folgende Änderung am Game erhalten: " + textMessage.Text+"(topicReceiver)");
        }
    }
}
