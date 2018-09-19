using Microsoft.EntityFrameworkCore;

namespace CustomerService.Models
{
    public class CustomerContext : DbContext
    {
        public CustomerContext(DbContextOptions<CustomerContext> options) : base(options)
        {
        }
        
        public DbSet<Customer> Customers { get; set; }
    }
}