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
    public class HeartRatesController : ControllerBase
    {
        private readonly OpFitContext _context;

        public HeartRatesController(OpFitContext context)
        {
            _context = context;
        }

        // GET: api/HeartRates
        [HttpGet]
        public async Task<ActionResult<IEnumerable<HeartRate>>> GetHeartRateReadings()
        {
            return await _context.HeartRateReadings.ToListAsync();
        }

        // GET: api/HeartRates/5
        [HttpGet("{id}")]
        public async Task<ActionResult<HeartRate>> GetHeartRate(int id)
        {
            var heartRate = await _context.HeartRateReadings.FindAsync(id);

            if (heartRate == null)
            {
                return NotFound();
            }

            return heartRate;
        }

        // PUT: api/HeartRates/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [HttpPut("{id}")]
        public async Task<IActionResult> PutHeartRate(int id, HeartRate heartRate)
        {
            if (id != heartRate.Id)
            {
                return BadRequest();
            }

            _context.Entry(heartRate).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!HeartRateExists(id))
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

        // POST: api/HeartRates
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [HttpPost]
        public async Task<ActionResult<HeartRate>> PostHeartRate(HeartRate heartRate)
        {
            _context.HeartRateReadings.Add(heartRate);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetHeartRate", new { id = heartRate.Id }, heartRate);
        }

        // DELETE: api/HeartRates/5
        [HttpDelete("{id}")]
        public async Task<ActionResult<HeartRate>> DeleteHeartRate(int id)
        {
            var heartRate = await _context.HeartRateReadings.FindAsync(id);
            if (heartRate == null)
            {
                return NotFound();
            }

            _context.HeartRateReadings.Remove(heartRate);
            await _context.SaveChangesAsync();

            return heartRate;
        }

        private bool HeartRateExists(int id)
        {
            return _context.HeartRateReadings.Any(e => e.Id == id);
        }
    }
}
