using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    class RailSection : ModelBase
    {
        //Klasse für ein Schienenstück mit "Eingang" und "Ausgang"

        //Geraden
        public static readonly RailSection STRAIGHT_VERTICAL = new RailSection (RailSectionPosition.NORTH, RailSectionPosition.SOUTH);
        public static readonly RailSection STRAIGHT_HORIZONTAL = new RailSection (RailSectionPosition.EAST, RailSectionPosition.WEST);
        //Kurven
        public static readonly RailSection CURVE_NE = new RailSection (RailSectionPosition.NORTH, RailSectionPosition.EAST);
        public static readonly RailSection CURVE_ES = new RailSection (RailSectionPosition.EAST, RailSectionPosition.SOUTH);
        public static readonly RailSection CURVE_SW = new RailSection (RailSectionPosition.SOUTH, RailSectionPosition.WEST);
        public static readonly RailSection CURVE_WN = new RailSection (RailSectionPosition.WEST, RailSectionPosition.NORTH);

        RailSectionPosition node1;
        RailSectionPosition node2;

        public RailSection (RailSectionPosition node1, RailSectionPosition node2) : base()
        {
            this.node1 = node1;
            this.node2 = node2;
        }
    }
}
