using System;
using System.Collections.Generic;
using System.Text;

namespace OpFit.Entities.Readings
{
    public class GeoLocation : IBaseEntity
    {
        #region Base
        public int Id { get; set; }
        public DateTime TimeStamp { get; set; }
        public bool IsAcknowledged { get; set; }
        #endregion

        #region Relation
        public int UserId { get; set; }
        public User User { get; set; }
        #endregion

        public float Longitude { get; set; }
        public float Latitude { get; set; }
    }
}
