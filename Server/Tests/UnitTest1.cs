using Microsoft.EntityFrameworkCore;
using NUnit.Framework;
using OpFit.Entities;

namespace Tests
{
    public class Tests
    {
        OpFitContext _context;
        
        [SetUp]
        public void Setup()
        {
            DbContextOptions<OpFitContext> options = new DbContextOptions<OpFitContext>();            
            _context = new OpFitContext(options);
        }

        [Test]
        public void Test1()
        {
            Assert.Pass();
        }
    }
}