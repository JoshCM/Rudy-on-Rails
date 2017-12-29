using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Models.Base;
using RoRClient.Models.Game;

namespace RoRClient.Models.Lobby
{
	class EditorInfo : ModelBase
	{
		private Player player;
		public EditorInfo(Player player)
		{
			this.player = player;
		}

		public Player Player
		{
			get { return player; }
			set { player = value; }
		}
	}
}
