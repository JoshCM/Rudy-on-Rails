using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Commands;
using RoRClient.Views.Popup;
using System;
using System.Collections.Generic;
using System.ComponentModel;
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
        private Script selectedSensorScript;
        private bool canPlaceSensor = false;
        private bool canConfigureSensor = false;
        private MapGameViewModel mapGameViewModel;
        private int currentNumberOfOwnGhostLocoScript = 1;

        public bool CanConfigureSensor
        {
            get
            {
                return canConfigureSensor;
            }
            set
            {
                canConfigureSensor = value;
                OnPropertyChanged("CanConfigureSensor");
            }
        }

        public bool CanPlaceSensor
        {
            get
            {
                return canPlaceSensor;
            }
            set
            {
                canPlaceSensor = value;
                OnPropertyChanged("CanPlaceSensor");
            }
        }

        public GameInteractionsViewModel(MapGameViewModel mapGameViewModel)
        {
            this.mapGameViewModel = mapGameViewModel;
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

        /// <summary>
        /// Command zum Platzieren eines Sensors
        /// </summary>
        private ICommand placeSensorCommand;
        public ICommand PlaceSensorCommand
        {
            get
            {
                if(placeSensorCommand == null)
                {
                    placeSensorCommand = new ActionCommand(param => PlaceSensor());
                }
                return placeSensorCommand;
            }
        }

        /// <summary>
        /// Schickt eine Nachricht mit dem ausgewählten Canvas an den Server, um zu prüfen, ob dort ein Sensor platziert werden kann
        /// </summary>
        private void PlaceSensor()
        {
            MessageInformation message = new MessageInformation();
            Guid selectedModelId = mapGameViewModel.SelectedGameCanvasViewModel.Id;
            message.PutValue("selectedModelId", selectedModelId);
            GameSession.GetInstance().QueueSender.SendMessage("PlaceSensor", message);

            // Button zum Platzieren von Sensoren deaktivieren, damit nicht mehrere Sensoren übereinander liegen
            CanPlaceSensor = false;
        }

        /// <summary>
        /// Aktuell ausgewähltes Script für Sensor
        /// </summary>
        public Script SelectedSensorScript
        {
            get
            {
                return selectedSensorScript;
            }
            set
            {
                if (selectedSensorScript != value)
                {
                    selectedSensorScript = value;
                    OnPropertyChanged("SelectedSensorScript");

                    if (selectedSensorScript != null) {
                        ChangeCurrentScriptOfSensor();
                    }
                }
            }
        }

        /// <summary>
        /// Ändert das Script für den Sensor
        /// </summary>
        private void ChangeCurrentScriptOfSensor()
        {
            MessageInformation message = new MessageInformation();
            Guid selectedModelId = mapGameViewModel.SelectedGameCanvasViewModel.Id;
            message.PutValue("selectedModelId", selectedModelId);
            message.PutValue("scriptId", SelectedSensorScript.Id);
            GameSession.GetInstance().QueueSender.SendMessage("ChangeCurrentScriptOfSensor", message);
        }

    }
}
