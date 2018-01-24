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
                GamePlayer player = (GamePlayer)GameSession.GetInstance().GetPlayerById(loco.PlayerId);
                int colorNumber = (int)player.PlayerColor;
                return IMAGE_FOLDER_PATH + LOCO_IMAGE_START + colorNumber + IMAGE_ENDING;
            }
            else if(value is Stock)
            {
                Stock stock = (Stock)value;
                Playertrainstation playerTrainstation = (Playertrainstation)GameSession.GetInstance().Map.GetPlaceableById(stock.TrainstationId);
                GamePlayer player = (GamePlayer)playerTrainstation.Player;
                if (player != null)
                {
                    int colorNumber = (int)player.PlayerColor;
                    return IMAGE_FOLDER_PATH + STOCK_IMAGE_START + colorNumber + IMAGE_ENDING;
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
