using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace RoRClient.Views.Popup
{
    public class CustomFileDialogs
    {
        /// <summary>
        /// Nutzt den Windows-eigenen FileDialog, um ein Python Script auszuwählen.
        /// </summary>
        /// <returns>filename, falls ungültig: null</returns>
        public static string AskUserToSelectPythonScript()
        {
            string filename = null;
            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.Filter = "python scipts (*.py)|*.py";
            DialogResult dialogResult = openFileDialog.ShowDialog();

            if (dialogResult == DialogResult.OK && File.Exists(openFileDialog.FileName))
            {
                filename = openFileDialog.FileName;
            }

            return filename;
        }
    }
}
