using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.ViewModels.Commands;
using RoRClient.ViewModels.Helper;
using System.ComponentModel;
using System.Windows.Input;

namespace RoRClient.ViewModels.Lobby
{
    class StartViewModel : ViewModelBase
    {
        public UIState uiState;
        private LobbyModel lobbyModel;

        public StartViewModel(UIState uiState)
        {
            lobbyModel = new LobbyModel();
            this.uiState = uiState;

            lobbyModel.PropertyChanged += OnClientModelChanged;
        }

        private ICommand start2GameCmd;
        public ICommand StartToGameCommand
        {
            get
            {
                if (start2GameCmd == null)
                {
                    start2GameCmd = new ActionCommand(e => { uiState.State = "game"; });
                }
                return start2GameCmd;
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
            lobbyModel.StartConnection();
            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("playerName", "Heinz");
            messageInformation.PutValue("editorName", "Editor1");
            lobbyModel.getFromClientRequestSender().SendMessage("CreateEditorSession", messageInformation);
        }

        public LobbyModel LobbyModel
        {
            get
            {
                return lobbyModel;
            }
        }

        private void OnClientModelChanged(object sender, PropertyChangedEventArgs e)
        {
            if(e.PropertyName == "Connected")
            {
                if (lobbyModel.Connected)
                {
                    uiState.State = "editor";
                }
            }
        }
    }
}
