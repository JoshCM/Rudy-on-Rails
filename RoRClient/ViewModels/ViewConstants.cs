using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels
{
    /// <summary>
    /// Hier kommen alle Konstanten hin, die die Views betreffen
    /// </summary>
    static class ViewConstants
    {
        public const int SQUARE_DIM = 150;
        public const int SIGNAL_DIMENSION = 35;
        public const int UPPER_SIGNAL_POS = 5;
        public const int LOWER_SIGNAL_POS = SQUARE_DIM - UPPER_SIGNAL_POS - SIGNAL_DIMENSION;
    }
}
