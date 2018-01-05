using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;

namespace RoRClient.Commands.Base
{
	class ChangeMapNameCommandBase : CommandBase
	{
		private string mapName;
		public ChangeMapNameCommandBase(RoRSession session, MessageInformation message) : base(session, message)
		{
			mapName = message.GetValueAsString("mapName");
		}
		public override void Execute()
		{
			session.MapName = mapName;
		}
	}
}
