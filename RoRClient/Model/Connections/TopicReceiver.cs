﻿using Apache.NMS;
using Apache.NMS.ActiveMQ;
using Apache.NMS.ActiveMQ.Commands;
using RoRClient.Model.DataTransferObject;
using RoRClient.Model.Models;
using RoRClient.Model.Models.Editor;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Connections
{
    class TopicReceiver
    {
       
        protected string topicName;
        protected ITopic topic;
        private IMessageConsumer messageConsumer;
        ISession session;


        public TopicReceiver(string topicName)
        {
            this.topicName = topicName;
            StartConnection();
        }

        private void StartConnection()
        {
            session=ClientConnection.GetInstance().Session; 
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
            MessageInformation messageInformation = MessageDeserializer.getInstance().deserialize(textMessage.Text);

            if(messageType == "CreateRail")
            {
                Guid railId = Guid.Parse(messageInformation.GetValueAsString("railId"));
                int xPos = messageInformation.GetValueAsInt("xPos");
                int yPos = messageInformation.GetValueAsInt("yPos");
                RailSectionPosition node1 = (RailSectionPosition)Enum.Parse(typeof(RailSectionPosition), messageInformation.GetValueAsString("railSectionPositionNode1"));
                RailSectionPosition node2 = (RailSectionPosition)Enum.Parse(typeof(RailSectionPosition), messageInformation.GetValueAsString("railSectionPositionNode2"));
                EditorSession editorSession = EditorSession.GetInstance();
                Square square = editorSession.Map.GetSquare(xPos, yPos);
                Rail rail = new Rail(railId, square, new RailSection(node1, node2));
                square.PlaceableOnSquare = rail;
            }

            Console.WriteLine("Folgende Änderung am Game erhalten: " + textMessage.Text+"(topicReceiver)");

            // ToDo: Dispatcher 
        }
    }
}
