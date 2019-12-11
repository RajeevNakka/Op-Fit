using System;
using System.Collections.Generic;
using System.Text;

namespace OpFit.Entities
{
    public interface IBaseEntity
    {
        public int Id { get; set; }
        public DateTime TimeStamp { get; set; }
    }
}
