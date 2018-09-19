using System;
using System.Drawing.Printing;
using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Hosting;

namespace CustomerService
{
    public class Program
    {
        public static void Main(string[] args)
        {
            Console.WriteLine("Starting the customer service...");
            CreateWebHostBuilder(args).Build().Run();
        }

        public static IWebHostBuilder CreateWebHostBuilder(string[] args) =>
            WebHost.CreateDefaultBuilder(args)
                .UseStartup<Startup>();
    }
}
