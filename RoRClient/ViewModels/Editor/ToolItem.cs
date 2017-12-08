namespace RoRClient.ViewModels.Editor
{
    public class ToolItem
    {
        private string name;
        private string imagePath;

        public ToolItem(string name, string imagePath)
        {
            this.name = name;
            this.imagePath = imagePath;
        }

        public string Name
        {
            get
            {
                return this.name;
            }
            set
            {
                this.name = value;
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
