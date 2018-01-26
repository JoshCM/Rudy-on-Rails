using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using RoRClient.Models.Game;

namespace RoRClientTests
{
    [TestClass]
    public class MapTests
    {
        [TestMethod]
        public void ConstructorCreatesAllSquares()
        {
            Map map = new Map(50);
            int counter = 0;
            
            foreach(Square square in map.Squares)
            {
                if(square != null)
                {
                    counter += 1;
                }
            }

            Assert.AreEqual(50, counter);
        }
    }
}
