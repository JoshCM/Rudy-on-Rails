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
    /// Interaktionslogik für DummySquareUserControl.xaml
    /// </summary>
    public partial class DummySquareUserControl : UserControl
    {
        private const int SQUARE_DIM = 50;

        public DummySquareUserControl()
        {
            InitializeComponent();
            SquareDim = SQUARE_DIM;
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
        public static readonly DependencyProperty XProperty = DependencyProperty.Register("X", typeof(int), typeof(DummySquareUserControl), new UIPropertyMetadata(0, OnXchanged));

        private static void OnXchanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            DummySquareUserControl dummySquareUserControl = (DummySquareUserControl)d;
            dummySquareUserControl.RealX = dummySquareUserControl.X * SQUARE_DIM;
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
        public static readonly DependencyProperty RealXProperty = DependencyProperty.Register("RealX", typeof(int), typeof(DummySquareUserControl), new UIPropertyMetadata(0));

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
        public static readonly DependencyProperty YProperty = DependencyProperty.Register("Y", typeof(int), typeof(DummySquareUserControl), new UIPropertyMetadata(0, OnYchanged));

        private static void OnYchanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            DummySquareUserControl dummySquareUserControl = (DummySquareUserControl)d;
            dummySquareUserControl.RealY = dummySquareUserControl.Y * SQUARE_DIM;
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
        public static readonly DependencyProperty RealYProperty = DependencyProperty.Register("RealY", typeof(int), typeof(DummySquareUserControl), new UIPropertyMetadata(0));

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
        public static readonly DependencyProperty SquareDimProperty = DependencyProperty.Register("SquareDim", typeof(int), typeof(DummySquareUserControl), new UIPropertyMetadata(0));
    }
}
