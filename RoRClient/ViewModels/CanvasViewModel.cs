using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Commands;
using System;
using System.Windows.Input;

namespace RoRClient.ViewModels
{
    /// <summary>
    /// Diese Klasse wird als Base-Klasse für alle ViewModels verwendet, die auf einem Canvas angezeigt werden.
    /// Dazu gehören zum Beispiel Squares und Rails. 
    /// </summary>
    public class CanvasViewModel : ViewModelBase
    {
        public CanvasViewModel(Guid modelId)
        {
            id = modelId;
        }

        private Guid id;
        public Guid Id
        {
            get
            {
                return id;
            }
        }

        private int squarePosX;
        public int SquarePosX
        {
            get
            {
                return squarePosX;
            }

            set
            {
                if (squarePosX != value)
                {
                    squarePosX = value;
                    OnPropertyChanged("SquarePosX");
                }
            }
        }

        private int squarePosY;
        public int SquarePosY
        {
            get
            {
                return squarePosY;
            }

            set
            {
                if (squarePosY != value)
                {
                    squarePosY = value;
                    OnPropertyChanged("SquarePosY");
                }
            }
        }

        // Auswählen/Selektieren von ViewModels
        private ICommand selectInteractiveGameObjectCommand;
        public ICommand SelectInteractiveGameObjectCommand
        {
            get
            {
                if (selectInteractiveGameObjectCommand == null)
                {
                    selectInteractiveGameObjectCommand = new ActionCommand(param => SelectInteractiveGameObject());
                }
                return selectInteractiveGameObjectCommand;
            }
        }

        // EditorObject (Rail etc.) ausgewählt
        public void SelectInteractiveGameObject()
        {
            Console.WriteLine("Selected ViewModel: " + this.ToString() + " / ID: " +  this.Id);
        }


        private ICommand deletePlaceableOnSquareCommand;
        public ICommand DeletePlaceableOnSquareCommand
        {
            get
            {
                if (deletePlaceableOnSquareCommand == null)
                {
                    deletePlaceableOnSquareCommand = new ActionCommand(param => SendDeletePlaceableOnSquareCommand());
                }
                return deletePlaceableOnSquareCommand;
            }
        }

        private void SendDeletePlaceableOnSquareCommand()
        {

            int xPos = squarePosX;
            int yPos = squarePosY;
            RoRSession editorSession = EditorSession.GetInstance();

            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", xPos);
            messageInformation.PutValue("yPos", yPos);

            // TODO: Message sollte mithilfe CommandManager oder so geschickt werden
            editorSession.QueueSender.SendMessage("DeletePlaceable", messageInformation);
        }
    }
}
