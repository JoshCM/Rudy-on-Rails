using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;

namespace RoRClient.Commands.Editor.Create
{
	/// <summary>
	/// CreateRailCommand für Game
	/// </summary>
	public class CreatePlayerCommand : CommandBase
	{
		private Guid playerId;
		private string playerName;
		private bool isHost;

		public CreatePlayerCommand(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
		{
			playerId = messageInformation.GetValueAsGuid("playerId");
			playerName = messageInformation.GetValueAsString("playerName");
			isHost = messageInformation.GetValueAsBool("isHost");
		}

		public override void Execute()
		{
			EditorPlayer player = new EditorPlayer(playerId, playerName, isHost);
			EditorSession.GetInstance().AddPlayer(player);
		}
	}
}
