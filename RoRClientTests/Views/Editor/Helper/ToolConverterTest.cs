using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using RoRClient.Views.Editor.Helper;
using RoRClient.Models.Game;
using System.IO;

namespace RoRClientTests.Views.Editor.Helper
{
    [TestClass]
    public class ToolConverterTest
    {
        [TestMethod]
        public void ToolConvert_ConvertStraightRailNameToRailSection()
        {
            String toolName = "rail_ew";
            RailSection railSection = new RailSection(RailSectionPosition.EAST, RailSectionPosition.WEST);
            Assert.AreEqual(railSection.Node1, ToolConverter.convertToRailSection(toolName).Node1);
            Assert.AreEqual(railSection.Node2, ToolConverter.convertToRailSection(toolName).Node2);

        }

        [TestMethod]
        public void ToolConvert_ConvertCurveRailNameToRailSection()
        {
            String toolName = "railcurve_nw";
            RailSection railSection = new RailSection(RailSectionPosition.NORTH, RailSectionPosition.WEST);
            Assert.AreEqual(railSection.Node1, ToolConverter.convertToRailSection(toolName).Node1);
            Assert.AreEqual(railSection.Node2, ToolConverter.convertToRailSection(toolName).Node2);
        }
    }
}
