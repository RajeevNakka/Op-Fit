using OpFit.Entities.Readings;
using System;
using System.Collections.Generic;

namespace OpFit.Entities
{
    public enum Gender { Male, Female, Trans}
    public class User : IBaseEntity
    {
        #region Base
        public int Id { get; set; }
        public DateTime TimeStamp { get; set; }
        #endregion

        public string UserName { get; set; }
        public string Password { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public uint Age { get; set; }
        public Gender Gender { get; set; }
        public string DeviceId { get; set; }
        public bool? IsWearingHelmet { get; set; }
        public bool? IsAcknowledged { get; set; }

        #region Relation/Navigation
        public List<Acceleration> AccelerationReadings { get; } = new List<Acceleration>();
        public List<HeartRate> HeartRateReadings { get; } = new List<HeartRate>();
        public List<GeoLocation> GpsReadings { get; } = new List<GeoLocation>(); 
        #endregion
    }
}
