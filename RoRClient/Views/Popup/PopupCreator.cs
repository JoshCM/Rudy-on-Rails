using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Views.Popup
{
    class PopupCreator
    {
        public static string AskUserToInputString(string message)
        {
            AskUserForStringPopup popup = new AskUserForStringPopup(message);
            popup.ShowDialog();
            if (popup.Confirmed)
            {
                return popup.InputText;
            }
            return "";
        }

       // public static void ShowRules()
        //{
        //    RulesPopup popup = new RulesPopup();
          //  popup.ShowDialog();
       // }

    }
}
