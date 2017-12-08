﻿using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using RoRClient.ViewModels.Helper;
using RoRClient.Models.Game;
using RoRClient.ViewModels.Editor;
using RoRClient.ViewModels;

namespace RoRClientTests.ViewModels.Helper
{
    [TestClass]
    public class ViewModelFactoryTests
    {
        [TestMethod]
        public void ViewModelFactory_CreatesRailViewModelFromRail()
        {
            ViewModelFactory viewModelFactory = new ViewModelFactory();
            Square square = new Square(0, 0);
            Rail rail = new Rail(Guid.NewGuid(), square, new RailSection(RailSectionPosition.NORTH, RailSectionPosition.SOUTH));

            RailViewModel railViewModel = (RailViewModel)viewModelFactory.CreateViewModelForModel(rail);

            Assert.IsNotNull(railViewModel);
            Assert.AreEqual(rail, railViewModel.Rail);
        }

        [TestMethod]
        [ExpectedException(typeof(TypeLoadException))]
        public void ViewModelFactory_ThrowsTypeLoadExceptionWithFalseModel()
        {
            ViewModelFactory viewModelFactory = new ViewModelFactory();
            Player square = new Player(new Guid(), "testplayer");

            CanvasViewModel canvasViewModel = (CanvasViewModel)viewModelFactory.CreateViewModelForModel(square);
        }
    }
}
