using RoRClient.Models.Base;
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
        public CanvasViewModel CreateViewModelForModel(IModel model)
        {
            Type modelType = model.GetType();
            String viewModelTypeName = VIEWMODEL_TYPE_PREFIX + modelType.Name + VIEWMODEL_CLASS_SUFFIX;
            Type viewModelType = Type.GetType(viewModelTypeName);

            if(viewModelType == null)
            {
                throw new TypeLoadException("There is no ViewModel for Model: " + modelType.Name);
            }

            CanvasViewModel viewModel = (CanvasViewModel)Activator.CreateInstance(viewModelType, model);
            return viewModel;
        }
    }
}
