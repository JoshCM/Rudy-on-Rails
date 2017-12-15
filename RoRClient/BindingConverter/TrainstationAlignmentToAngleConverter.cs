using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Data;

namespace RoRClient.BindingConverter
{
    class TrainstationAlignmentToAngleConverter : IValueConverter
    {
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            Compass alignment = (Compass)value;
            switch (alignment)
            {
                case Compass.NORTH: return -90;
                case Compass.EAST: return 0;
                case Compass.SOUTH: return 90;
                case Compass.WEST: return -180;
                default: return 0;
            }
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }
}
