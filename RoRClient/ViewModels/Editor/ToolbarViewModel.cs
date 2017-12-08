using System;
using System.Collections.ObjectModel;
using System.IO;

namespace RoRClient.ViewModels.Editor
{
    public class ToolbarViewModel : ViewModelBase
    {
        private string imageFolderPath = Properties.Settings.Default.ImageFolderPath;

        public ToolbarViewModel()
        {
            //GenerateToolbarItemsFromFolder();
            CreateToolbarItems();
        }

        public ToolbarViewModel(string path, bool generate)
        {
            imageFolderPath = path;
            if (generate)
                GenerateToolbarItemsFromFolder();
            else
                CreateToolbarItems();
        }

        private void CreateToolbarItems()
        {
            toolItems.Add(new ToolItem("rail_ns", imageFolderPath + "rail_ns.png"));
            toolItems.Add(new ToolItem("rail_ew", imageFolderPath + "rail_ew.png"));
            toolItems.Add(new ToolItem("railcurve_se", imageFolderPath + "railcurve_se.png"));
            toolItems.Add(new ToolItem("railcurve_sw", imageFolderPath + "railcurve_sw.png"));
            toolItems.Add(new ToolItem("railcurve_ne", imageFolderPath + "railcurve_ne.png"));
            toolItems.Add(new ToolItem("railcurve_nw", imageFolderPath + "railcurve_nw.png"));
        }

        /// <summary>
        /// Wird vielleicht für's Testen genutzt
        /// </summary>
        private void GenerateToolbarItemsFromFolder()
        {
            string RunningPath = AppDomain.CurrentDomain.BaseDirectory;
            string path = Path.GetFullPath(Path.Combine(RunningPath, @"..\..\")) + imageFolderPath;

            string[] files = Directory.GetFiles(path);
            foreach (string file in files)
            {
                toolItems.Add(new ToolItem(Path.GetFileName(file).TrimEnd(".png".ToCharArray()), file));
            }
        }

        private ObservableCollection<ToolItem> toolItems = new ObservableCollection<ToolItem>();
        public ObservableCollection<ToolItem> ToolItems
        {
            get
            {
                return toolItems;
            }
        }

        private ToolItem selectedTool;

        public ToolItem SelectedTool
        {
            get { return selectedTool; }
            set
            {
                if (selectedTool != value)
                {
                    selectedTool = value;
                    Console.WriteLine(selectedTool);
                    OnPropertyChanged("SelectedTool");
                }
            }
        }


    }
}
