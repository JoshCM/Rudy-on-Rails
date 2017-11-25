using Apache.NMS;
using RoRClient.Model.Connections;
using RoRClient.Model.Helper;
using RoRClient.Model.Models;
using RoRClient.ViewModel.Helper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModel
{
	class PlayerViewModel
	{
		private ICommand createPlayerCommand;
		public ICommand CreatePlayerCommand
		{
			get
			{
				if (createPlayerCommand == null)
				{
					createPlayerCommand = new ActionCommand(param => CreateNewPlayerCommand());
				}
				return createPlayerCommand;
			}
		}

		private void CreateNewPlayerCommand()
		{
			IMessage message = MessageBuilder.build(MessageType.CREATE, RequestType.PLAYER);
			ClientModel.getInstance().getFromClientRequestSender().SendMessage(message);
		}
	}
}
