using RoRClient.Model.Models;
using RoRClient.ViewModel;
using System;
using System.Collections.Generic;
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
        private const string IMAGE_FOLDER_PATH = "..\\..\\Ressourcen\\Images\\";
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            if (value != null)
            {
                RailViewModel railViewModel = (RailViewModel)value;
                Rail rail = railViewModel.Rail;

                // Beide Sections (section1, section2) sollen zum converten genutzt werden
                List<RailSectionPosition> positionList = rail.Section1.GetNodesAsList();
                if (positionList.Contains(RailSectionPosition.NORTH) && positionList.Contains(RailSectionPosition.SOUTH))
                {
                    return IMAGE_FOLDER_PATH + "rail_ns.png";
                }else if(positionList.Contains(RailSectionPosition.EAST) && positionList.Contains(RailSectionPosition.WEST))
                {
                    return IMAGE_FOLDER_PATH + "rail_ew.png";
                }
                else if (positionList.Contains(RailSectionPosition.SOUTH) && positionList.Contains(RailSectionPosition.WEST))
                {
                    return IMAGE_FOLDER_PATH + "railcurve_sw.png";
                }
                else if (positionList.Contains(RailSectionPosition.SOUTH) && positionList.Contains(RailSectionPosition.EAST))
                {
                    return IMAGE_FOLDER_PATH + "railcurve_se.png";
                }
                else if (positionList.Contains(RailSectionPosition.NORTH) && positionList.Contains(RailSectionPosition.WEST))
                {
                    return IMAGE_FOLDER_PATH + "railcurve_nw.png";
                }
                else if (positionList.Contains(RailSectionPosition.NORTH) && positionList.Contains(RailSectionPosition.EAST))
                {
                    return IMAGE_FOLDER_PATH + "railcurve_ne.png";
                }
            }
            return "";
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }

}
