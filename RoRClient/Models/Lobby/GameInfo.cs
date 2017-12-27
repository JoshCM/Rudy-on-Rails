using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Models.Base;
using RoRClient.Models.Game;

namespace RoRClient.Models.Lobby
{
	class GameInfo :ModelBase
	{
		private Player player;
		public GameInfo(Player player)
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
