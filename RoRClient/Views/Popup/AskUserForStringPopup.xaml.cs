using System;
using System.Collections.Generic;
using System.ComponentModel;
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
    public partial class AskUserForStringPopup : Window, INotifyPropertyChanged
    {
        private string inputText;
        private string message;
        private bool confirmed;

        #region NotifyPropertyChanged
        public event PropertyChangedEventHandler PropertyChanged;

        public virtual void NotifyPropertyChanged(string propertyName)
        {
            if (PropertyChanged != null)
            {
                this.PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
            }
        }
        #endregion

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
                NotifyPropertyChanged("Message");
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
            Message = message;
        }

        private void OkButton_Click(object sender, RoutedEventArgs e)
        {
            Confirm();
        }

        private void Confirm()
        {
            confirmed = true;
            this.Close();
        }

        private void CancelButton_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void ThisPopup_KeyDown(object sender, KeyEventArgs e)
        {
            if(e.Key == Key.Enter)
            {
                Confirm();
            }
        }
    }
}
