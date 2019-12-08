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

        public int X { get; set; }
        public int Y { get; set; }
        public int Z { get; set; }
    }
}
