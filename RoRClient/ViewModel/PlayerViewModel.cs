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

		private ICommand createEditorSessionCommand;
		public ICommand CreateEditorSessionCommand
		{
			get
			{
				if (createEditorSessionCommand == null)
				{
					createEditorSessionCommand = new ActionCommand(param => SendCreateEditorSessionCommand());
				}
				return createEditorSessionCommand;
			}
		}

        //Beispiel wie eine Message versendet werden kann
		private void SendCreateEditorSessionCommand()
		{
            MessageInformation messageInformation = new MessageInformation(new Dictionary<string, object>() { { "Playername", "Heinz" }, {"Editorname", "Editor1"} });
            IMessage message = MessageBuilder.build("CreateEditorSession", messageInformation) ;
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
