using RoRClient.Communication;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Data;
using static RoRClient.Models.Game.GamePlayer;

namespace RoRClient.BindingConverter
{
    class EditorStockToImagePathConverter : IValueConverter
    {
        private const string IMAGE_FOLDER_PATH = "..\\..\\Resources\\Images\\";
        private const string STOCK_IMAGE_START = "stock_container_";
        private const string IMAGE_ENDING = ".png";
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            if(value is Stock)
            {
                Stock stock = (Stock)value;
                if (stock.TrainstationType == TrainstationType.PLAYER)
                {
                    return IMAGE_FOLDER_PATH + STOCK_IMAGE_START + "0" + IMAGE_ENDING;
                } else if (stock.TrainstationType == TrainstationType.PUBLIC)
                {
                    return IMAGE_FOLDER_PATH + STOCK_IMAGE_START + "public_ts" + IMAGE_ENDING;
                }
            }

            // wenn type nicht bestimmt muss das hier zurückgegeben werden
            return null;
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }
}
