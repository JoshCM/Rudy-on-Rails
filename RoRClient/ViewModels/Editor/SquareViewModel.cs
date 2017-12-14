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
            if (toolbarViewModel.SelectedTool != null)
            {
                if (toolbarViewModel.SelectedTool.Name.Contains("rail"))
                {
                    SendCreateRailCommand();
                }
                else if (toolbarViewModel.SelectedTool.Name.Contains("trainstation"))
                {
                    SendCreateTrainstationCommand();
                }
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
            messageInformation.PutValue("railSectionPositionNode1", railSection.Node1.ToString());
            messageInformation.PutValue("railSectionPositionNode2", railSection.Node2.ToString());

            editorSession.QueueSender.SendMessage("CreateRail", messageInformation);
        }

        /// <summary>
        /// Sendet eine Anfrage an den Server der eine Trainstation setzen soll
        /// </summary>
        private void SendCreateTrainstationCommand()
        {
            int xPos = square.PosX;
            int yPos = square.PosY;
            EditorSession editorSession = EditorSession.GetInstance();

            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", xPos);
            messageInformation.PutValue("yPos", yPos);

            editorSession.QueueSender.SendMessage("CreateTrainstation", messageInformation);
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
    }
}
