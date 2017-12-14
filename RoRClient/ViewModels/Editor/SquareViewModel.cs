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
            // Quick-Navigation von einem möglich vorherigen angeklicken CanvasViewModel ausblenden
            MapViewModel.IsQuickNavigationVisible = false;

            int xPos = square.PosX;
            int yPos = square.PosY;
            EditorSession editorSession = EditorSession.GetInstance();
            RailSection railSection = ToolConverter.ConvertToRailSection(toolbarViewModel.SelectedTool.Name);

            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", xPos);
            messageInformation.PutValue("yPos", yPos);
            messageInformation.PutValue("railSectionPositionNode1", railSection.Node1.ToString());
            messageInformation.PutValue("railSectionPositionNode2", railSection.Node2.ToString());

            // TODO: Message sollte mithilfe CommandManager oder so geschickt werden
            editorSession.QueueSender.SendMessage("CreateRail", messageInformation);
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
