using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;
using RoRClient.ViewModels.Commands;
using RoRClient.Views.Editor.Helper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using Newtonsoft.Json.Linq;
using RoRClient.ViewModels.Helper;

namespace RoRClient.ViewModels.Editor
{
    public class SquareEditorViewModel : CanvasEditorViewModel
    {
        private Square square;
        private ToolbarViewModel toolbarViewModel;

        public SquareEditorViewModel(Square square, ToolbarViewModel toolbarViewModel) : base(square.Id)
        {
            this.toolbarViewModel = toolbarViewModel;
            this.square = square;
            this.SquarePosX = square.PosX;
            this.SquarePosY = square.PosY;
        }

        public Square Square
        {
            get
            {
                return square;
            }
            set
            {
                square = value;
            }
        }

        private ICommand createPlaceableOnSquareCommand;
        public ICommand CreatePlaceableOnSquareCommand
        {
            get
            {
                if (createPlaceableOnSquareCommand == null)
                {
                    createPlaceableOnSquareCommand = new ActionCommand(param => SendCreatePlaceableOnSquareCommand());
                }
                return createPlaceableOnSquareCommand;
            }
        }

        /// <summary>
        /// Wählt die richtige SendMethode für das ausgewählte Tool
        /// </summary>
        private void SendCreatePlaceableOnSquareCommand()
        {
            if (square.PlaceableOnSquare == null) {
                if(MapViewModel.SelectedEditorCanvasViewModel != null)
                {
                    Move();
                    MapViewModel.SelectedEditorCanvasViewModel = null;
                }
                else if (toolbarViewModel.SelectedTool != null)
                {
                    string selectedToolName = toolbarViewModel.SelectedTool.Name;

                    if (selectedToolName == "rail_crossing")
                    {
                        SendCreateCrossingCommand();
                    }
                    else if (selectedToolName == "rail_crossing_with_signals")
                    {
                        SendCreateCrossingWithSignalsCommand();
                    }
                    else if (selectedToolName.Contains("rail"))
                    {
                        SendCreateRailCommand();
                    }
                    else if (selectedToolName.Contains("publictrainstation"))
                    {
                        SendCreatePublictrainstationCommand();
                    }
                    else if (selectedToolName.Contains("trainstation"))
                    {
                        SendCreatePlayertrainstationCommand();
                    }
                    else if (selectedToolName.Contains("switch"))
                    {
                        SendCreateSwitchCommand();
                    }
                }
            }   
        }

        /// <summary>
        /// Sendet einen Anfrage-Command an den Server, der dort eine Rail erstellen soll
        /// </summary>
        private void SendCreateRailCommand()
        {
            // Quick-Navigation von einem möglich vorherigen angeklicken EditorCanvasViewModel ausblenden
            MapViewModel.IsQuickNavigationVisible = false;

            int xPos = square.PosX;
            int yPos = square.PosY;
            EditorSession editorSession = EditorSession.GetInstance();
            RailSection railSection = ToolConverter.ConvertToRailSection(toolbarViewModel.SelectedTool.Name);

            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", xPos);
            messageInformation.PutValue("yPos", yPos);

            List<JObject> railSections = new List<JObject>();
            JObject railSectionObject = new JObject();
            railSectionObject.Add("node1", railSection.Node1.ToString());
            railSectionObject.Add("node2", railSection.Node2.ToString());
            railSections.Add(railSectionObject);

            messageInformation.PutValue("railSectionList", railSections);

            editorSession.QueueSender.SendMessage("CreateRail", messageInformation);
        }

        /// <summary>
        /// Sendet einen Anfrage-Command an den Server, der dort eine Crossing erstellen soll
        /// </summary>
        private void SendCreateCrossingCommand()
        {
            // Quick-Navigation von einem möglich vorherigen angeklicken EditorCanvasViewModel ausblenden
            MapViewModel.IsQuickNavigationVisible = false;

            int xPos = square.PosX;
            int yPos = square.PosY;
            EditorSession editorSession = EditorSession.GetInstance();

            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", xPos);
            messageInformation.PutValue("yPos", yPos);

            editorSession.QueueSender.SendMessage("CreateCrossing", messageInformation);
        }

