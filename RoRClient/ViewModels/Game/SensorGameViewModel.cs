using RoRClient.Models.Game;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Game
{
    public class SensorGameViewModel : CanvasGameViewModel
    {

        private Sensor sensor;

        public SensorGameViewModel(Sensor sensor) : base(sensor.Id)
        {
            this.sensor = sensor;
            this.SquarePosX = sensor.
        }
    }
}
