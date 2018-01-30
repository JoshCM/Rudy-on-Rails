using RoRClient.Sound;
using RoRClient.ViewModels.Helper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace RoRClient.ViewModels.Editor
{
    class EditorViewModel : ViewModelBase
    {
        private UIState uiState;
        private MapEditorViewModel mapViewModel;
        private ToolbarViewModel toolbarViewModel;
        private TopMenuViewModel topMenuViewModel;
        private ObjectConfigurationViewModel objectConfigurationViewModel;
        private BackgroundSound background = new BackgroundSound();

        public MapEditorViewModel MapViewModel
        {
            get
            {
                return mapViewModel;
            }
        }

        public ToolbarViewModel ToolbarViewModel
        {
            get
            {
                return toolbarViewModel;
            }
        }

        public TopMenuViewModel TopMenuViewModel
        {
            get
            {
                return topMenuViewModel;
            }
        }

        public ObjectConfigurationViewModel ObjectConfigurationViewModel
        {
            get
            {
                return objectConfigurationViewModel;
            }
        }

        public EditorViewModel(UIState uiState, TaskFactory taskFactory)
        {
            this.uiState = uiState;
            toolbarViewModel = new ToolbarViewModel();
            mapViewModel = new MapEditorViewModel(toolbarViewModel, taskFactory);
            objectConfigurationViewModel = new ObjectConfigurationViewModel(mapViewModel);
            topMenuViewModel = new TopMenuViewModel();
            background.PlayInLoop();
            ViewConstants.Init();
        }
    }
}
