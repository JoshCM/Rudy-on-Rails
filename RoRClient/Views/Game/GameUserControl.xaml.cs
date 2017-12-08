﻿using System;
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
    /// Interaktionslogik für GameUserControl.xaml
    /// </summary>
    public partial class GameUserControl : UserControl
    {
        public GameUserControl()
        {
            InitializeComponent();
            GameStatusUserControl u2 = new GameStatusUserControl();
            cc.Content = u2;
        }
    }
}
