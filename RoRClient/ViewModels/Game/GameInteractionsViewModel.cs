using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Commands;
using RoRClient.Views.Popup;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Windows.Input;
using static RoRClient.Models.Game.Script;

namespace RoRClient.ViewModels.Game
{
    public class GameInteractionsViewModel : ViewModelBase
    {
        private Script selectedGhostLocoScript;
        private int currentNumberOfOwnGhostLocoScript = 1;

        public GameInteractionsViewModel()
        {
        }

        public Scripts Scripts
        {
            get
            {
                return GameSession.GetInstance().Scripts;
            }
        }

        public Script SelectedGhostLocoScript
        {
            get
            {
                return selectedGhostLocoScript;
            }
            set
            {
                if(selectedGhostLocoScript != value)
                {
                    selectedGhostLocoScript = value;
                    OnPropertyChanged("SelectedGhostLocoScript");
                    ChangeCurrentScriptOfGhostLocos();
                }
            }
        }

        /// <summary>
        /// Schickt eine Nachricht an den Server, durch die das aktuelle Script der GhostLocos des eigenen Spielers geändert und aktiviert wird
        /// </summary>
        private void ChangeCurrentScriptOfGhostLocos()
        {
            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("playerId", GameSession.GetInstance().OwnPlayer.Id);
            messageInformation.PutValue("scriptId", SelectedGhostLocoScript.Id);
            GameSession.GetInstance().QueueSender.SendMessage("ChangeCurrentScriptOfGhostLocos", messageInformation);
        }

        private ICommand addGhostLocoScriptFromPlayerCommand;
        public ICommand AddGhostLocoScriptFromPlayerCommand
        {
            get
            {
                if (addGhostLocoScriptFromPlayerCommand == null)
                {
                    addGhostLocoScriptFromPlayerCommand = new ActionCommand(param => AddGhostLocoScriptFromPlayer());
                }
                return addGhostLocoScriptFromPlayerCommand;
            }
        }

        /// <summary>
        /// Fügt ein neues GhostLocoScript zur Auswahl hinzu
        /// </summary>
        private void AddGhostLocoScriptFromPlayer()
        {
            string description = "Eigenes Script " + currentNumberOfOwnGhostLocoScript;
            string filename = CustomFileDialogs.AskUserToSelectPythonScript();

            if (filename != null)
            {
                string scriptContent = File.ReadAllText(filename);
                currentNumberOfOwnGhostLocoScript += 1;

                MessageInformation messageInformation = new MessageInformation();
                messageInformation.PutValue("playerId", GameSession.GetInstance().OwnPlayer.Id);
                messageInformation.PutValue("description", description);
                messageInformation.PutValue("scriptContent", scriptContent);
                messageInformation.PutValue("scriptType", ScriptTypes.GHOSTLOCO.ToString());
                GameSession.GetInstance().QueueSender.SendMessage("AddScriptFromPlayer", messageInformation);
            }
        }
    }
}
