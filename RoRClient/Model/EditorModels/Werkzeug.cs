using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.EditorModels
{
    class Werkzeug
    {
        private readonly Func<string, string> _convertion;

        public Werkzeug(Func<string, string> convertion)
        {
            _convertion = convertion;
        }

        public string ConvertText(string inputText)
        {
            return _convertion(inputText);
        }
    }
}
