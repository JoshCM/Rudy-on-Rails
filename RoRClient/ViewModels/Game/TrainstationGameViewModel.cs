using RoRClient.Communication;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Commands;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModels.Game
{
    class TrainstationGameViewModel : CanvasGameViewModel
    {
        private Trainstation trainstation;

        public TrainstationGameViewModel(Trainstation trainstation) : base(trainstation.Id)
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

        /// <summary>
        /// Auswählen/Selektieren von ViewModels
        /// </summary>
        private ICommand selectedTrainstationObjectCommand;
        public ICommand SelectedTrainstationObjectCommand
        {
            get
            {
                if (selectedTrainstationObjectCommand == null)
                {
                    selectedTrainstationObjectCommand = new ActionCommand(param => SelectTrainstationObject());
                }
                return selectedTrainstationObjectCommand;
            }
        }

        /// <summary>
        /// EditorObject (Rail etc.) ausgewählt + Quicknavigation anzeigen
        /// Schickt ein CreateCartCommand an den Server mit den Koordinaten
        /// des CartSpawnPoints
        /// </summary>
        public void SelectTrainstationObject()
        {
            RoRSession gameSession = GameSession.GetInstance();
            MessageInformation messageInformation = new MessageInformation();

			// TODO: sollte eigentlich bei erstellen des Trainstations als spawnPoint mitgeschickt werden
            Rail cartSpawnPointRail = findRail(gameSession.Map);

            Compass compass = findRailDirection();
            System.Console.WriteLine("Compass:" + compass.ToString());
            if(cartSpawnPointRail != null)
            {
                messageInformation.PutValue("posX", cartSpawnPointRail.Square.PosX);
                messageInformation.PutValue("posY", cartSpawnPointRail.Square.PosY);
                messageInformation.PutValue("compass", compass.ToString());
                messageInformation.PutValue("playerId",ClientConnection.GetInstance().ClientId);
                gameSession.QueueSender.SendMessage("CreateCart", messageInformation);
            }


		}

		/// <summary>
		/// Gibt anhand des Trainstations das Rail zurück das dem CartSpawnPoint entspricht
		/// </summary>
		/// <param name="map"></param>
		/// <returns>Rail, welches CartSpawnPoint ist</returns>
		private Rail findRail(Map map)
        {
            switch (trainstation.Alignment)
            {
                case Compass.EAST:
                    return map.GetSquare(trainstation.Square.PosX + 1, trainstation.Square.PosY - 2).PlaceableOnSquare as Rail;
                case Compass.SOUTH:
                    return map.GetSquare(trainstation.Square.PosX + 2, trainstation.Square.PosY - 1).PlaceableOnSquare as Rail;
                case Compass.WEST:
                    return map.GetSquare(trainstation.Square.PosX - 1, trainstation.Square.PosY + 2).PlaceableOnSquare as Rail;
                case Compass.NORTH:
                    return map.GetSquare(trainstation.Square.PosX - 2, trainstation.Square.PosY - 1).PlaceableOnSquare as Rail;
            }

            return null;
        }

		/// <summary>
		/// Gibt das Alignment des CartSpawnPointRails der Trainstation zurück
		/// </summary>
		/// <returns>CartSpawnPointRails der Trainstation</returns>
		private Compass findRailDirection()
        {
            switch (trainstation.Alignment)
            {
                case Compass.EAST:
                    return Compass.NORTH;
                case Compass.SOUTH:
                    return Compass.EAST;
                case Compass.WEST:
                    return Compass.SOUTH;
                case Compass.NORTH:
                    return Compass.WEST;
            }
            return 0;//geht nicht anders...
        }
    }
}
