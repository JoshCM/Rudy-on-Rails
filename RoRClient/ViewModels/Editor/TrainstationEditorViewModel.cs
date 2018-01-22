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
    public class TrainstationEditorViewModel : CanvasEditorViewModel
    {
        private Trainstation trainstation; 

        public TrainstationEditorViewModel(Trainstation trainstation) : base(trainstation.Id)
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

			// Id der Trainstation
			messageInformation.PutValue("trainstationId", trainstation.Id);

			List<Guid> railGuids = new List<Guid>();
            // Iteriert über alle TrainstationRails
            foreach (Rail trainstationRail in trainstation.TrainstationRails)
            {
                railGuids.Add(trainstationRail.Id);
            }
            messageInformation.PutValue("trainstationRailIds", railGuids);
            messageInformation.PutValue("stockId", trainstation.Stock.Id);
            editorSession.QueueSender.SendMessage("DeleteTrainstation", messageInformation);

			// setze das Selektierte Objekt auf null
			MapViewModel.SelectedEditorCanvasViewModel = null;
		}

        public override void Move()
        {
			
		}


        public override void RotateLeft()
        {
			MessageInformation messageInformation = new MessageInformation();
			messageInformation.PutValue("id", trainstation.Id);
			messageInformation.PutValue("right", false);
            EditorSession.GetInstance().QueueSender.SendMessage("RotateTrainstation", messageInformation);
		}

        public override void RotateRight()
        {
            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("id", trainstation.Id);
            messageInformation.PutValue("right", true);
            EditorSession.GetInstance().QueueSender.SendMessage("RotateTrainstation", messageInformation);
        }
    }
}
