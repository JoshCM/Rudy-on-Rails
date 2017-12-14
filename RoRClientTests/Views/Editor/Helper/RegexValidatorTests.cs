using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using RoRClient.Views.Editor.Helper;

namespace RoRClientTests.Views.Editor.Helper
{
    [TestClass]
    public class RegexValidatorTests
    {
        [DataTestMethod]
        [DataRow("NeueMap")]
        [DataRow("Neue Map")]
        [DataRow("Toll3M4p")]
        public void IsValidFilename_ApprovesSomeExamples(string filename)
        {
            Assert.IsTrue(RegexValidator.IsValidFilename(filename), "Fehlgeschlagen bei filename: " + filename);
        }

        [DataTestMethod]
        [DataRow("Neue/Map")]
        [DataRow("Neue\\Map")]
        [DataRow("TolleM@p")]
        [DataRow("!§$%&")]
        public void IsValidFilename_DisapprovesSomeExamples(string filename)
        {
            Assert.IsFalse(RegexValidator.IsValidFilename(filename), "Fehlgeschlagen bei filename: " + filename);
        }
    }
}
