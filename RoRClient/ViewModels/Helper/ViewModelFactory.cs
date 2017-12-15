using RoRClient.Models.Base;
using RoRClient.ViewModels.Editor;
using System;

namespace RoRClient.ViewModels.Helper
{
    public class ViewModelFactory
    {
        private const string VIEWMODEL_TYPE_PREFIX = "RoRClient.ViewModels.Editor.";
        private const string VIEWMODEL_CLASS_SUFFIX = "ViewModel";

        /// <summary>
        /// Erstellt das zu einem Model zugehörige ViewModel und gibt es zurück.
        /// Konvention ist dabei das Suffix 'ViewModel' sowie dass das ViewModel sein Model durch den Konstruktor reingereicht bekommt
        /// Beispiel: Rail -> RailViewModel
        /// </summary>
        /// <param name="model"></param>
        /// <returns>Konkretes ViewModel zu einem Model</returns>
        public EditorCanvasViewModel CreateViewModelForModel(IModel model, MapViewModel mapViewModel)
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
    }
}
