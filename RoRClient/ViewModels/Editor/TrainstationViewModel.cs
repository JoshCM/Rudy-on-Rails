using Newtonsoft.Json.Linq;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.ViewModels.Editor
{
    /// <summary>
    /// Hält die zugehörige Trainstation und die Position (SquarePosX, SquarePosY) der Trainstation
    /// </summary>
    public class TrainstationViewModel : CanvasViewModel
    {
        private Trainstation trainstation; 

        public TrainstationViewModel(Trainstation trainstation) : base(trainstation.Id)
        {
            this.trainstation = trainstation;
            this.SquarePosX = trainstation.Square.PosX;
            this.SquarePosY = trainstation.Square.PosY;
        }

        public Trainstation Trainstation
        {
            get
            {
                return trainstation;
            }
        }

        public override void Delete()
        {
            RoRSession editorSession = EditorSession.GetInstance();
            MessageInformation messageInformation = new MessageInformation();

            // Position der Trainstation
            messageInformation.PutValue("xPos", trainstation.Square.PosX);
            messageInformation.PutValue("yPos", trainstation.Square.PosY);

            List<JObject> railJObjects = new List<JObject>();
            // Iteriert über alle TrainstationRails
            foreach (Rail trainstationRail in trainstation.TrainstationRails)
            {
                // JSONObject der TrainstationRail
                JObject railJObject = new JObject();
                railJObject.Add("railId", trainstationRail.Id);
                railJObjects.Add(railJObject);
            }
            messageInformation.PutValue("trainstationRails", railJObjects);
            
            editorSession.QueueSender.SendMessage("DeleteTrainstation", messageInformation);
            Console.WriteLine("DELETE TRAINSTATION");
        }

        public override void RotateLeft()
        {
            throw new NotImplementedException();
        }

        public override void RotateRight()
        {
            throw new NotImplementedException();
        }
    }
}
