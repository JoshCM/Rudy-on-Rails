﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace RoRClient.Communication.DataTransferObject
{
    class MessageDeserializer
    {

        private static MessageDeserializer messageDeserializer;
        private MessageDeserializer()
        {

        }

        public static MessageDeserializer getInstance()
        {
            if (messageDeserializer == null)
            {
                messageDeserializer = new MessageDeserializer();
            }
            
            return messageDeserializer;
        }

        /// <summary>
        /// hier soll die empfangene Textmessage(ein String) zu einem MessageInformation Objekt deserialisiert werden
        /// </summary>
        /// <param name="textMessage"></param>
        /// <returns></returns>
        internal MessageInformation Deserialize(String textMessage)
        {
            MessageInformation messageInformation = JsonConvert.DeserializeObject<MessageInformation>(textMessage);

            return messageInformation;
        } 
    }
}
