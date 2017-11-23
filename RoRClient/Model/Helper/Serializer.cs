using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace RoRClient.Model.Helper
{
    static class Serializer
    {
        public static string serialize(Command cmd)
        {
            return JsonConvert.SerializeObject(cmd);
        }

        public static Command deserialize(string input)
        {
            return JsonConvert.DeserializeObject<Command>(input);
        }
    }
}
