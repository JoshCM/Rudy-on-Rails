using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Commands.Base;

namespace RoRClient.ViewModels.Game
{
    class SpeedSliderViewModel
    {
        private ICommand updateSpeedCommand;
        internal ICommand UpdateSpeedCommand(double newValue)
        {
            sendUpdateSpeedCommand(newValue);

            return updateSpeedCommand;
        }

        private void sendUpdateSpeedCommand(double newValue)
        {
            Console.WriteLine("neuer Wert:" + newValue);
        }
    }
}
