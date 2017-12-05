using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Model.Models;

namespace RoRClient.Model.DataTransferObject
{
    //Klasse für den Inhalt der Messages an den Server (folgt dem Schema: ClientId, Request, Attributes)
    class MessageInformation
    {
        public readonly String clientId;
        public Dictionary<String, String> attributes;

        public MessageInformation(Dictionary<String, String> attributes)
        {
            clientId = ClientModel.getInstance().getClientId().ToString();
            this.attributes = attributes;
        }
    }
}
