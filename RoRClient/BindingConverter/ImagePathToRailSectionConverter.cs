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
	class ImagePathToRailSectionConverter : IValueConverter
	{
		// TODO: muss noch ausgearbeitet werden
		public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
		{
			string imagePath = (string)value;
			string[] splittedImagePath = imagePath.Split('\\');
			string imageFileName = splittedImagePath[splittedImagePath.Length - 1];

			switch (imageFileName) {
				case "rail_ns.png": return new RailSection(RailSectionPosition.NORTH, RailSectionPosition.SOUTH);
				case "rail_ew.png": return new RailSection(RailSectionPosition.EAST, RailSectionPosition.WEST);
				case "railcurve_ne.png": return new RailSection(RailSectionPosition.NORTH, RailSectionPosition.EAST);
				case "railcurve_nw.png": return new RailSection(RailSectionPosition.NORTH, RailSectionPosition.WEST);
				case "railcurve_se.png": return new RailSection(RailSectionPosition.SOUTH, RailSectionPosition.EAST);
				case "railcurve_sw.png": return new RailSection(RailSectionPosition.SOUTH, RailSectionPosition.WEST);
				default: return new RailSection(RailSectionPosition.NORTH, RailSectionPosition.SOUTH);
			}
		}

		public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
		{
			throw new NotImplementedException();
		}
	}
}
