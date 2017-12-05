﻿using Apache.NMS;
using RoRClient.Model.DataTransferObject;
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
   class StartViewModel:ViewModelBase
    {
        public UIState uiState;
        private ClientModel clientModel;

        public StartViewModel(UIState uiState)
        {
            clientModel = new ClientModel();
            this.uiState = uiState;
        }

        private ICommand start2EditorCmd;
        public ICommand StartToEditorCommand
        {
            get
            {
                if(start2EditorCmd == null)
                {
                    start2EditorCmd = new ActionCommand(e => { uiState.State = "editor"; });
                }
                return start2EditorCmd;
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

        private void SendCreateEditorSessionCommand()
        {
            MessageInformation messageInformation = new MessageInformation(new Dictionary<string, object>() { { "Playername", "Heinz" }, { "Editorname", "Editor1" } });
            IMessage message = MessageBuilder.build("CreateEditorSession", messageInformation);
            clientModel.getFromClientRequestSender().SendMessage(message);
        }

        public ClientModel ClientModel
        {
            get
            {
                return clientModel;
            }
        }
    }
}
