using RoRClient.Views.Editor;
using RoRClient.ViewModels;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Media.Animation;
using System.ComponentModel;

namespace RoRClient.Views
{
    /// <summary>
    /// Oberklasse für alle UserControls, die Objekte auf dem Spielfeld anzeigen
    /// </summary>
    public class CanvasUserControl : UserControl
    {
        public CanvasUserControl()
        {
            ViewConstants.Instance.PropertyChanged += UpdatePositions;
        }

        private void UpdatePositions(object sender, PropertyChangedEventArgs e)
        {
            RealX = X * ViewConstants.Instance.SquareDim;
            RealY = Y * ViewConstants.Instance.SquareDim;
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
        public static readonly DependencyProperty XProperty = DependencyProperty.Register("X", typeof(int), typeof(CanvasUserControl), new UIPropertyMetadata(0, OnXchanged));

        private static void OnXchanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            CanvasUserControl canvasUserControl = (CanvasUserControl)d;
            canvasUserControl.RealX = canvasUserControl.X * ViewConstants.Instance.SquareDim;
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
        public static readonly DependencyProperty YProperty = DependencyProperty.Register("Y", typeof(int), typeof(CanvasUserControl), new UIPropertyMetadata(0, OnYchanged));

        private static void OnYchanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            CanvasUserControl canvasUserControl = (CanvasUserControl)d;
            canvasUserControl.RealY = canvasUserControl.Y * ViewConstants.Instance.SquareDim;
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
        public static readonly DependencyProperty RealXProperty = DependencyProperty.Register("RealX", typeof(int), typeof(CanvasUserControl), new UIPropertyMetadata(0));

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
        public static readonly DependencyProperty RealYProperty = DependencyProperty.Register("RealY", typeof(int), typeof(CanvasUserControl), new UIPropertyMetadata(0));
    }
}
