using RoRClient.ViewModels.Editor;
using System.Windows;

namespace RoRClient.Views.Editor
{
    /// <summary>
    /// Interaktionslogik für RailUserControl.xaml
    /// </summary>
    public partial class RailUserControl : CanvasUserControl
    {
        public RailUserControl()
        {
            InitializeComponent();
        }

        public RailViewModel RailViewModel
        {
            get
            {
                return (RailViewModel)GetValue(RailViewModelProperty);
            }
            set
            {
                SetValue(RailViewModelProperty, value);
            }
        }
        public static readonly DependencyProperty RailViewModelProperty = DependencyProperty.Register("RailViewModel", typeof(RailViewModel), typeof(RailUserControl), new UIPropertyMetadata(null));
    }
}
