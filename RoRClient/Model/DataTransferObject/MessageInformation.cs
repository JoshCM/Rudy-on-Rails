using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Model.Models;
using Newtonsoft.Json.Linq;

namespace RoRClient.Model.DataTransferObject
{
    //Klasse für den Inhalt der Messages an den Server (folgt dem Schema: ClientId, Request, Attributes)
    class MessageInformation
    {
        public readonly String clientId;
        public Dictionary<string, Object> attributes;

        public MessageInformation(Dictionary<string, Object> attributes)
        {
            clientId = ClientModel.getInstance().getClientId().ToString();
            this.attributes = attributes;
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
