using RoRClient.Model.DataTransferObject;
using RoRClient.Model.Models;
using RoRClient.Model.Models.Editor;
using RoRClient.ViewModel;
using RoRClient.ViewModel.Helper;
using System;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;

namespace RoRClient.View.Editor
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

        public RailSection RailSection
		{
            get
            {
                return (RailSection)GetValue(RailSectionProperty);
            }
            set
            {
                SetValue(RailSectionProperty, value);
            }
        }
        public static readonly DependencyProperty RailSectionProperty = DependencyProperty.Register("RailSection", typeof(RailSection), typeof(SquareUserControl), new UIPropertyMetadata(null));

        private void Rectangle_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            CreateRailCommand();
        }

        private void CreateRailCommand()
        {
            int xPos = X;
            int yPos = Y;
            EditorSession editorSession = EditorSession.GetInstance();

            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", xPos);
            messageInformation.PutValue("yPos", yPos);
            messageInformation.PutValue("railSectionPositionNode1", RailSection.Node1.ToString());
            messageInformation.PutValue("railSectionPositionNode2", RailSection.Node2.ToString());
			editorSession.QueueSender.SendMessage("CreateRail", messageInformation);
        }
    }
}
