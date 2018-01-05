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
    public class RailSectionsToImagePathConverter : IValueConverter
    {
        private const string IMAGE_FOLDER_PATH = "..\\..\\Resources\\Images\\";
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            if (value != null)
            {
                ObservableCollection<RailSection> railSections = (ObservableCollection<RailSection>)value;

                if(railSections.Count == 1)
                {
                    RailSection railSection = railSections.First();
                    List<Compass> positionList = railSection.GetNodesAsList();

                    if (positionList.Contains(Compass.NORTH) && positionList.Contains(Compass.SOUTH))
                    {
                        return IMAGE_FOLDER_PATH + "rail_ns.png";
                    }
                    else if (positionList.Contains(Compass.EAST) && positionList.Contains(Compass.WEST))
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
                else if (railSections.Count == 2)
                {
                    bool northSouth = railSections.Where(x => x.GetNodesAsList().Contains(Compass.NORTH) && x.GetNodesAsList().Contains(Compass.SOUTH)).Any();
                    bool eastWest = railSections.Where(x => x.GetNodesAsList().Contains(Compass.EAST) && x.GetNodesAsList().Contains(Compass.WEST)).Any();
                    bool southEast = railSections.Where(x => x.GetNodesAsList().Contains(Compass.SOUTH) && x.GetNodesAsList().Contains(Compass.EAST)).Any();



                    if (northSouth && eastWest)
                    {
                        return IMAGE_FOLDER_PATH + "rail_crossing.png";
                    }
                    else if (northSouth && southEast)
                    {
                        return IMAGE_FOLDER_PATH + "switch_sn_se.png";
                    }
                }
            }

            return IMAGE_FOLDER_PATH + "dummy.png";
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }

}
