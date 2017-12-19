using RoRClient.ViewModels.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Helper
{
	public class TypeHelper
	{
		/// <summary>
		/// Gibt den ersten Teil eines CamelCase Strings zurück
		/// </summary>
		/// <param name="canvasViewModel"></param>
		/// <returns></returns>
		public static String getTypeNameByViewModel(String camelCaseString)
		{
			return System.Text.RegularExpressions.Regex.Replace(camelCaseString, "([A-Z])", " $1", System.Text.RegularExpressions.RegexOptions.Compiled).Trim().Split()[0];
		}
	}
}
