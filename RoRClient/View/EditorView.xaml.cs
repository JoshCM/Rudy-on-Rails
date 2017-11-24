using RoRClient.ViewModel;
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
using System.Windows.Shapes;

namespace RoRClient.View
{
    /// <summary>
    /// Interaktionslogik für Window1.xaml
    /// </summary>
    public partial class EditorView : Window
    {
        public EditorView()
        {
            InitializeComponent();
        }

        // Das hier ist nur zum Test und extrem dirty, hier soll der Command-Mechanismus verwendet werden, sobald er eingebaut ist
        private void Button_Click(object sender, RoutedEventArgs e)
        {
            EditorViewModel viewModel = (EditorViewModel)DataContext;
            Random rand = new Random();
            foreach (DummySquare square in viewModel.Squares)
            {
                square.Rail = null;

                if (rand.Next(2) == 0)
                {
                    square.Rail = new DummyRail();
                }
            }
        }
    }
}
