using RoRClient.Models.Base;
using System;

namespace RoRClient.Models.Game
{
    public class Player: ModelBase
    {
        public Player(Guid id, string name)
        {
            this.name = name;
            this.id = id;
        }

        private string name;
        public String Name
        {
            get
            {
                return name;
            }
        }
        private Loco loco;
        public Loco Loco
        {
            get
            {
                return loco;
            }
            set
            {
                if (value != null)
                {
                    this.loco = value;
                }
            }
        }
    }
}
