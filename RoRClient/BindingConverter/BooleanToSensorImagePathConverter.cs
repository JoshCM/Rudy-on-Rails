using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Data;

namespace RoRClient.BindingConverter
{
    public class BooleanToSensorImagePathConverter : IValueConverter
    {

        private const string IMAGE_FOLDER_PATH = "..\\..\\Resources\\Images\\";

        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            bool boolValue = (bool)value;
            string pathToImage;

            if (boolValue)
            {
                pathToImage = IMAGE_FOLDER_PATH + "sensor_active.png";
            }
            else
            {
                pathToImage = IMAGE_FOLDER_PATH + "sensor_inactive.png";
            }
            return pathToImage;
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }
}
