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
                String railsection = parameter as string;
                if (railSections.Count == 1)
                {
                    RailSection railSection = railSections.First();

                    List<Compass> directionsLis = railSection.GetNodesAsList();
                    int i = Compass.NORTH.CompareTo(Compass.SOUTH);
                    int j = Compass.SOUTH.CompareTo(Compass.NORTH);


                    String imageName = "rail_"+directionsLis[0].ToString() + "_"+ directionsLis[1].ToString() + ".png";

                    return IMAGE_FOLDER_PATH + imageName;
                  
                }
                else if (railSections.Count == 2)
                {
                    bool northSouth = railSections.Where(x => x.GetNodesAsList().Contains(Compass.NORTH) && x.GetNodesAsList().Contains(Compass.SOUTH)).Any();
                    bool eastWest = railSections.Where(x => x.GetNodesAsList().Contains(Compass.EAST) && x.GetNodesAsList().Contains(Compass.WEST)).Any();

                    /// To -Do 
                    /// aus RS String generieren der imagepath entspricht und diesen auf bild mappen
                    /// ausnahme für crossing hinzufügen
                    if (railsection == "1")
                    {

                        return IMAGE_FOLDER_PATH + "rail_ns.png";

                    }
                    else if (railsection == "2") {
                        return IMAGE_FOLDER_PATH + "rail_ew.png";

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
