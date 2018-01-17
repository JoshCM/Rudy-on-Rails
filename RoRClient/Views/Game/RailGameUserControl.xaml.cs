using RoRClient.ViewModels;
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

namespace RoRClient.Views.Game
{
    /// <summary>
    /// Interaktionslogik für RailGameUserControl.xaml
    /// </summary>
    public partial class RailGameUserControl : CanvasUserControl
    {
        public RailGameUserControl()
        {
            InitializeComponent();
            SignalDimension = ViewConstants.SIGNAL_DIMENSION;
            UpperSignalPos = ViewConstants.UPPER_SIGNAL_POS;
            LowerSignalPos = ViewConstants.LOWER_SIGNAL_POS;
        }

        public int SignalDimension
        {
            get
            {
                return (int)GetValue(SignalDimensionProperty);
            }
            set
            {
                SetValue(SignalDimensionProperty, value);
            }
        }
        public static readonly DependencyProperty SignalDimensionProperty = DependencyProperty.Register("SignalDimension", typeof(int), typeof(RailGameUserControl), new UIPropertyMetadata(0));

        public int UpperSignalPos
        {
            get
            {
                return (int)GetValue(UpperSignalPosProperty);
            }
            set
            {
                SetValue(UpperSignalPosProperty, value);
            }
        }
        public static readonly DependencyProperty UpperSignalPosProperty = DependencyProperty.Register("UpperSignalPos", typeof(int), typeof(RailGameUserControl), new UIPropertyMetadata(0));

        public int LowerSignalPos
        {
            get
            {
                return (int)GetValue(LowerSignalPosProperty);
            }
            set
            {
                SetValue(LowerSignalPosProperty, value);
            }
        }
        public static readonly DependencyProperty LowerSignalPosProperty = DependencyProperty.Register("LowerSignalPos", typeof(int), typeof(RailGameUserControl), new UIPropertyMetadata(0));
    }
}
