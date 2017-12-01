using Apache.NMS;
using RoRClient.Model.Connections;
using RoRClient.Model.DataTransferObject;
using RoRClient.Model.Models;
using RoRClient.ViewModel.Helper;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModel
{
	class PlayerViewModel : INotifyPropertyChanged
	{
		public event PropertyChangedEventHandler PropertyChanged;
		public virtual void OnPropertyChanged(string propertyName)
		{
			if (PropertyChanged != null)
			{
				PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
			}
		}

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

        //Beispiel wie eine Message versendet werden kann
		private void CreateNewPlayerCommand()
		{
            MessageInformation messageInformation = new MessageInformation(RequestType.PLAYER, new Dictionary<string, string>() { { "testAttribut1", "1" }, { "testAttribut2", "2" } });
            IMessage message = MessageBuilder.build("CREATE_PLAYER", messageInformation) ;
			ClientModel.getInstance().getFromClientRequestSender().SendMessage(message);
		}

		private String playerLabel;
		public String PlayerLabel
		{
			get { return playerLabel; }
			set
			{
				if (playerLabel != value)
				{
					playerLabel = value;
					OnPropertyChanged("PlayerLabel");
				}
			}
		}
	}
}
