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

namespace RoRClient.BindingConverter
{
    class PlayerToImagePathConverter : IValueConverter
    {
        private const string IMAGE_FOLDER_PATH = "..\\..\\Resources\\Images\\";
        private const string STOCK_IMAGE_START = "stock_container_";
        private const string LOCO_IMAGE_START = "loco_";
        private const string IMAGE_ENDING = ".png";
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            if(value is Loco)
            {
                Loco loco = (Loco)value;
                Player player = GameSession.GetInstance().GetPlayerById(loco.PlayerId);
                int playerIndex = GameSession.GetInstance().Players.IndexOf(player);
                return IMAGE_FOLDER_PATH + LOCO_IMAGE_START + playerIndex + IMAGE_ENDING;
            }
            else if(value is Stock)
            {
                Stock stock = (Stock)value;
                Playertrainstation playerTrainstation = (Playertrainstation)GameSession.GetInstance().Map.GetPlaceableById(stock.TrainstationId);
                int playerIndex = GameSession.GetInstance().Players.IndexOf(playerTrainstation.Player);
                return IMAGE_FOLDER_PATH + STOCK_IMAGE_START + playerIndex + IMAGE_ENDING;
            }
            else
            {
                // wenn type nicht bestimmt muss das hier zurückgegeben werden
                return null;
            }
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }
}
