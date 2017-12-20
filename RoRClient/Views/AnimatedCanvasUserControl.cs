using RoRClient.Views.Editor;
using RoRClient.ViewModels;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Media.Animation;

namespace RoRClient.Views
{
    /// <summary>
    /// Oberklasse für alle UserControls, die Objekte auf dem Spielfeld anzeigen
    /// </summary>
    public class AnimatedCanvasUserControl : UserControl
    {
        public AnimatedCanvasUserControl()
        {
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
        public static readonly DependencyProperty XProperty = DependencyProperty.Register("X", typeof(int), typeof(AnimatedCanvasUserControl), new UIPropertyMetadata(0, OnXchanged));

        private static void OnXchanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            AnimatedCanvasUserControl canvasUserControl = (AnimatedCanvasUserControl)d;
            canvasUserControl.BeginAnimation(AnimatedCanvasUserControl.RealXProperty, new Int32Animation { From = canvasUserControl.RealX, To = canvasUserControl.X * ViewConstants.SQUARE_DIM });
        }


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
        public static readonly DependencyProperty YProperty = DependencyProperty.Register("Y", typeof(int), typeof(AnimatedCanvasUserControl), new UIPropertyMetadata(0, OnYchanged));

        private static void OnYchanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            AnimatedCanvasUserControl canvasUserControl = (AnimatedCanvasUserControl)d;
            canvasUserControl.BeginAnimation(AnimatedCanvasUserControl.RealYProperty, new Int32Animation { From = canvasUserControl.RealY, To = canvasUserControl.Y * ViewConstants.SQUARE_DIM });
        }

        public int RealX
        {
            get
            {
                return (int)GetValue(RealXProperty);
            }
            set
            {
                SetValue(RealXProperty, value);
            }
        }
        public static readonly DependencyProperty RealXProperty = DependencyProperty.Register("RealX", typeof(int), typeof(AnimatedCanvasUserControl), new UIPropertyMetadata(0));

        public int RealY
        {
            get
            {
                return (int)GetValue(RealYProperty);
            }
            set
            {
                SetValue(RealYProperty, value);
            }
        }
        public static readonly DependencyProperty RealYProperty = DependencyProperty.Register("RealY", typeof(int), typeof(AnimatedCanvasUserControl), new UIPropertyMetadata(0));
  
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
        public static readonly DependencyProperty SquareDimProperty = DependencyProperty.Register("SquareDim", typeof(int), typeof(AnimatedCanvasUserControl), new UIPropertyMetadata(0));
    }
}
