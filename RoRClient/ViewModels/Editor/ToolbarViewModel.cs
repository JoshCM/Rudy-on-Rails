using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.ViewModel.Helper;
using System.Collections.ObjectModel;
using System.Windows.Input;
using RoRClient.Model.Models;
using System.IO;
using System.Reflection;

namespace RoRClient.ViewModel.Editor
{
    public class ToolbarViewModel : ViewModelBase
    {
        private const string IMAGE_FOLDER_PATH = "..\\..\\Resources\\Images\\Tools\\";

        public ToolbarViewModel()
        {
            CreateToolbarItems();
        }

        private void CreateToolbarItems()
        {
            toolItems.Add(new ToolItem("rail_ns", IMAGE_FOLDER_PATH + "rail_ns.png"));
            toolItems.Add(new ToolItem("rail_ew", IMAGE_FOLDER_PATH + "rail_ew.png"));
            toolItems.Add(new ToolItem("railcurve_se", IMAGE_FOLDER_PATH + "railcurve_se.png"));
            toolItems.Add(new ToolItem("railcurve_sw", IMAGE_FOLDER_PATH + "railcurve_sw.png"));
            toolItems.Add(new ToolItem("railcurve_ne", IMAGE_FOLDER_PATH + "railcurve_ne.png"));
            toolItems.Add(new ToolItem("railcurve_nw", IMAGE_FOLDER_PATH + "railcurve_nw.png"));
        }

        /// <summary>
        /// Wird vielleicht für's Testen genutzt
        /// </summary>
        private void GenerateToolbarItemsFromFolder()
        {
            string RunningPath = AppDomain.CurrentDomain.BaseDirectory;
            string path = Path.GetFullPath(Path.Combine(RunningPath, @"..\..\")) + IMAGE_FOLDER_PATH;

            string[] files = Directory.GetFiles(path);
            foreach (string file in files)
            {
                toolItems.Add(new ToolItem(Path.GetFileName(file), file));
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
