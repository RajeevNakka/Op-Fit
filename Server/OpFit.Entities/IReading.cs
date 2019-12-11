using System;
using System.Collections.Generic;
using System.Text;

namespace OpFit.Entities
{
    public interface IReading : IBaseEntity
    {
        public bool IsAcknowledged { get; set; }
    }
}
