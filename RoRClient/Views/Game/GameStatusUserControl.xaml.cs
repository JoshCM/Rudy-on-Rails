using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Collections.Specialized;
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
    /// Interaktionslogik für GameStatusUserControl.xaml
    /// </summary>
    public partial class GameStatusUserControl : UserControl
    {
        public GameStatusUserControl()
        {
            InitializeComponent();
        }

        public ObservableCollection<Resource> PlayerResources
        {
            get
            {
                return (ObservableCollection<Resource>)GetValue(ResourcesProperty);
            }
            set
            {
                SetValue(ResourcesProperty, value);
            }
        }
        private static void OnPlayerResourcesChanged(object sender, DependencyPropertyChangedEventArgs e)
        {
            GameStatusUserControl gameStatusUserControl = sender as GameStatusUserControl;


            var newCollection = e.NewValue as ObservableCollection<Resource>;

            Console.WriteLine(newCollection.Count);
        }

        public static readonly DependencyProperty ResourcesProperty = DependencyProperty.Register("PlayerResources", typeof(ObservableCollection<Resource>), typeof(GameStatusUserControl), new UIPropertyMetadata(null, OnPlayerResourcesChanged));
    }
}
