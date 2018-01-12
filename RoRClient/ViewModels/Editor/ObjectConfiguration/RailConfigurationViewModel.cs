using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Commands;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModels.Editor.ObjectConfiguration
{
    class RailConfigurationViewModel : ViewModelBase
    {
        private MapEditorViewModel mapViewModel;
        private Rail rail;

        private int selectedAutoSwitchIntervalInSeconds;
        private int selectedPenalty;
        private int selectedSwitchCost;

        public RailConfigurationViewModel(MapEditorViewModel mapViewModel)
        {
            this.mapViewModel = mapViewModel;
            mapViewModel.PropertyChanged += OnSelectedEditorCanvasViewModelChanged;
        }

        public Rail Rail
        {
            get
            {
                return rail;
            }
            set
            {
                rail = value;
                OnPropertyChanged("Rail");
            }
        }

        public int SelectedAutoSwitchIntervalInSeconds
        {
            get
            {
                return selectedAutoSwitchIntervalInSeconds;
            }
            set
            {
                if (selectedAutoSwitchIntervalInSeconds != value)
                {
                    selectedAutoSwitchIntervalInSeconds = value;
                    OnPropertyChanged("SelectedAutoSwitchIntervalInSeconds");
                }
            }
        }

        public int SelectedPenalty
        {
            get
            {
                return selectedPenalty;
            }
            set
            {
                if (selectedPenalty != value)
                {
                    selectedPenalty = value;
                    OnPropertyChanged("SelectedPenalty");
                }
            }
        }

        public int SelectedSwitchCost
        {
            get
            {
                return selectedSwitchCost;
            }
            set
            {
                if (selectedSwitchCost != value)
                {
                    selectedSwitchCost = value;
                    OnPropertyChanged("SelectedSwitchCost");
                }
            }
        }

        private void OnSelectedEditorCanvasViewModelChanged(object sender, PropertyChangedEventArgs e)
        {
            if (e.PropertyName == "SelectedEditorCanvasViewModel")
            {
                CanvasEditorViewModel canvasEditorViewModel = mapViewModel.SelectedEditorCanvasViewModel;
                if (canvasEditorViewModel != null)
                {
                    if (canvasEditorViewModel.GetType() == typeof(RailEditorViewModel))
                    {
                        RailEditorViewModel railEditorViewModel = (RailEditorViewModel)canvasEditorViewModel;

                        // wenn die TrainstationId leer ist
                        if (railEditorViewModel.Rail.TrainstationId.Equals(Guid.Empty))
                        {
                            Rail = railEditorViewModel.Rail;
                            SelectedAutoSwitchIntervalInSeconds = rail.Signals.AutoSwitchIntervalInSeconds;
                            SelectedPenalty = rail.Signals.Penalty;
                            SelectedSwitchCost = rail.Signals.SwitchCost;
                        }
                    }
                }
            }
        }

        private void SendSignalsConfigCommand()
        {
            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", rail.Square.PosX);
            messageInformation.PutValue("yPos", rail.Square.PosY);
            messageInformation.PutValue("autoSwitchIntervalInSeconds", selectedAutoSwitchIntervalInSeconds);
            messageInformation.PutValue("penalty", selectedPenalty);
            messageInformation.PutValue("switchCost", SelectedSwitchCost);
            EditorSession.GetInstance().QueueSender.SendMessage("ChangeSignalsConfiguration", messageInformation);
        }

        private ICommand saveSignalsConfigCommand;
        public ICommand SaveSignalsConfigCommand
        {
            get
            {
                if (saveSignalsConfigCommand == null)
                {
                    saveSignalsConfigCommand = new ActionCommand(param => SendSignalsConfigCommand());
                }

                return saveSignalsConfigCommand;
            }
        }
    }
}
