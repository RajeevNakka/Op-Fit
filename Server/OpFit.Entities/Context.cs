using Microsoft.EntityFrameworkCore;
using OpFit.Entities.Readings;
using System;
using System.Collections.Generic;
using System.Text;

namespace OpFit.Entities
{
    public class OpFitContext : DbContext
    {
        public OpFitContext(DbContextOptions<OpFitContext> options)
            : base(options)
        {
        }

        public DbSet<User> Users { get; set; }
        public DbSet<Acceleration> AccelerationReadings { get; set; }
        public DbSet<HeartRate> HeartRateReadings { get; set; }
        public DbSet<GeoLocation> GpsReadings { get; set; }
    }
}
