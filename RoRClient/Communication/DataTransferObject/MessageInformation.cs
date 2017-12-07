using System;
using System.Collections.Generic;
using Newtonsoft.Json.Linq;

namespace RoRClient.Communication.DataTransferObject
{
    //Klasse für den Inhalt der Messages an den Server (folgt dem Schema: ClientId, Request, Attributes)
    public class MessageInformation
    {
        private readonly string _clientId;
        private string _messageId;
        private Dictionary<string, Object> _attributes;
      
        /// <summary>
        /// Klein geschriebene Property, damit Server das richtig deserialisieren kann
        /// </summary>
        public String clientId
        {
            get
            {
                return _clientId;
            }
        }

        /// <summary>
        /// Klein geschriebene Property, damit Server das richtig deserialisieren kann
        /// </summary>
        public String messageId
        {
            get
            {
                return _messageId;
            }
        }

        public Guid MessageIdAsGuid()
        {
            return Guid.Parse(_messageId);
        }

        /// <summary>
        /// Klein geschriebene Property, damit Server das richtig deserialisieren kann
        /// </summary>
        public Dictionary<string, Object> attributes
        {
            get
            {
                return _attributes;
            }
        }

        public MessageInformation(string messageType)
        {
            
        }

        public MessageInformation()
        {
            _clientId = ClientConnection.GetInstance().ClientId.ToString();
            _messageId = Guid.NewGuid().ToString();
            _attributes = new Dictionary<string, object>();
        }

        public void PutValue(string key, object value)
        {
            _attributes.Add(key, value);
        }
        
        public String GetValueAsString(string key)
        {
            return (string)_attributes[key];
        }

        public int GetValueAsInt(string key)
        {
            object obj = _attributes[key];
            if(obj.GetType() == typeof(double))
            {
                return Convert.ToInt32(obj);
            }
            string temp = obj.GetType().ToString();
            return Convert.ToInt32(obj);
        }

        public double GetValueAsDouble(string key)
        {
            return (double)_attributes[key];
        }

        public bool GetValueAsBool(string key)
        {
            return (bool)_attributes[key];
        }

        public JObject GetValueAsJsonObject(string key)
        {
            return JObject.Parse(GetValueAsString(key));
        }
    }
}
