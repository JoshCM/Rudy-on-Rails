using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Shapes;
using RoRClient.Models.Game;
using RoRClient.ViewModels.Commands;
using Point = System.Windows.Point;

namespace RoRClient.ViewModels.Game
{
    class GameMapViewModel : ViewModelBase
    {
        private Map map = new Map();

        private ObservableCollection<Rectangle> rechteckeCollection = new ObservableCollection<Rectangle>();

        public ObservableCollection<Rectangle> RechteckeCollection
        {
            get { return rechteckeCollection; }
        }

        private void RechteckInit()
        {
            Rectangle r = new Rectangle();
            r.Fill= new SolidColorBrush(Colors.Blue);
        }



        // TODO: Zeigt nocht nichts an
        private ICommand _clickPositionCommand;
        public ICommand ClickPositionCommand
        {
            get
            {
                if (_clickPositionCommand == null)
                {
                    _clickPositionCommand = new ActionCommand(param => LogPosition(param));
                }
                return _clickPositionCommand;
            }
        }

        // TODO: Zeigt nocht nichts an
        public void LogPosition(object param)
        {
            {
                // Mausklick-Position in Canvas
                Point mousePos = Mouse.GetPosition((IInputElement)param);

                // Tupel an der Position anlegen und ViewModel hinzufügen (Anzeige erfolgt über Binding in XAML automatisch)
                MessageBox.Show(mousePos.ToString());

            }

        }
    }
}
