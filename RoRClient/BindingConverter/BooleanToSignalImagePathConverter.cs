using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Data;
using System.Windows.Media;

namespace RoRClient.BindingConverter
{
    /// <summary>
    /// Wird genutzt, um bei einem RailViewModel zu entscheiden, um welchen Schienentyp es sich handelt und gibt dann den Pfad zum passenden
    /// Bild zurück
    /// </summary>
    public class BooleanToSignalImagePathConverter : IValueConverter
    {
        private const string IMAGE_FOLDER_PATH = "..\\..\\Resources\\Images\\";

        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            bool boolValue = (bool)value;

            if(!boolValue) // inactive == ich darf drüber fahren
            {
                return IMAGE_FOLDER_PATH + "signal_green.png";
            } else
            {
                return IMAGE_FOLDER_PATH + "signal_red.png";
            }
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }

}
