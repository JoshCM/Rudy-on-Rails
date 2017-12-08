using Microsoft.VisualStudio.TestTools.UnitTesting;
using RoRClient.Models.Editor;
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
        [TestMethod]
        public void MapViewModel_InitializeTest()
        {
            Map map = EditorSession.GetInstance().Map;
            Rail expectedRail = new Rail(new Guid(), map.GetSquare(0, 0), new RailSection(RailSectionPosition.NORTH, RailSectionPosition.SOUTH));
            map.GetSquare(0, 0).PlaceableOnSquare = expectedRail;
            MapViewModel mapViewModel = new MapViewModel();
            RailViewModel railViewModel = (RailViewModel)mapViewModel.PlaceableOnSquareCollection[0];
            Assert.AreSame(expectedRail, railViewModel.Rail);
        }
    }
}
