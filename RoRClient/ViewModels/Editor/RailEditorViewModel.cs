using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
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
    /// <summary>
    /// Hält die zugehörige Rail und die Position (SquarePosX, SquarePosY) des Rails
    /// </summary>
    public class RailEditorViewModel : CanvasEditorViewModel
    {
        private Rail rail;
        public RailEditorViewModel(Rail rail) : base(rail.Id)
        {
            this.rail = rail;
            this.SquarePosX = rail.Square.PosX;
            this.SquarePosY = rail.Square.PosY;
        }

        public Rail Rail
        {
            get
            {
                return rail;
            }
        }

        public override void Delete()
        {
            int xPos = this.SquarePosX;
            int yPos = this.SquarePosY;
            RoRSession editorSession = EditorSession.GetInstance();

            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", xPos);
            messageInformation.PutValue("yPos", yPos);

            // TODO: Message sollte mithilfe CommandManager oder so geschickt werden
            editorSession.QueueSender.SendMessage("DeletePlaceable", messageInformation);
        }

        public override void RotateLeft()
        {
            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", rail.Square.PosX);
            messageInformation.PutValue("yPos", rail.Square.PosY);
            messageInformation.PutValue("right", false);
            EditorSession.GetInstance().QueueSender.SendMessage("RotateRail", messageInformation);
        }

        public override void RotateRight()
        {
            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", rail.Square.PosX);
            messageInformation.PutValue("yPos", rail.Square.PosY);
            messageInformation.PutValue("right", true);
            EditorSession.GetInstance().QueueSender.SendMessage("RotateRail", messageInformation);
        }

        //TODO: Baustelle
        public override void Move()
        {
            this.Delete();
            RoRSession editorSession = EditorSession.GetInstance();

            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", this.SquarePosX);
            messageInformation.PutValue("yPos", this.SquarePosY);
            messageInformation.PutValue("raildId", MapViewModel.SelectedEditorCanvasViewModel.Id);
            EditorSession.GetInstance().QueueSender.SendMessage("CreateRail", messageInformation);
        }
    }
}