        private void SendCreateSwitchCommand()
        {
            // Quick-Navigation von einem möglich vorherigen angeklicken EditorCanvasViewModel ausblenden
            MapViewModel.IsQuickNavigationVisible = false;

            int xPos = square.PosX;
            int yPos = square.PosY;
            EditorSession editorSession = EditorSession.GetInstance();
            List <RailSection> railSectionList = ToolConverter.ConvertSwitchToRailSections(toolbarViewModel.SelectedTool.Name);

            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", xPos);
            messageInformation.PutValue("yPos", yPos);

            List<JObject> railSections = new List<JObject>();
            JObject railSectionObject = new JObject();
            railSectionObject.Add("node1", railSectionList[0].Node1.ToString());
            railSectionObject.Add("node2", railSectionList[0].Node2.ToString());
            railSectionObject.Add("node3", railSectionList[1].Node1.ToString());
            railSectionObject.Add("node4", railSectionList[1].Node2.ToString());

            railSections.Add(railSectionObject);

            messageInformation.PutValue("railSections", railSections);

            editorSession.QueueSender.SendMessage("CreateSwitch", messageInformation);
        }


        /// <summary>
        /// Sendet einen Anfrage-Command an den Server, der dort eine Crossing mit Signalen drauf erstellen soll
        /// </summary>
        private void SendCreateCrossingWithSignalsCommand()
        {
            // Quick-Navigation von einem möglich vorherigen angeklicken EditorCanvasViewModel ausblenden
            MapViewModel.IsQuickNavigationVisible = false;

            int xPos = square.PosX;
            int yPos = square.PosY;
            EditorSession editorSession = EditorSession.GetInstance();

            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", xPos);
            messageInformation.PutValue("yPos", yPos);

            editorSession.QueueSender.SendMessage("CreateCrossingWithSignals", messageInformation);
        }

        /// <summary>
        /// Sendet eine Anfrage an den Server der eine Playertrainstation setzen soll
        /// </summary>
        private void SendCreatePlayertrainstationCommand()
        {
            int xPos = square.PosX;
            int yPos = square.PosY;
            EditorSession editorSession = EditorSession.GetInstance();

            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", xPos);
            messageInformation.PutValue("yPos", yPos);
            messageInformation.PutValue("alignment", Compass.EAST.ToString());

            editorSession.QueueSender.SendMessage("CreatePlayertrainstation", messageInformation);
        }

        /// <summary>
        /// Sendet eine Anfrage an den Server der eine Publictrainstation setzen soll
        /// </summary>
        private void SendCreatePublictrainstationCommand()
        {
            int xPos = square.PosX;
            int yPos = square.PosY;
            EditorSession editorSession = EditorSession.GetInstance();

            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", xPos);
            messageInformation.PutValue("yPos", yPos);
            messageInformation.PutValue("alignment", Compass.EAST.ToString());

            editorSession.QueueSender.SendMessage("CreatePublictrainstation", messageInformation);
        }

        public override void RotateLeft()
        {
            throw new NotImplementedException();
        }

        public override void RotateRight()
        {
            throw new NotImplementedException();
        }

        public override void Delete()
        {
            throw new NotImplementedException();
        }
        public void ChangeSwitch()
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Move-Methode für alle PlaceableOnSquare
        /// </summary>
        public override void Move()
        {
            RoRSession editorSession = EditorSession.GetInstance();

            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("newXPos", this.SquarePosX);
            messageInformation.PutValue("newYPos", this.SquarePosY);
            messageInformation.PutValue("id", MapViewModel.SelectedEditorCanvasViewModel.Id);
			String viewModelType = TypeHelper.getTypeNameByViewModel(MapViewModel.SelectedEditorCanvasViewModel.GetType().Name);
            EditorSession.GetInstance().QueueSender.SendMessage("Move" + viewModelType, messageInformation);
        }
    }
}
