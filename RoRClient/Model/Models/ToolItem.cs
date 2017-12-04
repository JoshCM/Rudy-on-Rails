using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Model.Models
{
    class ToolItem
    {
        private string id;
        private string imagePath;

        public ToolItem(string id, string imagePath)
        {
            this.id = id;
            this.imagePath = imagePath;
        }

        public string ID
        {
            get
            {
                return this.id;
            }
            set
            {
                this.id = value;
            }
        }
        public string ImagePath
        {
            get
            {
                return this.imagePath;
            }
            set
            {
                this.imagePath = value;
            }
        }
    }
}
