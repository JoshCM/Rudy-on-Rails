using RoRClient.ViewModels.Editor;
using System.Windows;
using System.Windows.Controls;

namespace RoRClient.Views.Editor
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
