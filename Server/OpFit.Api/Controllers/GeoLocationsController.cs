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
    public class GeoLocationsController : ControllerBase
    {
        private readonly OpFitContext _context;

        public GeoLocationsController(OpFitContext context)
        {
            _context = context;
        }

        // GET: api/GeoLocations
        [HttpGet]
        public async Task<ActionResult<IEnumerable<GeoLocation>>> GetGpsReadings()
        {
            return await _context.GpsReadings.ToListAsync();
        }

        // GET: api/GeoLocations/5
        [HttpGet("{id}")]
        public async Task<ActionResult<GeoLocation>> GetGeoLocation(int id)
        {
            var geoLocation = await _context.GpsReadings.FindAsync(id);

            if (geoLocation == null)
            {
                return NotFound();
            }

            return geoLocation;
        }

        // GET: api/Accelerations
        [HttpGet("ByUser/{userId}")]
        public async Task<ActionResult<IEnumerable<GeoLocation>>> GetLocationsByUser(int userId)
        {
            return await _context.GpsReadings.Where(g => g.UserId == userId).ToListAsync();
        }

        [HttpGet("ByUser/{userId}/Limit/{limit}/Offset/{offsetId?}")]
        public async Task<ActionResult<IEnumerable<GeoLocation>>> GetTopHeartRatesByUser(int userId, int limit, int? offsetId = null)
        {
            var result = _context.GpsReadings.Where(a => a.UserId == userId);
            if (offsetId == null)
                result = result.OrderByDescending(a => a.Id).Take(limit).OrderBy(a => a.Id);
            else
                result = result.Where(a => a.Id > offsetId).Take(limit);

            return await result.ToListAsync();
        }

        // PUT: api/GeoLocations/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [HttpPut("{id}")]
        public async Task<IActionResult> PutGeoLocation(int id, GeoLocation geoLocation)
        {
            if (id != geoLocation.Id)
            {
                return BadRequest();
            }

            _context.Entry(geoLocation).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!GeoLocationExists(id))
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

        // POST: api/GeoLocations
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see https://aka.ms/RazorPagesCRUD.
        [HttpPost]
        public async Task<ActionResult<GeoLocation>> PostGeoLocation(GeoLocation geoLocation)
        {
            _context.GpsReadings.Add(geoLocation);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetGeoLocation", new { id = geoLocation.Id }, geoLocation);
        }

        // DELETE: api/GeoLocations/5
        [HttpDelete("{id}")]
        public async Task<ActionResult<GeoLocation>> DeleteGeoLocation(int id)
        {
            var geoLocation = await _context.GpsReadings.FindAsync(id);
            if (geoLocation == null)
            {
                return NotFound();
            }

            _context.GpsReadings.Remove(geoLocation);
            await _context.SaveChangesAsync();

            return geoLocation;
        }

        private bool GeoLocationExists(int id)
        {
            return _context.GpsReadings.Any(e => e.Id == id);
        }
    }
}
