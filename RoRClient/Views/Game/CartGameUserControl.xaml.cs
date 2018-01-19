using RoRClient.ViewModels;
using RoRClient.ViewModels.Game;
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
using System.Windows.Media.Animation;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace RoRClient.Views.Game
{
    /// <summary>
    /// Interaktionslogik für CartGameUserControl.xaml
    /// </summary>
    public partial class CartGameUserControl : UserControl
    {
        private const double speedFactor = 0.7;
        public CartGameUserControl()
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
        public static readonly DependencyProperty XProperty = DependencyProperty.Register("X", typeof(int), typeof(CartGameUserControl), new UIPropertyMetadata(0, OnXchanged));

        private static void OnXchanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            CartGameUserControl cartGameUserControl = (CartGameUserControl)d;
            CartGameViewModel cartGameViewModel = (CartGameViewModel)cartGameUserControl.DataContext;
            double speedRatio = cartGameViewModel.Cart.Speed > 0 ? cartGameViewModel.Cart.Speed * speedFactor : 1;
            if(cartGameUserControl.RealX == 0)
            {
                cartGameUserControl.RealX = cartGameViewModel.SquarePosX * ViewConstants.SQUARE_DIM;
            }
            cartGameUserControl.BeginAnimation(CartGameUserControl.RealXProperty, new Int32Animation { From = cartGameUserControl.RealX, To = cartGameUserControl.X * ViewConstants.SQUARE_DIM, SpeedRatio = speedRatio });
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
        public static readonly DependencyProperty YProperty = DependencyProperty.Register("Y", typeof(int), typeof(CartGameUserControl), new UIPropertyMetadata(0, OnYchanged));

        private static void OnYchanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            CartGameUserControl cartGameUserControl = (CartGameUserControl)d;
            CartGameViewModel cartGameViewModel = (CartGameViewModel)cartGameUserControl.DataContext;
            double speedRatio = cartGameViewModel.Cart.Speed > 0 ? cartGameViewModel.Cart.Speed * speedFactor : 1;
            if (cartGameUserControl.RealY == 0)
            {
                cartGameUserControl.RealY = cartGameViewModel.SquarePosY * ViewConstants.SQUARE_DIM;
            }
            cartGameUserControl.BeginAnimation(CartGameUserControl.RealYProperty, new Int32Animation { From = cartGameUserControl.RealY, To = cartGameUserControl.Y * ViewConstants.SQUARE_DIM, SpeedRatio = speedRatio });
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
        public static readonly DependencyProperty RealXProperty = DependencyProperty.Register("RealX", typeof(int), typeof(CartGameUserControl), new UIPropertyMetadata(0));

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
        public static readonly DependencyProperty RealYProperty = DependencyProperty.Register("RealY", typeof(int), typeof(CartGameUserControl), new UIPropertyMetadata(0));

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
        public static readonly DependencyProperty SquareDimProperty = DependencyProperty.Register("SquareDim", typeof(int), typeof(CartGameUserControl), new UIPropertyMetadata(0));


        public int RotationAngle
        {
            get
            {
                return (int)GetValue(RotationAngleProperty);
            }
            set
            {
                SetValue(RotationAngleProperty, value);
            }
        }
        public static readonly DependencyProperty RotationAngleProperty = DependencyProperty.Register("RotationAngle", typeof(int), typeof(CartGameUserControl), new UIPropertyMetadata(0, OnRotationAngleChanged));

        private static void OnRotationAngleChanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            CartGameUserControl cartGameUserControl = (CartGameUserControl)d;
            CartGameViewModel cartGameViewModel = (CartGameViewModel)cartGameUserControl.DataContext;
            double speedRatio = cartGameViewModel.Cart.Speed > 0 ? cartGameViewModel.Cart.Speed * speedFactor : 1;
            cartGameUserControl.BeginAnimation(CartGameUserControl.RealRotationAngleProperty, new Int32Animation { From = cartGameUserControl.RealRotationAngle, To = cartGameUserControl.RotationAngle, SpeedRatio = speedRatio, EasingFunction = new QuadraticEase() });
        }

        public int RealRotationAngle
        {
            get
            {
                return (int)GetValue(RealRotationAngleProperty);
            }
            set
            {
                SetValue(RealRotationAngleProperty, value);
            }
        }
        public static readonly DependencyProperty RealRotationAngleProperty = DependencyProperty.Register("RealRotationAngle", typeof(int), typeof(CartGameUserControl), new UIPropertyMetadata(0));
    }
}
