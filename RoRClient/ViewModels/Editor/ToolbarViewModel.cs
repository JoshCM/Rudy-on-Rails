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
            GenerateToolbarItemsFromFolder();
            //CreateToolbarItems();
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
            toolItems.Add(new ToolItem("rail_crossing", imageFolderPath + "rail_crossing.png"));
            toolItems.Add(new ToolItem("switch_sn_se", imageFolderPath + "switch_sn_se.png"));
            selectedTool = toolItems[0];
        }

        /// <summary>
        /// Durchsucht den Ordner Resources/images/Tools und die darin liegenden Unterordner nach Bildern für die Toolbar
        /// </summary>
        private void GenerateToolbarItemsFromFolder()
        {
            string RunningPath = AppDomain.CurrentDomain.BaseDirectory;
            string path = Path.GetFullPath(Path.Combine(RunningPath, @"..\..\")) + imageFolderPath;

            string[] files = Directory.GetFiles(path);

            foreach (string dir in Directory.GetDirectories(path))
            {
                foreach (string file in Directory.GetFiles(dir))
                {
                    toolItems.Add(new ToolItem(Path.GetFileName(file).Split('.')[0], file));
                }
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
