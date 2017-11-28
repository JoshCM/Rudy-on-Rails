using RoRClient.ViewModel;
using System.Windows;
using System.Windows.Controls;

namespace RoRClient.View
{
    public partial class SquareUserControl : UserControl
    {
        public SquareUserControl()
        {
            InitializeComponent();
            SquareDim = ViewConstants.SQUARE_DIM;
        }
        public int X
        {
            get
            {
                return (int)GetValue(XProperty);
            }
            set
            {
                SetValue(XProperty, value);
            }
        }
        public static readonly DependencyProperty XProperty = DependencyProperty.Register("X", typeof(int), typeof(SquareUserControl), new UIPropertyMetadata(0, OnXchanged));

        private static void OnXchanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            SquareUserControl squareUserControl = (SquareUserControl)d;
            squareUserControl.RealX = squareUserControl.X * ViewConstants.SQUARE_DIM;
        }

        public int RealX
        {
            get
            {
                return (int)GetValue(XProperty);
            }
            set
            {
                SetValue(RealXProperty, value);
            }
        }
        public static readonly DependencyProperty RealXProperty = DependencyProperty.Register("RealX", typeof(int), typeof(SquareUserControl), new UIPropertyMetadata(0));

        public int Y
        {
            get
            {
                return (int)GetValue(YProperty);
            }
            set
            {
                SetValue(YProperty, value);
            }
        }
        public static readonly DependencyProperty YProperty = DependencyProperty.Register("Y", typeof(int), typeof(SquareUserControl), new UIPropertyMetadata(0, OnYchanged));

        private static void OnYchanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            SquareUserControl squareUserControl = (SquareUserControl)d;
            squareUserControl.RealY = squareUserControl.Y * ViewConstants.SQUARE_DIM;
        }
        public int RealY
        {
            get
            {
                return (int)GetValue(YProperty);
            }
            set
            {
                SetValue(RealYProperty, value);
            }
        }
        public static readonly DependencyProperty RealYProperty = DependencyProperty.Register("RealY", typeof(int), typeof(SquareUserControl), new UIPropertyMetadata(0));

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
