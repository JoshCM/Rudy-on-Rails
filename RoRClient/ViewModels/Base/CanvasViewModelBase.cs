using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Base
{
    public abstract class CanvasViewModelBase : ViewModelBase
    {
        public CanvasViewModelBase(Guid modelId)
        {
            id = modelId;
        }

        private Guid id;
        public Guid Id
        {
            get
            {
                return id;
            }
        }

        private int squarePosX;
        public int SquarePosX
        {
            get
            {
                return squarePosX;
            }

            set
            {
                if (squarePosX != value)
                {
                    squarePosX = value;
                    OnPropertyChanged("SquarePosX");
                }
            }
        }

        private int squarePosY;
        public int SquarePosY
        {
            get
            {
                return squarePosY;
            }

            set
            {
                if (squarePosY != value)
                {
                    squarePosY = value;
                    OnPropertyChanged("SquarePosY");
                }
            }
        }
    }
}
