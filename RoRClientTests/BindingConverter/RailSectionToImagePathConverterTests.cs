using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using RoRClient.BindingConverter;
using RoRClient.Model.Models;
using RoRClient.ViewModel;

namespace RoRClientTests
{
    [TestClass]
    public class RailSectionToImagePathConverterTests
    {
        [TestMethod]
        public void RailSectionToImagePathConverter_ReturnsNorthSouthImage()
        {
            RailSectionToImagePathConverter converter = new RailSectionToImagePathConverter();
            RailViewModel railViewModel = CreateRailViewModel(RailSectionPosition.NORTH, RailSectionPosition.SOUTH);
            string result = (string)converter.Convert(railViewModel, typeof(string), null, null);
            Assert.AreEqual("..\\..\\Ressourcen\\Images\\rail_ns.png", result);
        }

        [TestMethod]
        public void RailSectionToImagePathConverter_ReturnsSouthNorthImage()
        {
            RailSectionToImagePathConverter converter = new RailSectionToImagePathConverter();
            RailViewModel railViewModel = CreateRailViewModel(RailSectionPosition.SOUTH, RailSectionPosition.NORTH);
            string result = (string)converter.Convert(railViewModel, typeof(string), null, null);
            Assert.AreEqual("..\\..\\Ressourcen\\Images\\rail_ns.png", result);
        }

        [TestMethod]
        public void RailSectionToImagePathConverter_ReturnsNorthEastImage()
        {
            RailSectionToImagePathConverter converter = new RailSectionToImagePathConverter();
            RailViewModel railViewModel = CreateRailViewModel(RailSectionPosition.NORTH, RailSectionPosition.EAST);
            string result = (string)converter.Convert(railViewModel, typeof(string), null, null);
            Assert.AreEqual("..\\..\\Ressourcen\\Images\\railcurve_ne.png", result);
        }

        [TestMethod]
        public void RailSectionToImagePathConverter_ReturnsSouthEastImage()
        {
            RailSectionToImagePathConverter converter = new RailSectionToImagePathConverter();
            RailViewModel railViewModel = CreateRailViewModel(RailSectionPosition.SOUTH, RailSectionPosition.EAST);
            string result = (string)converter.Convert(railViewModel, typeof(string), null, null);
            Assert.AreEqual("..\\..\\Ressourcen\\Images\\railcurve_se.png", result);
        }

        [TestMethod]
        public void RailSectionToImagePathConverter_ReturnsSouthWestImage()
        {
            RailSectionToImagePathConverter converter = new RailSectionToImagePathConverter();
            RailViewModel railViewModel = CreateRailViewModel(RailSectionPosition.SOUTH, RailSectionPosition.WEST);
            string result = (string)converter.Convert(railViewModel, typeof(string), null, null);
            Assert.AreEqual("..\\..\\Ressourcen\\Images\\railcurve_sw.png", result);
        }

        [TestMethod]
        public void RailSectionToImagePathConverter_ReturnsNorthWestImage()
        {
            RailSectionToImagePathConverter converter = new RailSectionToImagePathConverter();
            RailViewModel railViewModel = CreateRailViewModel(RailSectionPosition.NORTH, RailSectionPosition.WEST);
            string result = (string)converter.Convert(railViewModel, typeof(string), null, null);
            Assert.AreEqual("..\\..\\Ressourcen\\Images\\railcurve_nw.png", result);
        }

        [TestMethod]
        public void RailSectionToImagePathConverter_ReturnsEmptyStringWithFalseRailSectionPositions()
        {
            RailSectionToImagePathConverter converter = new RailSectionToImagePathConverter();
            RailViewModel railViewModel = CreateRailViewModel(RailSectionPosition.NORTH, RailSectionPosition.NORTH);
            string result = (string)converter.Convert(railViewModel, typeof(string), null, null);
            Assert.AreEqual("", result);
        }

        private RailViewModel CreateRailViewModel(RailSectionPosition pos1, RailSectionPosition pos2)
        {
            Square square = new Square(0, 0);
            Rail rail = new Rail(square, new RailSection(pos1, pos2));
            return new RailViewModel(rail);
        }
    }
}
