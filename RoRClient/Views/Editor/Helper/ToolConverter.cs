using RoRClient.Models.Game;
using System;

namespace RoRClient.Views.Editor.Helper
{
    /// <summary>
    /// Stellt Methoden zur Konvertierung von Tools (z.B. verschiedene Rails) bereit
    /// </summary>
    public class ToolConverter
    {
        /// <summary>
        /// Erzeugt aus einem definierten Namen eine Railsection mit zwei RailSectionPositions
        /// </summary>
        /// <param name="toolName">Name des selectedTool</param>
        /// <returns>Railsection</returns>
        public static RailSection ConvertToRailSection(String toolName)
        {
            Compass node1;
            Compass node2;

            switch (toolName)
            {
                case "rail_ew":
                    node1 = Compass.EAST;
                    node2 = Compass.WEST;
                    break;
                case "rail_ns":
                    node1 = Compass.NORTH;
                    node2 = Compass.SOUTH;
                    break;
                case "railcurve_ne":
                    node1 = Compass.NORTH;
                    node2 = Compass.EAST;
                    break;
                case "railcurve_nw":
                    node1 = Compass.NORTH;
                    node2 = Compass.WEST;
                    break;
                case "railcurve_se":
                    node1 = Compass.SOUTH;
                    node2 = Compass.EAST;
                    break;
                case "railcurve_sw":
                    node1 = Compass.SOUTH;
                    node2 = Compass.WEST;
                    break;
                default:
                    node1 = new Compass();
                    node2 = new Compass();
                    break;
            }
            return new RailSection(Guid.NewGuid(), node1, node2);
        }
    }
}
