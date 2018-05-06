using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SimpleRFDS.FileServer.FileOperation;
using SimpleRFDS.FileServer.Network;


namespace SimpleRFDS.FileServer
{
    class Program
    {
        static void Main(string[] args)
        {
            int x = 0;
            if(args.Length == 0)
            {
                Console.WriteLine("Please Enter A Port Or Run This Application with >> app.exe <port> << port is an int");  
                int.TryParse(Console.ReadLine(), out x);
            } else
                int.TryParse(args[0], out  x);

            ServerNode sn = new ServerNode(x);

            //Let the user close the server
            Console.WriteLine("\nPress any key to close server.");
            Console.ReadKey(true);

            //We have used NetworkComms so we should ensure that we correctly call shutdown
            sn.Shutdown();
        }
    }
}
