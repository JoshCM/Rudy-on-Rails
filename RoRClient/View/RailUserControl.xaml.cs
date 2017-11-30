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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace RoRClient.View
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
