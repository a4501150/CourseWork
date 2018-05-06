using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using NetworkCommsDotNet;
using NetworkCommsDotNet.Connections;
using NetworkCommsDotNet.Tools;
using SimpleRFDS.Client.Forms;
using DataType;

namespace SimpleRFDS.Client
{
    class ClientRequest
    {

        public String[] ServerList = 
        {
            "127.0.0.1:6666",
            "127.0.0.1:6667",
            "127.0.0.1:6668"
        };

        HashSet<String> UsableServer;

        public ClientRequest()
        {
            UsableServer = new HashSet<string>();
            Console.WriteLine("Cilent Network Component Initialized!");
        }

        public RFDSFile[] GetFileList()
        {
            if (UsableServer.Count == 0)
                return null;

            int index = (new Random()).Next(UsableServer.Count);
            String PickedServer = UsableServer.ElementAt(index);
            byte[] ret = NetworkComms.SendReceiveObject<String, byte[]>("Message", GetIP(PickedServer), GetPort(PickedServer), "RFDSFileArray", 1000, "GetFileList");
            RFDSFile[] ret2 = (RFDSFile[])MainForm.ByteArrayToObject(ret);
            Console.WriteLine("GetFileList Request Sent");
            return ret2;
        }

        public bool OpenFile(String md5)
        {
            if (UsableServer.Count == 0)
                return false;

            int index = (new Random()).Next(UsableServer.Count);
            String PickedServer = UsableServer.ElementAt(index);
            NetworkComms.SendObject<String>("OpenFile", GetIP(PickedServer), GetPort(PickedServer), md5);
            Console.WriteLine("OpenFile Request Sent");
            return true;
        }

        public bool CloseFile(String md5)
        {
            if (UsableServer.Count == 0)
                return false;

            int index = (new Random()).Next(UsableServer.Count);
            String PickedServer = UsableServer.ElementAt(index);
            NetworkComms.SendObject<String>("CloseFile", GetIP(PickedServer), GetPort(PickedServer), md5);
            Console.WriteLine("CloseFile Request Sent");
            return true;
        }

        public String ReadFile(String md5)
        {
            if (UsableServer.Count == 0)
                return null;

            int index = (new Random()).Next(UsableServer.Count);
            String PickedServer = UsableServer.ElementAt(index);
            var ret = NetworkComms.SendReceiveObject<String, byte[]>("ReadFile", GetIP(PickedServer), GetPort(PickedServer), "ExistFileData", 1000, md5);
            String str;
            if (ret != null)
                str = System.Text.Encoding.UTF8.GetString(ret);
            else
                str = "";

            Console.WriteLine("ReadFile Request Sent");
            return str;

        }

        public String AppendFile(String md5, String content)
        {

            if (UsableServer.Count == 0)
                return "";

            int index = (new Random()).Next(UsableServer.Count);
            String PickedServer = UsableServer.ElementAt(index);

            String SentShit = md5 + "<MySplit>" + content;
            var newmd5 = NetworkComms.SendReceiveObject<String, String>("AppendFile", GetIP(PickedServer), GetPort(PickedServer), "OperationDone", 1000, SentShit);
            Console.WriteLine("AppendFile Request Sent");
            return newmd5;
        }

        public String WriteFile(String md5, String content)
        {
            if (UsableServer.Count == 0)
                return "";

            int index = (new Random()).Next(UsableServer.Count);
            String PickedServer = UsableServer.ElementAt(index);

            String SentShit = md5 + "<MySplit>" + content;
            var newmd5 = NetworkComms.SendReceiveObject<String, String>("WriteFile", GetIP(PickedServer), GetPort(PickedServer), "OperationDone", 1000, SentShit);
            Console.WriteLine("WriteFile Request Sent");
            return newmd5;
        }

        public String QueryFileLock(String md5)
        {
            if (UsableServer.Count == 0)
                return "";

            int index = (new Random()).Next(UsableServer.Count);
            String PickedServer = UsableServer.ElementAt(index);

           
            var status = NetworkComms.SendReceiveObject<String, String>("QueryFileLock", GetIP(PickedServer), GetPort(PickedServer), "OperationDone", 1000, md5);
            Console.WriteLine("QueryFileLock Request Sent");
            return status;

        }

        public String SetupFileLock(String md5)
        {
            if (UsableServer.Count == 0)
                return "";

            int index = (new Random()).Next(UsableServer.Count);
            String PickedServer = UsableServer.ElementAt(index);
            var ret = NetworkComms.SendReceiveObject<String, String>("SetupFileLock", GetIP(PickedServer), GetPort(PickedServer), "OperationDone", 1000, md5);
            Console.WriteLine("SetupFileLock Request Sent");
            return ret;

        }

        public String SendReceiveMessage(String sendMSG, String ExpectedRerturnTypeStr, String TargetHost)
        {
            return NetworkComms.SendReceiveObject<String, String>("Message", GetIP(TargetHost), GetPort(TargetHost), ExpectedRerturnTypeStr, 1000, sendMSG);
        }

        public bool SendFileObject(RFDSFile fo)
        {
            if (UsableServer.Count == 0)
                return false;

            int index = (new Random()).Next(UsableServer.Count);
            String PickedServer = UsableServer.ElementAt(index);
            try
            {
                //NetworkComms.SendObject<byte[]>("NewFileMetaData", GetIP(PickedServer), GetPort(PickedServer), MainForm.ObjectToByteArray(fo));
                NetworkComms.SendReceiveObject<byte[], String>("NewFileMetaData", GetIP(PickedServer), GetPort(PickedServer), "OperationDone", 1000, MainForm.ObjectToByteArray(fo));
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
                return false;
            }

            return true;
        }

        public bool SendFileBinary(string path)
        {
            byte[] fileBin = MainForm.GetFileBytes(path);

            if (UsableServer.Count == 0)
                return false;

            int index = (new Random()).Next(UsableServer.Count);
            String PickedServer = UsableServer.ElementAt(index);
            try
            {
                NetworkComms.SendObject<byte[]>("NewFileData", GetIP(PickedServer), GetPort(PickedServer), fileBin);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
                return false;
            }

            return true;
        }

        String GetIP(String host)
        {
            return host.Split(':')[0];
        }

        int GetPort(String host)
        {
            int.TryParse(host.Split(':')[1], out int port);
            return port;
        }

        public int GetAliveServerCount()
        {

            int aliveCount = 0;

            UsableServer.Clear();

            foreach (String conn in ServerList)
            {
                String ret = "";
                try
                {
                    ret = SendReceiveMessage("HeartBeat", "HeartBeat", conn);
                }
                catch (Exception)
                {
                    continue;
                }
                if (ret == "alive")
                {
                    aliveCount++;
                    UsableServer.Add(conn);
                }
                    
            }

            return aliveCount;
        }

    }
}
