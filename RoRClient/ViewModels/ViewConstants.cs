using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels
{
    /// <summary>
    /// Hier kommen alle Konstanten hin, die die Views betreffen
    /// </summary>
    public class ViewConstants : NotifyPropertyChangedBase
    {
        private static ViewConstants instance;
        private TaskFactory taskFactory;

        private int squareDim;
        private int signalDimension;
        private int upperSignalPos;
        private int lowerSignalPos;
        private int sensorDimension;

        public ViewConstants()
        {
            Init();
        }

        public static ViewConstants Instance
        {
            get
            {
                if(instance == null)
                {
                    instance = new ViewConstants();
                }
                return instance;
            }
        }

        public int SquareDim
        {
            get
            {
                return squareDim;
            }
            set
            {
                if(squareDim != value)
                {
                    squareDim = value;
                    OnPropertyChanged("SquareDim");
                    UpdateValuesDependentOnSquareDim();
                }
            }
        }

        public int SignalDimension
        {
            get
            {
                return signalDimension;
            }
            set
            {
                if (signalDimension != value)
                {
                    signalDimension = value;
                    OnPropertyChanged("SignalDimension");
                }
            }
        }

        public void Init()
        {
            SquareDim = 50;
            UpperSignalPos = 4;
        }

        public void UpdateValuesDependentOnSquareDim()
        {
            SignalDimension = squareDim / 5;
            LowerSignalPos = squareDim - upperSignalPos - signalDimension;
            SensorDimension = SquareDim / 2;
        } 

        public int UpperSignalPos
        {
            get
            {
                return upperSignalPos;
            }
            set
            {
                if (upperSignalPos != value)
                {
                    upperSignalPos = value;
                    OnPropertyChanged("UpperSignalPos");
                }
            }
        }

        public int LowerSignalPos
        {
            get
            {
                return lowerSignalPos;
            }
            set
            {
                if (lowerSignalPos != value)
                {
                    lowerSignalPos = value;
                    OnPropertyChanged("LowerSignalPos");
                }
            }
        }

        public int SensorDimension
        {
            get
            {
                return sensorDimension;
            }
            set
            {
                if (sensorDimension != value)
                {
                    sensorDimension = value;
                    OnPropertyChanged("SensorDimension");
                }
            }
        }

        public TaskFactory TaskFactory
        {
            get
            {
                return taskFactory;
            }
            set
            {
                taskFactory = value;
            }
        }
    }
}
