using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Editor
{
    class CraneEditorViewModel : CanvasEditorViewModel 
    {
        public CraneEditorViewModel(Crane crane) : base(crane.Id)
        {
            this.crane = crane;
            this.SquarePosX = crane.Square.PosX;
            this.SquarePosY = crane.Square.PosY;
        }
        private Crane crane;

        public Crane Crane
        {
            get { return crane;}
        }

        public override void RotateLeft()
        {
            throw new NotImplementedException();
        }

        public override void RotateRight()
        {
            throw new NotImplementedException();
        }

        public override void Move()
        {
            throw new NotImplementedException();
        }

        public override void Delete()
        {
            throw new NotImplementedException();
        }
    }
}
