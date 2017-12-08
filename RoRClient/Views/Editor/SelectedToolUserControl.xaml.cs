using RoRClient.ViewModels.Editor;
using System.Windows;
using System.Windows.Controls;

namespace RoRClient.Views.Editor
{
    public partial class SelectedToolUserControl : UserControl
    {
        public SelectedToolUserControl()
        {
            InitializeComponent();
        }
        public ToolbarViewModel ToolbarViewModel
        {
            get
            {
                return (ToolbarViewModel)GetValue(ToolbarViewModelProperty);
            }
            set
            {
                SetValue(ToolbarViewModelProperty, value);
            }
        }
        public static readonly DependencyProperty ToolbarViewModelProperty = DependencyProperty.Register("ToolbarViewModel", typeof(ToolbarViewModel), typeof(SelectedToolUserControl), new UIPropertyMetadata(null));
    }

}
