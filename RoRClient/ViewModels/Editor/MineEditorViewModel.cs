using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Editor
{
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

        public MineEditorViewModel(Guid modelId) : base(modelId)
        {
        }

        public override void Delete()
        {
            throw new NotImplementedException();
        }

        public override void Move()
        {
            throw new NotImplementedException();
        }

        public override void RotateLeft()
        {
            throw new NotImplementedException();
        }

        public override void RotateRight()
        {
            throw new NotImplementedException();
        }
    }
}
