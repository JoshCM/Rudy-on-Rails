using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;

namespace RoRClient.Commands.Game.Update
{
    class UpdatePlayerCommand : CommandBase
    {
	    private Guid playerId;
	    private string playerName;
	    private bool isHost;
	    private int coalCount;
	    private int goldCount;
	    private int pointCount;

		public UpdatePlayerCommand(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
	    {
		    playerId = messageInformation.GetValueAsGuid("playerId");
		    playerName = messageInformation.GetValueAsString("playerName");
		    isHost = messageInformation.GetValueAsBool("isHost");
		    coalCount = messageInformation.GetValueAsInt("coalCount");
		    goldCount = messageInformation.GetValueAsInt("goldCount");
		    pointCount = messageInformation.GetValueAsInt("pointCount");
		}

	    public override void Execute()
	    {
		    GamePlayer player = (GamePlayer)GameSession.GetInstance().GetPlayerById(playerId);
		    player.CoalCount = coalCount;
		    player.GoldCount = goldCount;
		    player.PointCount = pointCount;
	    }
    }
}
