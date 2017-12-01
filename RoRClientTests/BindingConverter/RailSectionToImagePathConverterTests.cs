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
            RailSection section = new RailSection(RailSectionPosition.NORTH, RailSectionPosition.SOUTH);
            string result = (string)converter.Convert(section, typeof(string), null, null);
            Assert.AreEqual("..\\..\\Ressourcen\\Images\\rail_ns.png", result);
        }

        [TestMethod]
        public void RailSectionToImagePathConverter_ReturnsSouthNorthImage()
        {
            RailSectionToImagePathConverter converter = new RailSectionToImagePathConverter();
            RailSection section = new RailSection(RailSectionPosition.SOUTH, RailSectionPosition.NORTH);
            string result = (string)converter.Convert(section, typeof(string), null, null);
            Assert.AreEqual("..\\..\\Ressourcen\\Images\\rail_ns.png", result);
        }

        [TestMethod]
        public void RailSectionToImagePathConverter_ReturnsNorthEastImage()
        {
            RailSectionToImagePathConverter converter = new RailSectionToImagePathConverter();
            RailSection section = new RailSection(RailSectionPosition.NORTH, RailSectionPosition.EAST);
            string result = (string)converter.Convert(section, typeof(string), null, null);
            Assert.AreEqual("..\\..\\Ressourcen\\Images\\railcurve_ne.png", result);
        }

        [TestMethod]
        public void RailSectionToImagePathConverter_ReturnsSouthEastImage()
        {
            RailSectionToImagePathConverter converter = new RailSectionToImagePathConverter();
            RailSection section = new RailSection(RailSectionPosition.SOUTH, RailSectionPosition.EAST);
            string result = (string)converter.Convert(section, typeof(string), null, null);
            Assert.AreEqual("..\\..\\Ressourcen\\Images\\railcurve_se.png", result);
        }

        [TestMethod]
        public void RailSectionToImagePathConverter_ReturnsSouthWestImage()
        {
            RailSectionToImagePathConverter converter = new RailSectionToImagePathConverter();
            RailSection section = new RailSection(RailSectionPosition.SOUTH, RailSectionPosition.WEST);
            string result = (string)converter.Convert(section, typeof(string), null, null);
            Assert.AreEqual("..\\..\\Ressourcen\\Images\\railcurve_sw.png", result);
        }

        [TestMethod]
        public void RailSectionToImagePathConverter_ReturnsNorthWestImage()
        {
            RailSectionToImagePathConverter converter = new RailSectionToImagePathConverter();
            RailSection section = new RailSection(RailSectionPosition.NORTH, RailSectionPosition.WEST);
            string result = (string)converter.Convert(section, typeof(string), null, null);
            Assert.AreEqual("..\\..\\Ressourcen\\Images\\railcurve_nw.png", result);
        }

        [TestMethod]
        public void RailSectionToImagePathConverter_ReturnsDummyImageWithFalseRailSectionPositions()
        {
            RailSectionToImagePathConverter converter = new RailSectionToImagePathConverter();
            RailSection section = new RailSection(RailSectionPosition.NORTH, RailSectionPosition.NORTH);
            string result = (string)converter.Convert(section, typeof(string), null, null);
            Assert.AreEqual("..\\..\\Ressourcen\\Images\\dummy.png", result);
        }

        [TestMethod]
        public void RailSectionToImagePathConverter_ReturnsDummyImageIfRailSectionIsNull()
        {
            RailSectionToImagePathConverter converter = new RailSectionToImagePathConverter();
            string result = (string)converter.Convert(null, typeof(string), null, null);
            Assert.AreEqual("..\\..\\Ressourcen\\Images\\dummy.png", result);
        }

    }
}
