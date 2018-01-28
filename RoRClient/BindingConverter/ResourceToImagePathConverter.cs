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
    public class ResourceToImagePathConverter : IValueConverter
    {
        private const string IMAGE_FOLDER_PATH = "..\\..\\Resources\\Images\\";
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            if (value != null)
            {
                Resource resource = (Resource)value;
                if(resource is Gold)
                {
                    return IMAGE_FOLDER_PATH + "container_gold.png";
                }
                else if (resource is Coal)
                {
                    return IMAGE_FOLDER_PATH + "container_coal.png";
                }
                if (resource is PointContainer)
                {
                    return IMAGE_FOLDER_PATH + "container_points.png";
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
