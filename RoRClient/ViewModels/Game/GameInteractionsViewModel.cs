using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Commands;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Windows.Input;

namespace RoRClient.ViewModels.Game
{
    public class GameInteractionsViewModel : ViewModelBase
    {
        private Script selectedGhostLocoScript;
        private bool canActivateSensor = false;
        private CanvasGameViewModel canvasGameViewModel;

        public bool CanActivateSensor
        {
            get
            {
                return canActivateSensor;
            }
            set
            {
                canActivateSensor = value;
                OnPropertyChanged("CanActivateSensor");
            }
        }

        public GameInteractionsViewModel()
        {

        }

        public void OnSelectedGameObjectChanged(object sender, PropertyChangedEventArgs e)
        {
            if (e.PropertyName == "SelectedGameCanvasViewModel")
            {
                canvasGameViewModel = (CanvasGameViewModel)sender;
            }
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
            MessageInformation messageInformation = new MessageInformation();
            string scriptName = "Eigenes Script";

            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.ShowDialog();

            if(openFileDialog.FileName != "")
            {
                string scriptContent = File.ReadAllText(openFileDialog.FileName);

                messageInformation.PutValue("playerId", GameSession.GetInstance().OwnPlayer.Id);
                messageInformation.PutValue("scriptName", scriptName);
                messageInformation.PutValue("scriptContent", scriptContent);
                GameSession.GetInstance().QueueSender.SendMessage("AddGhostLocoScriptFromPlayer", messageInformation);
            }
        }

        private ICommand activateSensorCommand;
        public ICommand ActivateSensorCommand
        {
            get
            {
                if(activateSensorCommand == null)
                {
                    activateSensorCommand = new ActionCommand(param => ActivateSensor());
                }
                return activateSensorCommand;
            }
        }

        private void ActivateSensor()
        {
            MessageInformation message = new MessageInformation();
            Rail rail = ((RailGameViewModel)canvasGameViewModel).Rail;
            message.PutValue("railId", rail.Id);
            GameSession.GetInstance().QueueSender.SendMessage("ActivateSensor", message);
        }

    }
}
