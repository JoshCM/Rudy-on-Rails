﻿using Apache.NMS;
using RoRClient.Model.Connections;
using RoRClient.Model.DataTransferObject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.HandleResponse
{
    class ResponseDispatcher
    {
        private static ResponseDispatcher responseDispatcher;
        private TopicReceiver topic;
        private ResponseDispatcher()
        {

        }

        public static ResponseDispatcher getInstance()
        {
            if (responseDispatcher == null)
            {
                responseDispatcher = new ResponseDispatcher();
            }
            return responseDispatcher;
        }

        /// <summary>
        /// Aufgabe die Message zu "dispatchen" um zu entscheiden, was damit passieren soll, je nach MessageType
        /// </summary>
        /// <param name="message"></param>
		internal void dispatch(string messageType, ITextMessage textMessage)
        {
           
            Console.WriteLine("ResponseHandler macht was");
            MessageInformation messageInformation= MessageDeserializer.getInstance().deserialize(textMessage.Text);

            //initalen TopicReceiver erstellen zum test des EditorTopics
            if(messageType == "")
            if (topic == null)
            {
                topic = new TopicReceiver(messageInformation.GetAttribute("Topicname"));
            }
        }

        /// <summary>
        /// Hier wird entschieden was bei einem bestimmten RequestTyps des MessageTyps CREATE gemacht wird
        /// </summary>
        void resolveCreateResponses(RequestType requestType)
        {

        }

        void resolveErrorResponses(RequestType requestType)
        {

        }
        void resolveUpdateResponses(RequestType requestType)
        {

        }
        void resolveReadResponses(RequestType requestType)
        {

        }




    }
}
