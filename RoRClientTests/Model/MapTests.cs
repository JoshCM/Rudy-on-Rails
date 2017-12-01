using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using RoRClient.Model.Models;

namespace RoRClientTests
{
    [TestClass]
    public class MapTests
    {
        [TestMethod]
        public void ConstructorCreatesAllSquares()
        {
            Map map = new Map();
            int counter = 0;
            
            foreach(Square square in map.Squares)
            {
                if(square != null)
                {
                    counter += 1;
                }
            }

            Assert.AreEqual(Map.MapSize * Map.MapSize, counter);
        }
    }
}
