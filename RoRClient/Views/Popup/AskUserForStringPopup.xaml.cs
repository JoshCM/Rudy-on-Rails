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

namespace RoRClient.Views.Popup
{
    public partial class AskUserForStringPopup : Window
    {
        private string inputText;
        private string message;
        private bool confirmed;

        public string InputText
        {
            get
            {
                return inputText;
            }
            set
            {
                inputText = value;
            }
        }

        public string Message
        {
            get
            {
                return message;
            }
            set
            {
                message = value;
            }
        }

        public bool Confirmed
        {
            get
            {
                return confirmed;
            }
        }

        public AskUserForStringPopup(string message)
        {
            InitializeComponent();
            this.message = message;
        }

        private void OkButton_Click(object sender, RoutedEventArgs e)
        {
            confirmed = true;
            this.Close();
        }

        private void CancelButton_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }
    }
}
