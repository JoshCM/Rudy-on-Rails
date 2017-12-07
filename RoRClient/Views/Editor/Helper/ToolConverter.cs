using RoRClient.Models.Game;
using System;

namespace RoRClient.Views.Editor.Helper
{
    public class ToolConverter
    {
        /// <summary>
        /// Erzeugt aus einem definierten Namen eine Railsection mit zwei RailSectionPositions
        /// </summary>
        /// <param name="toolName">Name des selectedTool</param>
        /// <returns></returns>
        public static RailSection convertToRailSection(String toolName)
        {
            RailSectionPosition node1;
            RailSectionPosition node2;

            switch (toolName)
            {
                case "rail_ew":
                    node1 = RailSectionPosition.EAST;
                    node2 = RailSectionPosition.WEST;
                    break;
                case "rail_ns":
                    node1 = RailSectionPosition.NORTH;
                    node2 = RailSectionPosition.SOUTH;
                    break;
                case "railcurve_ne":
                    node1 = RailSectionPosition.NORTH;
                    node2 = RailSectionPosition.EAST;
                    break;
                case "railcurve_nw":
                    node1 = RailSectionPosition.NORTH;
                    node2 = RailSectionPosition.WEST;
                    break;
                case "railcurve_se":
                    node1 = RailSectionPosition.SOUTH;
                    node2 = RailSectionPosition.EAST;
                    break;
                case "railcurve_sw":
                    node1 = RailSectionPosition.SOUTH;
                    node2 = RailSectionPosition.WEST;
                    break;
                default:
                    node1 = new RailSectionPosition();
                    node2 = new RailSectionPosition();
                    break;
            }
            return new RailSection(node1, node2);
        }
    }
}
