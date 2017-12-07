using RoRClient.Model.Models;
using RoRClient.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace RoRClient.View.Editor
{
    public partial class MapUserControl : UserControl
    {
        public MapUserControl()
        {
            InitializeComponent();
        }

		public ToolItem SelectedTool
		{
			get
			{
				return (ToolItem)GetValue(SelectedToolProperty);
			}
			set
			{
				SetValue(SelectedToolProperty, value);
			}
		}
		public static readonly DependencyProperty SelectedToolProperty = DependencyProperty.Register("SelectedTool", typeof(ToolItem), typeof(MapUserControl), new UIPropertyMetadata(null));

	}
}
