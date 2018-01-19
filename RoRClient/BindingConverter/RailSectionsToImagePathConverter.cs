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
    /// Bild zurück. Rails mit mehreren Railsection (z. B. Weichen) werden aus zwei übereinander gelagerten Bildern erzeugt.
    /// parameter entscheidet welches Railsection-Bild bei mehreren Schienen übergeordnet angezeigt werden soll
    public class RailSectionsToImagePathConverter : IValueConverter
    {
        private const string IMAGE_FOLDER_PATH = "..\\..\\Resources\\Images\\";
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            if (value != null)
            {
                ObservableCollection<RailSection> railSections = (ObservableCollection<RailSection>)value;
                bool northSouth = railSections.Where(x => x.GetNodesAsList().Contains(Compass.NORTH) && x.GetNodesAsList().Contains(Compass.SOUTH)).Any();
                bool eastWest = railSections.Where(x => x.GetNodesAsList().Contains(Compass.EAST) && x.GetNodesAsList().Contains(Compass.WEST)).Any();
                String param = parameter as string;
                RailSection railSection1 = railSections.First();

                List<String> directionsLis = railSection1.GetNodesAsSortedStringList();

                if (param.Contains("1"))
                {

                    if (railSections.Count == 2)
                    {
                        /// evtl sowas ?railSections.Where(x => x.GetNodesAsList().Contains(Compass.NORTH) && x.GetNodesAsList().Contains(Compass.SOUTH)).Any();

                        foreach (RailSection railSection in railSections)
                        {
                            if (railSection.Status == RailSectionStatus.INACTIVE)
                            {
                                return IMAGE_FOLDER_PATH + "rail_" + railSection.GetNodesAsSortedStringList()[0] + "_" + railSection.GetNodesAsSortedStringList()[1] + "_inactive.png";
                            }
                        }
                    }

                }
                else if (param.Contains("2"))
                {
                    if (northSouth && eastWest)
                    {
                        return IMAGE_FOLDER_PATH + "crossing.png";
                    }

                    foreach (RailSection railSection in railSections)
                    {
                        if (railSection.Status == RailSectionStatus.ACTIVE)
                        {
                            return IMAGE_FOLDER_PATH + "rail_" + railSection.GetNodesAsSortedStringList()[0] + "_" + railSection.GetNodesAsSortedStringList()[1] + ".png";
                        }
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
