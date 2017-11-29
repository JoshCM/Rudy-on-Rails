using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    public class RailSection : ModelBase
    {
        //Klasse für ein Schienenstück mit "Eingang" und "Ausgang"
        private RailSectionPosition node1;
        public RailSectionPosition Node1
        {
            get
            {
                return node1;
            }
        }
        private RailSectionPosition node2;
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

        public List<RailSectionPosition> GetNodesAsList()
        {
            List<RailSectionPosition> positionList = new List<RailSectionPosition>();
            positionList.Add(node1);
            positionList.Add(node2);
            return positionList;
        }
    }
}
