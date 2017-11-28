using RoRClient.ViewModel;
using System.Windows;
using System.Windows.Controls;

namespace RoRClient.View
{
    public partial class SquareUserControl : CanvasUserControl
    {
        public SquareUserControl()
        {
            InitializeComponent();
            SquareDim = ViewConstants.SQUARE_DIM;
        }
        
        public int SquareDim
        {
            get
            {
                return (int)GetValue(SquareDimProperty);
            }
            set
            {
                SetValue(SquareDimProperty, value);
            }
        }
        public static readonly DependencyProperty SquareDimProperty = DependencyProperty.Register("SquareDim", typeof(int), typeof(SquareUserControl), new UIPropertyMetadata(0));
    }
}
