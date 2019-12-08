using System;
using System.Collections.Generic;
using System.Text;

namespace OpFit.Entities.Readings
{
    public class HeartRate : IBaseEntity
    {
        #region Base
        public int Id { get; set; }
        public DateTime TimeStamp { get; set; }
        #endregion

        #region Relation
        public int UserId { get; set; }
        public User User { get; set; }
        #endregion

        /// <summary>
        /// The number of times the heart beats in the space of a minute.
        /// </summary>
        public uint Rate { get; set; }
    }
}
