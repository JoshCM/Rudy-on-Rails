using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;

namespace RoRClient.Commands.Base
{
	class ChangeMapSelectionCommandBase : CommandBase
	{
		private string mapName;
        private int availablePlayerSlots;
		public ChangeMapSelectionCommandBase(RoRSession session, MessageInformation message) : base(session, message)
		{
			mapName = message.GetValueAsString("mapName");
            //availablePlayerSlots = message.GetValueAsInt("availablePlayerSlots");
        }
		public override void Execute()
		{
			session.MapName = mapName;
        }
	}
}
