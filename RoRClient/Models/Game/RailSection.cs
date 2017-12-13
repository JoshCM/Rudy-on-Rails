using RoRClient.Models.Base;
using System.Collections.Generic;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Klasse für ein Schienenstück mit "Eingang" und "Ausgang"
    /// </summary>
    public class RailSection : ModelBase
    {
        private RailSectionPosition node1;
        private RailSectionPosition node2;

        public RailSectionPosition Node1
        {
            get
            {
                return node1;
            }
        }
        public RailSectionPosition Node2
        {
            get
            {
                return node2;
            }
        }

        public RailSection (RailSectionPosition node1, RailSectionPosition node2) : base()
        {
            this.node1 = node1;
            this.node2 = node2;
        }

        /// <summary>
        /// Gibt alle Nodes als Liste zurück
        /// </summary>
        /// <returns></returns>
        public List<RailSectionPosition> GetNodesAsList()
        {
            List<RailSectionPosition> positionList = new List<RailSectionPosition>();
            positionList.Add(node1);
            positionList.Add(node2);
            return positionList;
        }
    }
}
