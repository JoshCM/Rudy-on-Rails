using RoRClient.Model.DataTransferObject;
using RoRClient.Model.Models;
using RoRClient.Model.Models.Editor;
using RoRClient.ViewModel;
using RoRClient.ViewModel.Helper;
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

        public ToolItem ToolItem
        {
            get
            {
                return (ToolItem)GetValue(ToolItemProperty);
            }
            set
            {
                SetValue(ToolItemProperty, value);
            }
        }
        public static readonly DependencyProperty ToolItemProperty = DependencyProperty.Register("ToolItem", typeof(ToolItem), typeof(SquareUserControl), new UIPropertyMetadata(null));

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
            messageInformation.PutValue("railSectionPositionNode1", RailSectionPosition.NORTH.ToString());
            messageInformation.PutValue("railSectionPositionNode2", RailSectionPosition.SOUTH.ToString());

            editorSession.QueueSender.SendMessage("CreateRail", messageInformation);
        }
    }
}
