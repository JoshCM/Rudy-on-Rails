using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Editor
{
    /// <summary>
    /// ViewModel für Mine
    /// </summary>
    public class MineEditorViewModel : CanvasEditorViewModel
    {

        private Mine mine;

        public Mine Mine
        {
            get
            {
                return mine;
            }
        }

        public MineEditorViewModel(Mine mine) : base(mine.Id)
        {
            this.mine = mine;
            this.SquarePosX = mine.Square.PosX;
            this.SquarePosY = mine.Square.PosY;
        }

        public override void Delete()
        {
            MessageInformation message = new MessageInformation();
            message.PutValue("xPos", mine.Square.PosX);
            message.PutValue("yPos", mine.Square.PosY);
            message.PutValue("mineId", mine.Id);
            message.PutValue("railId", mine.Square.PlaceableOnSquare.Id);
            EditorSession.GetInstance().QueueSender.SendMessage("DeleteMine", message);
        }

        public override void Move()
        {

        }


        public override void ChangeSwitch()
        {
            throw new NotImplementedException();
        }

        public override void RotateLeft()
        {
            MessageInformation message = new MessageInformation();
            message.PutValue("xPos", mine.Square.PosX);
            message.PutValue("yPos", mine.Square.PosY);
            message.PutValue("mineId", mine.Id);
            message.PutValue("railId", mine.Square.PlaceableOnSquare.Id);
            message.PutValue("right", false);
            EditorSession.GetInstance().QueueSender.SendMessage("RotateMine", message);

        }

        public override void RotateRight()
        {
            MessageInformation message = new MessageInformation();
            message.PutValue("xPos", mine.Square.PosX);
            message.PutValue("yPos", mine.Square.PosY);
            message.PutValue("mineId", mine.Id);
            message.PutValue("railId", mine.Square.PlaceableOnSquare.Id);
            message.PutValue("right", true);
            EditorSession.GetInstance().QueueSender.SendMessage("RotateMine", message);
        }
    }
}
