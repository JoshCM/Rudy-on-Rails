using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Model.Models;

namespace RoRClient.Model.Helper
{
    //Klasse für den Inhalt der Messages an den Server (folgt dem Schema: ClientId, Request, Attributes)
    class Content
    {
        public readonly String clientId;
        public String request;
        public Dictionary<String, String> attributes;

        public Content(RequestType request, Dictionary<String, String> attributes)
        {
            clientId = ClientModel.getInstance().getClientId().ToString();
            this.request = request.ToString();
            this.attributes = attributes;


        }

    }
}
