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

namespace RoRClient.ViewModels.Editor
{
    public class SquareViewModel : CanvasViewModel
    {
        private Square square;
        private String toolName;

        public SquareViewModel(Square square) : base(square.Id)
        {
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
        public String ToolName
        {
            get
            {
                return toolName;
            }
            set
            {
                toolName = value;
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

        private void SendCreateRailCommand()
        {
            int xPos = square.PosX;
            int yPos = square.PosY;
            EditorSession editorSession = EditorSession.GetInstance();
            RailSection railSection = ToolConverter.convertToRailSection(ToolName);

            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", xPos);
            messageInformation.PutValue("yPos", yPos);
            messageInformation.PutValue("railSectionPositionNode1", railSection.Node1.ToString());
            messageInformation.PutValue("railSectionPositionNode2", railSection.Node2.ToString());

            // TODO: Message sollte mithilfe CommandManager oder so geschickt werden
            editorSession.QueueSender.SendMessage("CreateRail", messageInformation);
        }
    }
}
