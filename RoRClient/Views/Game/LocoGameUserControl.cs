using RoRClient.ViewModels;
using RoRClient.ViewModels.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Media.Animation;

namespace RoRClient.Views.Game
{
    public class LocoGameUserControl : UserControl
    {
        private const double speedFactor = 0.7;
        public LocoGameUserControl()
        {
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
        public static readonly DependencyProperty XProperty = DependencyProperty.Register("X", typeof(int), typeof(LocoGameUserControl), new UIPropertyMetadata(0, OnXchanged));

        private static void OnXchanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            LocoGameUserControl LocoGameUserControl = (LocoGameUserControl)d;
            LocoGameViewModel locoGameViewModel = (LocoGameViewModel)LocoGameUserControl.DataContext;
            double speedRatio = locoGameViewModel.Loco.Speed > 0 ? locoGameViewModel.Loco.Speed * speedFactor : 1;
            LocoGameUserControl.BeginAnimation(LocoGameUserControl.RealXProperty, new Int32Animation { From = LocoGameUserControl.RealX, To = LocoGameUserControl.X * ViewConstants.Instance.SquareDim, SpeedRatio = speedRatio });
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
        public static readonly DependencyProperty YProperty = DependencyProperty.Register("Y", typeof(int), typeof(LocoGameUserControl), new UIPropertyMetadata(0, OnYchanged));

        private static void OnYchanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            LocoGameUserControl LocoGameUserControl = (LocoGameUserControl)d;
            LocoGameViewModel locoGameViewModel = (LocoGameViewModel)LocoGameUserControl.DataContext;
            double speedRatio = locoGameViewModel.Loco.Speed > 0 ? locoGameViewModel.Loco.Speed * speedFactor : 1;
            LocoGameUserControl.BeginAnimation(LocoGameUserControl.RealYProperty, new Int32Animation { From = LocoGameUserControl.RealY, To = LocoGameUserControl.Y * ViewConstants.Instance.SquareDim, SpeedRatio = speedRatio });
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
        public static readonly DependencyProperty RealXProperty = DependencyProperty.Register("RealX", typeof(int), typeof(LocoGameUserControl), new UIPropertyMetadata(0));

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
        public static readonly DependencyProperty RealYProperty = DependencyProperty.Register("RealY", typeof(int), typeof(LocoGameUserControl), new UIPropertyMetadata(0));

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
        public static readonly DependencyProperty RotationAngleProperty = DependencyProperty.Register("RotationAngle", typeof(int), typeof(LocoGameUserControl), new UIPropertyMetadata(0, OnRotationAngleChanged));

        private static void OnRotationAngleChanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            LocoGameUserControl LocoGameUserControl = (LocoGameUserControl)d;
            LocoGameViewModel locoGameViewModel = (LocoGameViewModel)LocoGameUserControl.DataContext;
            double speedRatio = locoGameViewModel.Loco.Speed > 0 ? locoGameViewModel.Loco.Speed * speedFactor : 1;
            LocoGameUserControl.BeginAnimation(LocoGameUserControl.RealRotationAngleProperty, new Int32Animation { From = LocoGameUserControl.RealRotationAngle, To = LocoGameUserControl.RotationAngle, SpeedRatio = speedRatio, EasingFunction = new QuadraticEase() });
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
        public static readonly DependencyProperty RealRotationAngleProperty = DependencyProperty.Register("RealRotationAngle", typeof(int), typeof(LocoGameUserControl), new UIPropertyMetadata(0));
    }
}