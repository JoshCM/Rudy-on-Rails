using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Data;

namespace RoRClient.BindingConverter
{
    /// <summary>
    /// Wird genutzt, um bei einem RailViewModel zu entscheiden, um welchen Schienentyp es sich handelt und gibt dann den Pfad zum passenden
    /// Bild zurück
    /// </summary>
    public class RailSectionToImagePathConverter : IValueConverter
    {
        private const string IMAGE_FOLDER_PATH = "..\\..\\Resources\\Images\\";
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            if (value != null)
            {
                RailSection railSection = (RailSection)value;
                List<Compass> positionList = railSection.GetNodesAsList();

                if (positionList.Contains(Compass.NORTH) && positionList.Contains(Compass.SOUTH))
                {
                    return IMAGE_FOLDER_PATH + "rail_ns.png";
                }else if(positionList.Contains(Compass.EAST) && positionList.Contains(Compass.WEST))
                {
                    return IMAGE_FOLDER_PATH + "rail_ew.png";
                }
                else if (positionList.Contains(Compass.SOUTH) && positionList.Contains(Compass.WEST))
                {
                    return IMAGE_FOLDER_PATH + "railcurve_sw.png";
                }
                else if (positionList.Contains(Compass.SOUTH) && positionList.Contains(Compass.EAST))
                {
                    return IMAGE_FOLDER_PATH + "railcurve_se.png";
                }
                else if (positionList.Contains(Compass.NORTH) && positionList.Contains(Compass.WEST))
                {
                    return IMAGE_FOLDER_PATH + "railcurve_nw.png";
                }
                else if (positionList.Contains(Compass.NORTH) && positionList.Contains(Compass.EAST))
                {
                    return IMAGE_FOLDER_PATH + "railcurve_ne.png";
                }
            }

            // ToDo: Hier brauchen wir ein Dummy-Bild oder so, das ganz total transparent ist
            return IMAGE_FOLDER_PATH + "dummy.png";
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }

}
