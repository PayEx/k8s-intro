using System.Collections.Generic;
using System.Linq;
using CustomerService.Models;
using Microsoft.AspNetCore.Mvc;

namespace CustomerService.Controllers
{
    [Route("api/customers")]
    [ApiController]
    public class CustomerController : ControllerBase
    {
        private readonly CustomerContext _context;

        public CustomerController(CustomerContext context)
        {
            _context = context;

            if (!_context.Customers.Any())
            {
                _context.Customers.Add(new Customer { FirstName = "John", LastName = "Doe" });
                _context.SaveChanges();
            }
        }

        // GET api/customers
        [HttpGet]
        public ActionResult<List<Customer>> GetAll()
        {
            return _context.Customers.ToList();
        }

        // GET api/customers/5
        [HttpGet("{id}")]
        public ActionResult<Customer> GetById(long id)
        {
            var customer = _context.Customers.Find(id);
            if (customer == null)
            {
                return NotFound();
            }
            return customer;
        }

        // POST api/customers
        [HttpPost]
        public Customer Create(Customer customer)
        {
            var createdCustomer = _context.Customers.Add(customer);
            _context.SaveChanges();

            return createdCustomer.Entity;
        }

        // PUT api/customers/5
        [HttpPut("{id}")]
        public IActionResult Put(long id, Customer customer)
        {
            var existing = _context.Customers.Find(id);
            if (existing == null)
            {
                return NotFound();
            }

            existing.FirstName = customer.FirstName;
            existing.LastName = customer.LastName;

            _context.Customers.Update(existing);
            _context.SaveChanges();
            return NoContent();
        }

        // DELETE api/customers/5
        [HttpDelete("{id}")]
        public IActionResult Delete(long id)
        {
            var customer = _context.Customers.Find(id);
            if (customer == null)
            {
                return NotFound();
            }

            _context.Customers.Remove(customer);
            _context.SaveChanges();
            return NoContent();
        }
    }
}
