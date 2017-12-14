using Microsoft.VisualStudio.TestTools.UnitTesting;
using RoRClient.Models.Session;
using RoRClient.Models.Game;
using RoRClient.ViewModels.Editor;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClientTests.ViewModels.Editor
{
    [TestClass]
    public class MapViewModelTest
    {
        Map map;
        Rail expectedRail;
        GameSession editorSession;
        MapViewModel mapViewModel;

        [TestInitialize]
        public void TestInitialize()
        {
            mapViewModel = new MapViewModel(new ToolbarViewModel());
            editorSession = GameSession.GetInstance();
            map = editorSession.Map;
        }

        /* Kann erst genutzt werden wenn der Dispatcher-Thread der Application der Collection nicht mehr adden muss
        [TestMethod]
        public void MapViewModel_PlaceableOnSquareCollectionInitializedTest()
        {
            expectedRail = new Rail(new Guid(), map.GetSquare(0, 0), new RailSection(RailSectionPosition.NORTH, RailSectionPosition.SOUTH));
            map.GetSquare(0, 0).PlaceableOnSquare = expectedRail;
            
            RailViewModel railViewModel = (RailViewModel)mapViewModel.PlaceableOnSquareCollection[0];
            Assert.AreSame(expectedRail, railViewModel.Rail);
        }
       
        [TestMethod]
        public void MapViewModel_SquareViewModelsInitializedTest()
        {
            Assert.AreEqual(map.Squares.Length, mapViewModel.SquareViewModels.Count);
            foreach(SquareViewModel squareViewModel in mapViewModel.SquareViewModels)
            {
                foreach(Square square in map.Squares) {
                    if (squareViewModel.Square.Equals(square))
                    {
                        Assert.AreEqual(squareViewModel.SquarePosX, square.PosX);
                        Assert.AreEqual(squareViewModel.SquarePosY, square.PosY);
                    }
                }
            }
        }
        */
    }
}
