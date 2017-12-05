﻿using RoRClient.ViewModel.Editor;
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

namespace RoRClient.View.Editor
{
    /// <summary>
    /// Interaktionslogik für SelectedToolUserControl.xaml
    /// </summary>
    public partial class SelectedToolUserControl : UserControl
    {
        public SelectedToolUserControl()
        {
            InitializeComponent();
        }
        public ToolbarViewModel ToolbarViewModel
        {
            get
            {
                return (ToolbarViewModel)GetValue(ToolbarViewModelProperty);
            }
            set
            {
                Console.WriteLine(value);
                SetValue(ToolbarViewModelProperty, value);
            }
        }
        public static readonly DependencyProperty ToolbarViewModelProperty = DependencyProperty.Register("ToolbarViewModel", typeof(ToolbarViewModel), typeof(SelectedToolUserControl), new UIPropertyMetadata(null));
    }

}
