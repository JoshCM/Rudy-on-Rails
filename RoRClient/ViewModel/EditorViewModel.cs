using RoRClient.Model.Models;
using RoRClient.ViewModel.Helper;
using System;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Windows.Input;
using System.Linq;
using System.Collections.Generic;

namespace RoRClient.ViewModel
{
    class EditorViewModel : ViewModelBase
    {
        private const string VIEWMODEL_TYPE_PREFIX = "RoRClient.ViewModel.";
        private const string VIEWMODEL_CLASS_SUFFIX = "ViewModel";

        private ObservableCollection<Square> squares = new ObservableCollection<Square>();
        public ObservableCollection<Square> Squares
        {
            get
            {
                return squares;
            }
        }

        private ObservableCollection<CanvasViewModel> placeableOnSquareCollection = new ObservableCollection<CanvasViewModel>();
        public ObservableCollection<CanvasViewModel> PlaceableOnSquareCollection
        {
            get
            {
                return placeableOnSquareCollection;
            }
        }

        private int mapWidth;
        public int MapWidth
        {
            get { return mapWidth; }
            set
            {
                if (mapWidth != value)
                {
                    mapWidth = value;
                    OnPropertyChanged("MapWidth");
                }
            }
        }

        private int mapHeight;
        public int MapHeight
        {
            get { return mapHeight; }
            set
            {
                if (mapHeight != value)
                {
                    mapHeight = value;
                    OnPropertyChanged("MapHeight");
                }
            }
        }

        public EditorViewModel()
        {
            Map map = new Map();
            foreach(Square square in map.Squares)
            {
                squares.Add(square);
                square.PropertyChanged += OnSquarePropertyChanged;

                if(square.PlaceableOnSquare != null && square.PlaceableOnSquare.GetType() == typeof(Rail))
                {
                    Rail rail = (Rail)square.PlaceableOnSquare;
                    RailViewModel railViewModel = new RailViewModel(rail);
                    placeableOnSquareCollection.Add(railViewModel);
                    rail.PropertyChanged += OnRailPropertyChanged;                    
                }
            }

            MapWidth = map.Squares.GetLength(0) * ViewConstants.SQUARE_DIM;
            MapHeight = map.Squares.GetLength(1) * ViewConstants.SQUARE_DIM;
        }

        private ICommand createRandomRailsCommand;
        public ICommand CreateRandomRailsCommand
        {
            get
            {
                if (createRandomRailsCommand == null)
                {
                    createRandomRailsCommand = new ActionCommand(param => CreateRandomRails());
                }
                return createRandomRailsCommand;
            }
        }

        private void CreateRandomRails()
        {
            Random rand = new Random();
            foreach (Square square in Squares)
            {
                square.PlaceableOnSquare = null;
                if(rand.Next(3) == 0)
                {
                    List<RailSection> railSections = new List<RailSection>();
                    railSections.Add(new RailSection(RailSectionPosition.NORTH, RailSectionPosition.SOUTH));
                    railSections.Add(new RailSection(RailSectionPosition.WEST, RailSectionPosition.SOUTH));
                    railSections.Add(new RailSection(RailSectionPosition.EAST, RailSectionPosition.WEST));
                    railSections.Add(new RailSection(RailSectionPosition.WEST, RailSectionPosition.NORTH));
                    railSections.Add(new RailSection(RailSectionPosition.EAST, RailSectionPosition.SOUTH));
                    railSections.Add(new RailSection(RailSectionPosition.EAST, RailSectionPosition.NORTH));

                    Rail rail = new Rail(square, railSections[rand.Next(railSections.Count)]);
                    square.PlaceableOnSquare = rail;
                }
            }
        }

        private void OnRailPropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            Rail rail = (Rail)sender;

            // ToDo
        }

        private void OnSquarePropertyChanged(object sender, PropertyChangedEventArgs e)
        {
            Square square = (Square)sender;

            // Erstmal Annahme: Ist immer Rail
            if (e.PropertyName == "PlaceableOnSquare")
            {
                PropertyChangedExtendedEventArgs<IPlaceableOnSquare> eventArgs = (PropertyChangedExtendedEventArgs<IPlaceableOnSquare>)e;
                
                if (square.PlaceableOnSquare == null)
                {
                    IModel model = (IModel)eventArgs.OldValue;
                    CanvasViewModel result = placeableOnSquareCollection.Where(x => x.Id == model.Id).First();

                    if (result != null)
                    {
                        placeableOnSquareCollection.Remove(result);
                    }
                }
                else
                {
                    CanvasViewModel viewModel = CreateViewModelForModel(square.PlaceableOnSquare);
                    placeableOnSquareCollection.Add(viewModel);
                }
            }
        }

        private CanvasViewModel CreateViewModelForModel(IModel model)
        {
            Type modelType = model.GetType();
            String viewModelTypeName = VIEWMODEL_TYPE_PREFIX + modelType.Name + VIEWMODEL_CLASS_SUFFIX;
            Type viewModelType = Type.GetType(viewModelTypeName);
            CanvasViewModel viewModel = (CanvasViewModel)Activator.CreateInstance(viewModelType, model);
            return viewModel;
        }
    }
}
