using RoRClient.Models.Base;
using RoRClient.ViewModels.Editor;
using RoRClient.ViewModels.Game;
using System;

namespace RoRClient.ViewModels.Helper
{
    public class ViewModelFactory
    {
        private const string VIEWMODEL_TYPE_PREFIX = "RoRClient.ViewModels.Editor.";
        private const string VIEWMODEL_CLASS_SUFFIX = "ViewModel";
        private const string GAMEVIEWMODEL_TYPE_PREFIX = "RoRClient.ViewModels.Game.";
        private const string GAMEVIEWMODEL_CLASS_SUFFIX = "GameViewModel";

        /// <summary>
        /// Erstellt das zu einem Model zugehörige ViewModel und gibt es zurück.
        /// Konvention ist dabei das Suffix 'ViewModel' sowie dass das ViewModel sein Model durch den Konstruktor reingereicht bekommt
        /// Beispiel: Rail -> RailViewModel
        /// </summary>
        /// <param name="model"></param>
        /// <returns>Konkretes ViewModel zu einem Model</returns>
        public EditorCanvasViewModel CreateEditorViewModelForModel(IModel model, MapViewModel mapViewModel)
        {
            Type modelType = model.GetType();
            String viewModelTypeName = VIEWMODEL_TYPE_PREFIX + modelType.Name + VIEWMODEL_CLASS_SUFFIX;
            Type viewModelType = Type.GetType(viewModelTypeName);

            if(viewModelType == null)
            {
                throw new TypeLoadException("There is no ViewModel for Model: " + modelType.Name);
            }

            EditorCanvasViewModel viewModel = (EditorCanvasViewModel)Activator.CreateInstance(viewModelType, model);
            viewModel.MapViewModel = mapViewModel;
            return viewModel;
        }

        public GameCanvasViewModel CreateGameViewModelForModel(IModel model, GameMapViewModel mapViewModel)
        {
            Type modelType = model.GetType();
            String viewModelTypeName = VIEWMODEL_TYPE_PREFIX + modelType.Name + VIEWMODEL_CLASS_SUFFIX;
            Type viewModelType = Type.GetType(viewModelTypeName);

            if (viewModelType == null)
            {
                throw new TypeLoadException("There is no ViewModel for Model: " + modelType.Name);
            }

            GameCanvasViewModel viewModel = (GameCanvasViewModel)Activator.CreateInstance(viewModelType, model);
            viewModel.MapViewModel = mapViewModel;
            return viewModel;
        }



    }
}
