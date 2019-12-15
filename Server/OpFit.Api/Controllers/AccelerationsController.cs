using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using OpFit.Entities;
using OpFit.Entities.Readings;

namespace OpFit.Api.Controllers
{
    [Route("[controller]")]
    [ApiController]
    public class AccelerationsController : ControllerBase
    {
        private readonly OpFitContext _context;

        public AccelerationsController(OpFitContext context)
        {
            _context = context;
        }

        // GET: api/Accelerations
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Acceleration>>> GetAccelerationReadings()
        {
            return await _context.AccelerationReadings.ToListAsync();
        }

        // GET: api/Accelerations
        [HttpGet("ByUser/{userId}")]
        public async Task<ActionResult<IEnumerable<Acceleration>>> GetAccelerationsByUser(int userId)
        {
            return await _context.AccelerationReadings.Where(a=>a.UserId == userId).ToListAsync();
        }

        // GET: api/Accelerations
        [HttpGet("ByUser/{userId}/Limit/{limit}/Offset/{offsetId?}")]
        public async Task<ActionResult<IEnumerable<Acceleration>>> GetTopAccelerationsByUser(int userId, int limit,int? offsetId = null)
        {
            var result = _context.AccelerationReadings.Where(a => a.UserId == userId);
            if (offsetId == null)
                result = result.OrderByDescending(a=>a.Id).Take(limit).OrderBy(a=>a.Id);
            else
                result = result.Where(a => a.Id > offsetId).Take(limit);

            return await result.ToListAsync();
        }

        // GET: api/Accelerations/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Acceleration>> GetAcceleration(int id)
        {
            var acceleration = await _context.AccelerationReadings.FindAsync(id);

            if (acceleration == null)
            {
                return NotFound();
            }

            return acceleration;
        }


        // PUT: api/Accelerations/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [HttpPut("{id}")]
        public async Task<IActionResult> PutAcceleration(int id, Acceleration acceleration)
        {
            if (id != acceleration.Id)
            {
                return BadRequest();
            }

            _context.Entry(acceleration).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!AccelerationExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Accelerations
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [HttpPost]
        public async Task<ActionResult<Acceleration>> PostAcceleration(Acceleration acceleration)
        {
            _context.AccelerationReadings.Add(acceleration);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetAcceleration", new { id = acceleration.Id }, acceleration);
        }

        // DELETE: api/Accelerations/5
        [HttpDelete("{id}")]
        public async Task<ActionResult<Acceleration>> DeleteAcceleration(int id)
        {
            var acceleration = await _context.AccelerationReadings.FindAsync(id);
            if (acceleration == null)
            {
                return NotFound();
            }

            _context.AccelerationReadings.Remove(acceleration);
            await _context.SaveChangesAsync();

            return acceleration;
        }

        private bool AccelerationExists(int id)
        {
            return _context.AccelerationReadings.Any(e => e.Id == id);
        }
    }
}
