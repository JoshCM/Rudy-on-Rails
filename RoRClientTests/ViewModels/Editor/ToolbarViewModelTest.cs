using Microsoft.VisualStudio.TestTools.UnitTesting;
using RoRClient.ViewModels.Editor;
using System;
using System.Collections.Generic;

namespace RoRClientTests.ViewModels.Editor
{
    [TestClass]
    public class ToolBarViewModelTest
    {
        [TestMethod]
        public void ToolBarViewModel_GenerateToolBarItemsFromFolder()
        {
            String imageFolderPath = "..\\RoRClient\\Resources\\Images\\Tools\\";
            ToolbarViewModel expectedToolbarViewModel = new ToolbarViewModel(imageFolderPath, false);
            expectedToolbarViewModel.ToolItems.Clear();
            expectedToolbarViewModel.ToolItems.Add(new ToolItem("rail_ns", imageFolderPath + "rail_ns.png"));
            expectedToolbarViewModel.ToolItems.Add(new ToolItem("rail_ew", imageFolderPath + "rail_ew.png"));
            expectedToolbarViewModel.ToolItems.Add(new ToolItem("railcurve_se", imageFolderPath + "railcurve_se.png"));
            expectedToolbarViewModel.ToolItems.Add(new ToolItem("railcurve_sw", imageFolderPath + "railcurve_sw.png"));
            expectedToolbarViewModel.ToolItems.Add(new ToolItem("railcurve_ne", imageFolderPath + "railcurve_ne.png"));
            expectedToolbarViewModel.ToolItems.Add(new ToolItem("railcurve_nw", imageFolderPath + "railcurve_nw.png"));
            expectedToolbarViewModel.ToolItems.Add(new ToolItem("trainstation", imageFolderPath + "trainstation.png"));

            ToolbarViewModel actualToolbarViewModel = new ToolbarViewModel(imageFolderPath, true);
            List<String> excpectedToolItemNames = new List<string>();

            foreach (ToolItem expectedToolItem in expectedToolbarViewModel.ToolItems)
            {
                excpectedToolItemNames.Add(expectedToolItem.Name);
            }
            foreach (ToolItem actualToolItem in actualToolbarViewModel.ToolItems)
            {
                Assert.IsTrue(excpectedToolItemNames.Contains(actualToolItem.Name));
            }

            Assert.AreEqual(expectedToolbarViewModel.ToolItems.Count, actualToolbarViewModel.ToolItems.Count);
        }
    }
}
