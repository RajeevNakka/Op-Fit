using System;
using System.Collections.Generic;
using System.Text;

namespace OpFit.Entities.Readings
{
    public class Acceleration :IBaseEntity
    {
        #region Base
        public int Id { get; set; }
        public DateTime TimeStamp { get; set; } 
        #endregion

        #region Relation
        public int UserId { get; set; }
        public User User { get; set; } 
        #endregion

        public float X { get; set; }
        public float Y { get; set; }
        public float Z { get; set; }
    }
}
