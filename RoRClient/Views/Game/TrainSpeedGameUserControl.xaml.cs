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
using RoRClient.ViewModels.Game;

namespace RoRClient.Views.Game
{
    /// <summary>
    /// Interaktionslogik für GameTrainSpeedUserControl.xaml
    /// </summary>
    public partial class TrainSpeedGameUserControl : UserControl
    {
        SpeedSliderViewModel speedSlider;
        public TrainSpeedGameUserControl()
        {
            InitializeComponent();
            speedSlider = new SpeedSliderViewModel();
        }
        public void SliderValueChanged(object sender, RoutedPropertyChangedEventArgs<Double> e)
        {
            speedSlider.UpdateSpeedCommand(e.NewValue);
        }
    }
}
