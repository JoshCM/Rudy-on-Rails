using RoRClient.Models.Base;
using System;
using System.Collections.Generic;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Klasse für ein Schienenstück mit "Eingang" und "Ausgang"
    /// </summary>
    public class RailSection : ModelBase
    {
        private Compass node1;
        private Compass node2;
        private RailSectionStatus status;

        public Compass Node1
        {
            get
            {
                return node1;
            }
        }
        public Compass Node2
        {
            get
            {
                return node2;
            }
        }

        public RailSectionStatus Status
        {
            get {
                return status;
            }
        }

        public RailSection (Guid id, Compass node1, Compass node2, RailSectionStatus status) : base()
        {
            this.id = id;
            this.node1 = node1;
            this.node2 = node2;
            this.status = status;
            
        }

        public RailSection(Guid id, Compass node1, Compass node2) : base()
        {
            this.id = id;
            this.node1 = node1;
            this.node2 = node2;

        }

        /// <summary>
        /// Gibt alle Nodes als Liste zurück
        /// </summary>
        /// <returns></returns>
        public List<Compass> GetNodesAsList()
        {
            List<Compass> positionList = new List<Compass>();
            positionList.Add(node1);
            positionList.Add(node2);
            return positionList;
        }
    }
}
