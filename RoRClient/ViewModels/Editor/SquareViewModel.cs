﻿using RoRClient.Communication.DataTransferObject;
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

namespace RoRClient.ViewModels.Editor
{
    public class SquareViewModel : CanvasViewModel
    {
        private Square square;
        private ToolbarViewModel toolbarViewModel;

        public SquareViewModel(Square square, ToolbarViewModel toolbarViewModel) : base(square.Id)
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

        private ICommand createRailCommand;
        public ICommand CreateRailCommand
        {
            get
            {
                if (createRailCommand == null)
                {
                    createRailCommand = new ActionCommand(param => SendCreateRailCommand());
                }
                return createRailCommand;
            }
        }
        
        /// <summary>
        /// Sendet einen Anfrage-Command an den Server, der dort eine Rail erstellen soll
        /// </summary>
        private void SendCreateRailCommand()
        {
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

            messageInformation.PutValue("railSections", railSections);

            // TODO: Message sollte mithilfe CommandManager oder so geschickt werden
            editorSession.QueueSender.SendMessage("CreateRail", messageInformation);
        }
    }
}
