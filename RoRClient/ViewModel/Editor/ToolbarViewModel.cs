using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Model.EditorModels;
using RoRClient.ViewModel.Helper;
using System.Collections.ObjectModel;
using System.Windows.Input;
using RoRClient.Model.Models;
using System.IO;
using System.Reflection;

namespace RoRClient.ViewModel.Editor
{
    class ToolbarViewModel : ViewModelBase
    {
        private string previewImagePath;
        private const string IMAGE_FOLDER_PATH = "..\\..\\Resources\\Images\\Tools\\";
        public ToolbarViewModel()
        {
            string[] paths = System.IO.Directory.GetFiles(IMAGE_FOLDER_PATH);
            foreach(string path in paths)
            {
                toolItems.Add(new ToolItem(Path.GetFileNameWithoutExtension(path), path));
            }
        }

        private ObservableCollection<ToolItem> toolItems = new ObservableCollection<ToolItem>();
        public ObservableCollection<ToolItem> ToolItems
        {
            get { return toolItems; }
        }
        public string PreviewImagePath
        {
            get { return previewImagePath; }
            set
            {
                if (previewImagePath != value)
                {
                    previewImagePath = value;
                    OnPropertyChanged();
                }
            }
        }
        
    }
}
