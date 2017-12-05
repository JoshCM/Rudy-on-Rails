﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Model.Models;
using Newtonsoft.Json.Linq;
using RoRClient.Model.Connections;

namespace RoRClient.Model.DataTransferObject
{
    //Klasse für den Inhalt der Messages an den Server (folgt dem Schema: ClientId, Request, Attributes)
    class MessageInformation
    {
        public readonly String clientId;
        public Dictionary<string, Object> attributes;

        public MessageInformation()
        {
            clientId = ClientConnection.GetInstance().ClientId.ToString();
            attributes = new Dictionary<string, object>();
        }

        public void PutValue(string key, object value)
        {
            attributes.Add(key, value);
        }
        
        public String GetValueAsString(string key)
        {
            return (string)attributes[key];
        }

        public int GetValueAsInt(string key)
        {
            return (int)attributes[key];
        }

        public double GetValueAsDouble(string key)
        {
            return (double)attributes[key];
        }

        public bool GetValueAsBool(string key)
        {
            return (bool)attributes[key];
        }

        public JObject GetValueAsJsonObject(string key)
        {
            return JObject.Parse(GetValueAsString(key));
        }
    }
}
