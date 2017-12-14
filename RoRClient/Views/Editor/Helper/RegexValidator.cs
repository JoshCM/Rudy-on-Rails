using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace RoRClient.Views.Editor.Helper
{
    public class RegexValidator
    {
        public static bool IsValidFilename(string filename)
        {
            string filenamePattern = @"^[\w\-. ]+$";
            return Regex.IsMatch(filename, filenamePattern);
        }
    }
}
