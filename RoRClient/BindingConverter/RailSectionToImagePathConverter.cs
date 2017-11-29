using RoRClient.Model.Models;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Data;

namespace RoRClient.BindingConverter
{
    class RailSectionToImagePathConverter : IValueConverter
    {
        private const string IMAGE_FOLDER_PATH = "..\\..\\Ressourcen\\images\\";
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            // TODO: beide sections zum converten nutzen
            Rail rail = (Rail)value;
            List<RailSectionPosition> positionList = rail.Section1.GetNodesAsList();
            if(positionList.Contains(RailSectionPosition.NORTH) && positionList.Contains(RailSectionPosition.SOUTH)){
                return IMAGE_FOLDER_PATH + "rail_straight_v1.svg";
            }
            return "";
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }

}
