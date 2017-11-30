using RoRClient.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;

namespace RoRClient.View
{
    /// <summary>
    /// Oberklasse für alle UserControls, die Objekte auf dem Spielfeld anzeigen
    /// </summary>
    public class CanvasUserControl : UserControl
    {
        public CanvasUserControl()
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
        public static readonly DependencyProperty XProperty = DependencyProperty.Register("X", typeof(int), typeof(CanvasUserControl), new UIPropertyMetadata(0, OnXchanged));

        private static void OnXchanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            CanvasUserControl CanvasUserControl = (CanvasUserControl)d;
            CanvasUserControl.RealX = CanvasUserControl.X * ViewConstants.SQUARE_DIM;
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
            CanvasUserControl CanvasUserControl = (CanvasUserControl)d;
            CanvasUserControl.RealY = CanvasUserControl.Y * ViewConstants.SQUARE_DIM;
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
