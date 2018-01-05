using RoRClient.ViewModels;
using RoRClient.ViewModels.Editor;
using System.Windows;

namespace RoRClient.Views.Editor
{
    public partial class RailEditorUserControl : CanvasUserControl
    {
        public RailEditorUserControl()
        {
            InitializeComponent();
            SignalDimension = ViewConstants.SIGNAL_DIMENSION;
            UpperSignalPos = 5;
            LowerSignalPos = ViewConstants.SQUARE_DIM - UpperSignalPos - SignalDimension;
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
        public static readonly DependencyProperty SignalDimensionProperty = DependencyProperty.Register("SignalDimension", typeof(int), typeof(RailEditorUserControl), new UIPropertyMetadata(0));

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
        public static readonly DependencyProperty UpperSignalPosProperty = DependencyProperty.Register("UpperSignalPos", typeof(int), typeof(RailEditorUserControl), new UIPropertyMetadata(0));

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
        public static readonly DependencyProperty LowerSignalPosProperty = DependencyProperty.Register("LowerSignalPos", typeof(int), typeof(RailEditorUserControl), new UIPropertyMetadata(0));
    }
}
