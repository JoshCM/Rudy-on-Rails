using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

namespace RoRClient.Model.Helper
{
    class Command
    {
        /* Ohne JsonConverter: CommandEnum wird nur als Integer
         * in Json serialisiert CREATE kommt also serverseitig als 0 an*/
        [JsonConverter(typeof(StringEnumConverter))]
        public enum CommandEnum { CREATE, UPDATE, DELETE };

        public Dictionary<String, String> attributes;
        public CommandEnum cEnum { get; set; }
        public Command()
        {

        }
    }
}
