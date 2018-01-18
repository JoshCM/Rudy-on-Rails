using RoRClient.Models.Base;
using System;
using System.Collections.ObjectModel;

namespace RoRClient.Models.Game
{
    public class Player: ModelBase
    {
        public Player(Guid id, string name)
        {
            this.name = name;
            this.id = id;
        }

        public Player(Guid id, string name, bool isHost) : this(id, name)
        {
            this.isHost = isHost;
        }

        private string name;
        public String Name
        {
            get
            {
                return name;
            }
        }

        private bool isHost;
        public bool IsHost
        {
            get
            {
                return isHost;
            }
        }
    }
}
