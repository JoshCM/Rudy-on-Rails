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

        public ToolbarViewModel()
        {
            string RunningPath = AppDomain.CurrentDomain.BaseDirectory;
            string path = Path.GetFullPath(Path.Combine(RunningPath, @"..\..\")) + "\\Resources\\Images\\Tools";

            string[] files = Directory.GetFiles(path);
            foreach (string file in files)
            {
                toolItems.Add(new ToolItem(Path.GetFileName(file), file));
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
