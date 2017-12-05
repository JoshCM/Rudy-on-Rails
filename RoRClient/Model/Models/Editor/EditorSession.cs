using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models.Editor
{
    class EditorSession : ModelBase
    {
        private string name;
        private Map map;
        private ObservableCollection<Player> players = new ObservableCollection<Player>();

        public EditorSession(string name)
        {
            this.name = name;
            this.map = new Map();
        }

        public string Name
        {
            get
            {
                return name;
            }
        }

        public Map Map
        {
            get
            {
                return map;
            }
        }

        public ObservableCollection<Player> Players
        {
            get
            {
                return players;
            }
        }

        public void AddPlayer(Player player)
        {
            players.Add(player);
        }

        public void RemovePlayer(Player player)
        {
            players.Remove(player);
        }
    }
}
