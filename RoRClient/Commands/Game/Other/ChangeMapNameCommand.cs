using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;

namespace RoRClient.Commands.Game.Other
{
	class ChangeMapNameCommand : ChangeMapNameCommandBase
	{
		public ChangeMapNameCommand(RoRSession session, MessageInformation message) : base(session, message)
		{
		}
	}
}
