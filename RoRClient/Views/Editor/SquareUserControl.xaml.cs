using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;
using RoRClient.ViewModels.Commands;
using RoRClient.ViewModels.Editor;
using RoRClient.Views.Editor.Helper;
using System;
using System.Windows;
using System.Windows.Input;

namespace RoRClient.Views.Editor
{
    public partial class SquareUserControl : CanvasUserControl
    {
        public SquareUserControl()
        {
            InitializeComponent();
        }

        public SquareViewModel SquareViewModel
        {
            get
            {
                return (SquareViewModel)GetValue(SquareViewModelProperty);
            }
            set
            {
                SetValue(SquareViewModelProperty, value);
            }
        }
        public static readonly DependencyProperty SquareViewModelProperty = DependencyProperty.Register("SquareViewModel", typeof(SquareViewModel), typeof(SquareUserControl), new UIPropertyMetadata(null));

        public String ToolName
		{
            get
            {
                return (String)GetValue(ToolNameProperty);
            }
            set
            {
                SetValue(ToolNameProperty, value);
            }
        }
        public static readonly DependencyProperty ToolNameProperty = DependencyProperty.Register("ToolName", typeof(String), typeof(SquareUserControl), new UIPropertyMetadata(null, OnToolNameChanged));
        private static void OnToolNameChanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            SquareUserControl squareUserControl = (SquareUserControl)d;
            squareUserControl.SquareViewModel.ToolName = squareUserControl.ToolName;
        }
    }
}
