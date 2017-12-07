using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Editor;
using RoRClient.Models.Game;
using RoRClient.ViewModels.Commands;
using RoRClient.Views.Editor.Helper;
using System;
using System.Windows;
using System.Windows.Input;

namespace RoRClient.Views.Editor
{
    public partial class SquareUserControl : CanvasUserControl
    {
        public SquareUserControl()
        {
            InitializeComponent();
        }

        private ICommand createSelectedPlaceableOnSquareCommand;
        public ICommand CreateSelectedPlaceableOnSquareCommand
        {
            get
            {
                if (createSelectedPlaceableOnSquareCommand == null)
                {
                    createSelectedPlaceableOnSquareCommand = new ActionCommand(e => { CreateRailCommand(); });
                }
                return createSelectedPlaceableOnSquareCommand;
            }
        }

        public String ToolName
		{
            get
            {
                return (String)GetValue(ToolNameProperty);
            }
            set
            {
                SetValue(ToolNameProperty, value);
            }
        }
        public static readonly DependencyProperty ToolNameProperty = DependencyProperty.Register("ToolName", typeof(String), typeof(SquareUserControl), new UIPropertyMetadata(null));

        private void Rectangle_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            // TODO: hier sollte es eine Aufteilung geben, je nachdem was der User machen wollte
            CreateRailCommand();
        }

        /// <summary>
        /// Baut eine Message aus ToolName des SelectedTool und der Position (xPos, yPos)
        /// und schickt diese über den QueueSender an den Server
        /// </summary>
        private void CreateRailCommand()
        {
            int xPos = X;
            int yPos = Y;
            EditorSession editorSession = EditorSession.GetInstance();
            RailSection railSection = ToolConverter.convertToRailSection(ToolName);

            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", xPos);
            messageInformation.PutValue("yPos", yPos);
            messageInformation.PutValue("railSectionPositionNode1", railSection.Node1.ToString());
            messageInformation.PutValue("railSectionPositionNode2", railSection.Node2.ToString());

            // TODO: Message sollte mithilfe CommandManager oder so geschickt werden
			editorSession.QueueSender.SendMessage("CreateRail", messageInformation);
        }
    }
}
