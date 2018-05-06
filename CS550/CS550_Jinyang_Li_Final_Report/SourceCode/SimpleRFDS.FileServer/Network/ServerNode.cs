using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using NetworkCommsDotNet;
using NetworkCommsDotNet.Connections;
using System.Net;
using SimpleRFDS.FileServer.FileOperation;
using DataType;

namespace SimpleRFDS.FileServer.Network
{
    class ServerNode
    {
        /// <summary>
        /// Private object used to ensure thread safety
        /// </summary>
        object syncRoot = new object();
        object syncRootServer = new object();

        private int port;

        public String[] ServerList =
        {
            "127.0.0.1:6666",
            "127.0.0.1:6667",
            "127.0.0.1:6668"
        };

        HashSet<String> UsableServer;

        public static Dictionary<string, RFDSFile> FileObjectMap = new Dictionary<string, RFDSFile>();

        public ServerNode(int port)
        {
            Initialize(port);
            this.port = port;
            UsableServer = new HashSet<string>();
        }

        void Initialize(int port)
        {
            //Client APi
            NetworkComms.AppendGlobalIncomingPacketHandler<string>("Message", IncomingOperationOrder);
            NetworkComms.AppendGlobalIncomingPacketHandler<string>("QueryFileLock", IncomingQueryFileLock);
            NetworkComms.AppendGlobalIncomingPacketHandler<byte[]>("NewFileMetaData", IncomingFileMetaData);
            NetworkComms.AppendGlobalIncomingPacketHandler<byte[]>("NewFileData", IncomingFileData);

            NetworkComms.AppendGlobalIncomingPacketHandler<string>("OpenFile", IncomingOpenFile);
            NetworkComms.AppendGlobalIncomingPacketHandler<string>("CloseFile", IncomingCloseFile);
            NetworkComms.AppendGlobalIncomingPacketHandler<string>("ReadFile", IncomingReadFile);
            NetworkComms.AppendGlobalIncomingPacketHandler<string>("WriteFile", IncomingWriteFile);
            NetworkComms.AppendGlobalIncomingPacketHandler<string>("AppendFile", IncomingAppendFile);
            NetworkComms.AppendGlobalIncomingPacketHandler<string>("SetupFileLock", IncomingSetupFileLock);

            //ServerApi
            NetworkComms.AppendGlobalIncomingPacketHandler<byte[]>("ServerNewFileMetaData", ServerIncomingFileMetaData);
            NetworkComms.AppendGlobalIncomingPacketHandler<byte[]>("ServerNewFileData", ServerIncomingFileData);

            NetworkComms.AppendGlobalIncomingPacketHandler<string>("ServerOpenFile", ServerIncomingOpenFile);
            NetworkComms.AppendGlobalIncomingPacketHandler<string>("ServerCloseFile", ServerIncomingCloseFile);
            NetworkComms.AppendGlobalIncomingPacketHandler<string>("ServerReadFile", ServerIncomingReadFile);
            NetworkComms.AppendGlobalIncomingPacketHandler<string>("ServerWriteFile", ServerIncomingWriteFile);
            NetworkComms.AppendGlobalIncomingPacketHandler<string>("ServerAppendFile", ServerIncomingAppendFile);
            NetworkComms.AppendGlobalIncomingPacketHandler<string>("ServerSetupFileLock", ServerIncomingSetupFileLock);

            Connection.StartListening(ConnectionType.TCP, new IPEndPoint(IPAddress.Any, port));
            Console.WriteLine("Server Listening at " + port);
        }


        internal void Shutdown()
        {
            NetworkComms.Shutdown();
        }

        private void ServerIncomingSetupFileLock(PacketHeader packetHeader, Connection connection, string incomingObject)
        {
            try
            {
                FileObjectMap[incomingObject].IsWritting = true;
                Console.WriteLine("[Sync]Recieved SetupFileLock For File [{0}] From Server [{1}]", FileObjectMap[incomingObject].FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());
            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                Console.WriteLine("Exception - " + ex.ToString());
            }
        }

        private void ServerIncomingFileMetaData(PacketHeader packetHeader, Connection connection, byte[] incomingObject)
        {
            try
            {
                //Perform this in a thread safe way
                //lock (syncRootServer)
                //{
                //    RFDSFile fileObject;
                //    fileObject = (RFDSFile)LocalFileServer.ByteArrayToObject(incomingObject);
                //    if (FileObjectMap.TryGetValue(fileObject.FileMD5, out RFDSFile fo))
                //        FileObjectMap[fileObject.FileMD5] = fileObject;
                //    else
                //        FileObjectMap.Add(fileObject.FileMD5, fileObject);

                //    Console.WriteLine("Recieved New File MetaData From Server: " + fileObject.FileName);

                //}

                RFDSFile fileObject;
                fileObject = (RFDSFile)LocalFileServer.ByteArrayToObject(incomingObject);
                if (FileObjectMap.TryGetValue(fileObject.FileMD5, out RFDSFile fo))
                    FileObjectMap[fileObject.FileMD5] = fileObject;
                else
                    FileObjectMap.Add(fileObject.FileMD5, fileObject);
                Console.WriteLine("[Sync]Recieved New File MetaData [{0}] From Server [{1}]", fileObject.FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());



            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                Console.WriteLine("Exception - " + ex.ToString());
            }
        }

        private void ServerIncomingFileData(PacketHeader packetHeader, Connection connection, byte[] incomingObject)
        {
            try
            {
                //Perform this in a thread safe way
                //lock (syncRootServer)
                //{
                //    LocalFileServer.CreateTempFile();
                //    LocalFileServer.AppendFile("temp", incomingObject);
                //    String md5 = LocalFileServer.GetFileMD5("temp");
                //    RFDSFile fo;
                //    if ((fo = GetFileObject(md5)) != null)
                //    {
                //        LocalFileServer.CreateFile(fo.FileName, incomingObject);
                //        Console.WriteLine("Recieved New File Binary Data From Server: " + fo.FileName);
                //    }
                //}

                LocalFileServer.CreateTempFile();
                LocalFileServer.AppendFile("temp", incomingObject);
                String md5 = LocalFileServer.GetFileMD5("temp");
                RFDSFile fo;
                if ((fo = GetFileObject(md5)) != null)
                {
                    LocalFileServer.CreateFile(fo.FileName, incomingObject);
                    Console.WriteLine("[Sync]Recieved New File Binary Data [{0}] From Server [{1}]", fo.FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());
                }



            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                Console.WriteLine("Exception - " + ex.ToString());
            }
        }

        private void ServerIncomingAppendFile(PacketHeader packetHeader, Connection connection, string incomingObject)
        {
            try
            {
                //Perform this in a thread safe way
                //lock (syncRoot)
                //{

                //}
                String md5 = incomingObject.Split(new string[] { "<MySplit>" }, StringSplitOptions.None)[0];
                String content = incomingObject.Split(new string[] { "<MySplit>" }, StringSplitOptions.None)[1];
                var fo = FileObjectMap[md5];
                FileObjectMap.Remove(md5);

                LocalFileServer.AppendFile(fo.FileName, Encoding.UTF8.GetBytes(content));
                fo.FileMD5 = LocalFileServer.GetFileMD5(fo.FileName);
                fo.FileSize = LocalFileServer.GetFileSize(fo.FileName);

                FileObjectMap.Add(fo.FileMD5, fo);

                Console.WriteLine("[Sync]Recieved New Append Request For File [{0}] From Server [{1}]", fo.FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());

            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                Console.WriteLine("Exception - " + ex.ToString());
            }
        }

        private void ServerIncomingWriteFile(PacketHeader packetHeader, Connection connection, string incomingObject)
        {
            try
            {
                //Perform this in a thread safe way
                //lock (syncRoot)
                //{
                //    String md5 = incomingObject.Split(new string[] { "<MySplit>" }, StringSplitOptions.None)[0];
                //    String content = incomingObject.Split(new string[] { "<MySplit>" }, StringSplitOptions.None)[1];
                //    var fo = FileObjectMap[md5];
                //    FileObjectMap.Remove(md5);

                //    LocalFileServer.RewnewFile(fo.FileName, Encoding.UTF8.GetBytes(content));
                //    fo.FileMD5 = LocalFileServer.GetFileMD5(fo.FileName);
                //    fo.FileSize = LocalFileServer.GetFileSize(fo.FileName);

                //    FileObjectMap.Add(fo.FileMD5, fo);

                //    Console.WriteLine("[Sync]Recieved New Write Request For File [{0}] From Server [{1}]", fo.FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());
                //}

                String md5 = incomingObject.Split(new string[] { "<MySplit>" }, StringSplitOptions.None)[0];
                String content = incomingObject.Split(new string[] { "<MySplit>" }, StringSplitOptions.None)[1];
                var fo = FileObjectMap[md5];
                FileObjectMap.Remove(md5);

                LocalFileServer.RewnewFile(fo.FileName, Encoding.UTF8.GetBytes(content));
                fo.FileMD5 = LocalFileServer.GetFileMD5(fo.FileName);
                fo.FileSize = LocalFileServer.GetFileSize(fo.FileName);

                FileObjectMap.Add(fo.FileMD5, fo);

                Console.WriteLine("[Sync]Recieved New Write Request For File [{0}] From Server [{1}]", fo.FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());

            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                Console.WriteLine("Exception - " + ex.ToString());
            }
        }

        private void ServerIncomingReadFile(PacketHeader packetHeader, Connection connection, string incomingObject)
        {
            try
            {
                ////Perform this in a thread safe way
                //lock (syncRoot)
                //{
                //    FileObjectMap[incomingObject].IsReading = true;
                //}
                FileObjectMap[incomingObject].IsReading = true;
                Console.WriteLine("[Sync]Recieved New Read Request For File [{0}] From Server [{1}]", FileObjectMap[incomingObject].FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());
            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                Console.WriteLine("Exception - " + ex.ToString());
            }
        }

        private void ServerIncomingCloseFile(PacketHeader packetHeader, Connection connection, string incomingObject)
        {
            try
            {
                //Perform this in a thread safe way
                //lock (syncRoot)
                //{
                //    FileObjectMap[incomingObject].IsReading = false;
                //    FileObjectMap[incomingObject].IsWritting = false;
                //    FileObjectMap[incomingObject].IsOpened = false;
                //}

                FileObjectMap[incomingObject].IsReading = false;
                FileObjectMap[incomingObject].IsWritting = false;
                FileObjectMap[incomingObject].IsOpened = false;
                Console.WriteLine("[Sync]Recieved New Close Request For File [{0}] From Server [{1}]", FileObjectMap[incomingObject].FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());
            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                Console.WriteLine("Exception - " + ex.ToString());
            }
        }

        private void ServerIncomingOpenFile(PacketHeader packetHeader, Connection connection, string incomingObject)
        {
            try
            {
                ////Perform this in a thread safe way
                //lock (syncRoot)
                //{
                //    FileObjectMap[incomingObject].IsOpened = true;
                //}

                FileObjectMap[incomingObject].IsOpened = true;
                Console.WriteLine("[Sync]Recieved New Open Request For File [{0}] From Server [{1}]", FileObjectMap[incomingObject].FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());
            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                Console.WriteLine("Exception - " + ex.ToString());
            }
        }

        /// <summary>
        /// Handles an incoming packet of type 'OpenFile'
        /// </summary>
        /// <param name="header">Header associated with incoming packet</param>
        /// <param name="connection">The connection associated with incoming packet</param>
        /// <param name="fileMD5">The incoming file MD5</param>
        private void IncomingOpenFile(PacketHeader header, Connection connection, String fileMD5)
        {
            try
            {
                ////Perform this in a thread safe way
                //lock (syncRoot)
                //{
                //    FileObjectMap[fileMD5].IsOpened = true;
                //    BroadCastToOtherServer<String>("OpenFile", fileMD5);
                //}

                FileObjectMap[fileMD5].IsOpened = true;
                BroadCastToOtherServer<String>("OpenFile", fileMD5);
                Console.WriteLine("[Reqeust]Recieved New Open Request For File [{0}] From Client [{1}]", FileObjectMap[fileMD5].FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());
            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                Console.WriteLine("Exception - " + ex.ToString());
            }
        }

        /// <summary>
        /// Handles an incoming packet of type 'CloseFile'
        /// </summary>
        /// <param name="header">Header associated with incoming packet</param>
        /// <param name="connection">The connection associated with incoming packet</param>
        /// <param name="fileMD5">The incoming file MD5</param>
        private void IncomingCloseFile(PacketHeader header, Connection connection, String fileMD5)
        {
            try
            {
                ////Perform this in a thread safe way
                //lock (syncRoot)
                //{
                //    FileObjectMap[fileMD5].IsOpened = false;
                //    FileObjectMap[fileMD5].IsWritting = false;
                //    FileObjectMap[fileMD5].IsReading = false;
                //    BroadCastToOtherServer<String>("CloseFile", fileMD5);
                //}

                FileObjectMap[fileMD5].IsOpened = false;
                FileObjectMap[fileMD5].IsWritting = false;
                FileObjectMap[fileMD5].IsReading = false;
                BroadCastToOtherServer<String>("CloseFile", fileMD5);
                Console.WriteLine("[Reqeust]Recieved New Close Request For File [{0}] From Client [{1}]", FileObjectMap[fileMD5].FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());
            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                Console.WriteLine("Exception - " + ex.ToString());
            }
        }

        /// <summary>
        /// Handles an incoming packet of type 'ReadFile'
        /// </summary>
        /// <param name="header">Header associated with incoming packet</param>
        /// <param name="connection">The connection associated with incoming packet</param>
        /// <param name="fileMD5">The incoming file MD5</param>
        private void IncomingReadFile(PacketHeader header, Connection connection, String fileMD5)
        {
            try
            {
                //Perform this in a thread safe way
                //lock (syncRoot)
                //{
                //    if (FileObjectMap[fileMD5].IsOpened)
                //    {
                //        if(FileObjectMap[fileMD5].IsWritting == false)
                //        {
                //            connection.SendObject<byte[]>("ExistFileData", LocalFileServer.GetFileBytes(FileObjectMap[fileMD5].FileName));
                //            FileObjectMap[fileMD5].IsReading = true;
                //            BroadCastToOtherServer<RFDSFile>("ReadFile", FileObjectMap[fileMD5]);
                //        }
                //        else
                //            connection.SendObject<byte[]>("ExistFileData", Encoding.UTF8.GetBytes("File Is Being Wirrten"));
                //    }
                //    else
                //    {
                //        connection.SendObject<byte[]>("ExistFileData", Encoding.UTF8.GetBytes("File Is Not Opended"));
                //    }


                //}

                if (FileObjectMap[fileMD5].IsOpened)
                {
                    if (FileObjectMap[fileMD5].IsWritting == false)
                    {
                        connection.SendObject<byte[]>("ExistFileData", LocalFileServer.GetFileBytes(FileObjectMap[fileMD5].FileName));
                        FileObjectMap[fileMD5].IsReading = true;
                        BroadCastToOtherServer<String>("ReadFile", FileObjectMap[fileMD5].FileMD5);
                    }
                    else
                        connection.SendObject<byte[]>("ExistFileData", Encoding.UTF8.GetBytes("File Is Being Wirrten"));
                }
                else
                {
                    connection.SendObject<byte[]>("ExistFileData", Encoding.UTF8.GetBytes("File Is Not Opended"));
                }
                Console.WriteLine("[Reqeust]Recieved New Reading Request For File [{0}] From Client [{1}]", FileObjectMap[fileMD5].FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());
            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                Console.WriteLine("Exception - " + ex.ToString());
            }
        }

        /// <summary>
        /// Handles an incoming packet of type 'WriteFile'
        /// </summary>
        /// <param name="header">Header associated with incoming packet</param>
        /// <param name="connection">The connection associated with incoming packet</param>
        /// <param name="fileMD5">The incoming original file MD5 and new Content</param>
        private void IncomingWriteFile(PacketHeader header, Connection connection, String md5Content)
        {
            try
            {
                String md5 = md5Content.Split(new string[] { "<MySplit>" }, StringSplitOptions.None)[0];
                String content = md5Content.Split(new string[] { "<MySplit>" }, StringSplitOptions.None)[1];

                var fo = FileObjectMap[md5];

                if (!fo.IsOpened)
                {
                    connection.SendObject<String>("OperationDone", "file_closed");
                    return;
                }

                FileObjectMap.Remove(fo.FileMD5);

                LocalFileServer.RewnewFile(fo.FileName, Encoding.UTF8.GetBytes(content));
                fo.FileMD5 = LocalFileServer.GetFileMD5(fo.FileName);
                fo.FileSize = LocalFileServer.GetFileSize(fo.FileName);

                FileObjectMap.Add(fo.FileMD5, fo);
                BroadCastToOtherServer<String>("WriteFile", md5Content);
                connection.SendObject<String>("OperationDone", fo.FileMD5);
                Console.WriteLine("[Reqeust]Recieved New WriteFile Request For File [{0}] From Client [{1}]", fo.FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());

            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                Console.WriteLine("Exception - In Client Request To Write\n" + ex.ToString());
            }
        }

        /// <summary>
        /// Handles an incoming packet of type 'AppendFile'
        /// </summary>
        /// <param name="header">Header associated with incoming packet</param>
        /// <param name="connection">The connection associated with incoming packet</param>
        /// <param name="fileMD5">The incoming file MD5 and Content</param>
        private void IncomingAppendFile(PacketHeader header, Connection connection, String md5Content)
        {
            try
            {
                //Perform this in a thread safe way
                //lock (syncRoot)
                //{
                //    String md5 = md5Content.Split(new string[] { "<MySplit>" }, StringSplitOptions.None)[0];
                //    String content = md5Content.Split(new string[] { "<MySplit>" }, StringSplitOptions.None)[1];
                //    var fo = FileObjectMap[md5];
                //    FileObjectMap.Remove(md5);

                //    LocalFileServer.AppendFile(fo.FileName, Encoding.UTF8.GetBytes(content));
                //    fo.FileMD5 = LocalFileServer.GetFileMD5(fo.FileName);
                //    fo.FileSize = LocalFileServer.GetFileSize(fo.FileName);

                //    FileObjectMap.Add(fo.FileMD5, fo);
                //    connection.SendObject<String>("OperationDone", fo.FileMD5);
                //}

                String md5 = md5Content.Split(new string[] { "<MySplit>" }, StringSplitOptions.None)[0];
                String content = md5Content.Split(new string[] { "<MySplit>" }, StringSplitOptions.None)[1];
    
                var fo = FileObjectMap[md5];

                if(fo.IsReading==true || fo.IsWritting == true)
                {
                    connection.SendObject<String>("OperationDone", "file_locked");
                    return;
                }

                FileObjectMap.Remove(md5);

                LocalFileServer.AppendFile(fo.FileName, Encoding.UTF8.GetBytes(content));
                fo.FileMD5 = LocalFileServer.GetFileMD5(fo.FileName);
                fo.FileSize = LocalFileServer.GetFileSize(fo.FileName);

                FileObjectMap.Add(fo.FileMD5, fo);
                BroadCastToOtherServer<string>("AppendFile", md5Content);
                connection.SendObject<String>("OperationDone", fo.FileMD5);
                Console.WriteLine("[Reqeust]Recieved New Append Request For File [{0}] From Client [{1}]", fo.FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());
            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                Console.WriteLine("Exception - " + ex.ToString());
            }
        }

        /// <summary>
        /// Handles an incoming packet of type 'RFDSFile'
        /// </summary>
        /// <param name="header">Header associated with incoming packet</param>
        /// <param name="connection">The connection associated with incoming packet</param>
        /// <param name="fileObject">The incoming file object</param>
        private void IncomingFileMetaData(PacketHeader header, Connection connection, byte[] fileObjectBytes)
        {
            try
            {
                //Perform this in a thread safe way
                lock (syncRoot)
                {
                    RFDSFile fileObject;
                    fileObject = (RFDSFile)LocalFileServer.ByteArrayToObject(fileObjectBytes);
                    if (FileObjectMap.TryGetValue(fileObject.FileMD5, out RFDSFile fo))
                        FileObjectMap[fileObject.FileMD5] = fileObject;
                    else
                        FileObjectMap.Add(fileObject.FileMD5, fileObject);

                    BroadCastToOtherServer<byte[]>("NewFileMetaData", fileObjectBytes);
                    connection.SendObject("OperationDone");
                    Console.WriteLine("[Reqeust]Recieved New FileMetaData For File [{0}] From Client [{1}]", fileObject.FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());
                }



            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                Console.WriteLine("Exception - " + ex.ToString());
            }
        }

        /// <summary>
        /// Handles an incoming packet of type 'byte[]'
        /// </summary>
        /// <param name="header">Header associated with incoming packet</param>
        /// <param name="connection">The connection associated with incoming packet</param>
        /// <param name="data">The incoming data</param>
        private void IncomingFileData(PacketHeader header, Connection connection, byte[] data)
        {
            try
            {
                //Perform this in a thread safe way
                lock (syncRoot)
                {
                    LocalFileServer.CreateTempFile();
                    LocalFileServer.AppendFile("temp", data);
                    String md5 = LocalFileServer.GetFileMD5("temp");
                    RFDSFile fo;
                    if ((fo = GetFileObject(md5)) != null)
                    {
                        LocalFileServer.CreateFile(fo.FileName, data);
                        BroadCastToOtherServer<byte[]>("NewFileData", data);
                        Console.WriteLine("[Reqeust]Recieved New File Binary Data For File [{0}] From Client [{1}]", fo.FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());
                    }
                }
            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                Console.WriteLine("Exception - " + ex.ToString());
            }
        }

        /// <summary>
        /// Handles an incoming packet of type 'String'
        /// </summary>
        /// <param name="header">Header associated with incoming packet</param>
        /// <param name="connection">The connection associated with incoming packet</param>
        /// <param name="msg">The incoming order msg</param>
        private void IncomingOperationOrder(PacketHeader header, Connection connection, String msg)
        {
            try
            {
                switch (msg)
                {
                    case "HeartBeat":
                        connection.SendObject<String>("HeartBeat", "alive");
                        break;

                    case "GetFileList":
                        connection.SendObject<byte[]>("RFDSFileArray", LocalFileServer.ObjectToByteArray(GetAllFile()));
                        Console.WriteLine("[Order]From Client {0}, with Command {1}", connection.ConnectionInfo.RemoteEndPoint, msg);
                        break;
                }

            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                connection.CloseConnection(true);
                Console.WriteLine("Exception - " + ex.ToString());
            }
        }

        private void IncomingSetupFileLock(PacketHeader packetHeader, Connection connection, string incomingObject)
        {
            try
            {
                FileObjectMap[incomingObject].IsWritting = true;
                BroadCastToOtherServer<string>("SetupFileLock", incomingObject);
                connection.SendObject<String>("OperationDone", "ok");
                Console.WriteLine("[Request]Recieved SetupFileLock For File [{0}] From Client [{1}]", FileObjectMap[incomingObject].FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());
            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                Console.WriteLine("Exception - " + ex.ToString());
            }
        }

        private void IncomingQueryFileLock(PacketHeader packetHeader, Connection connection, string incomingObject)
        {
            try
            {
                //Perform this in a thread safe way
                //lock (syncRootServer)
                //{
                //    RFDSFile fileObject;
                //    fileObject = (RFDSFile)LocalFileServer.ByteArrayToObject(incomingObject);
                //    if (FileObjectMap.TryGetValue(fileObject.FileMD5, out RFDSFile fo))
                //        FileObjectMap[fileObject.FileMD5] = fileObject;
                //    else
                //        FileObjectMap.Add(fileObject.FileMD5, fileObject);

                //    Console.WriteLine("Recieved New File MetaData From Server: " + fileObject.FileName);

                //}

                var fo = FileObjectMap[incomingObject];
                String stauts;

                if (fo.IsReading)
                    stauts = "file_read_lock";
                else if (fo.IsWritting)
                    stauts = "file_write_lock";
                else if (!fo.IsOpened)
                    stauts = "file_not_open";
                else
                    stauts = "ok";

                connection.SendObject<String>("OperationDone", stauts);
                Console.WriteLine("[Request]Recieved QueryFileLock For File [{0}] From Client [{1}]", fo.FileName, connection.ConnectionInfo.RemoteEndPoint.ToString());

            }
            catch (Exception ex)
            {
                //If an exception occurs we write to the log window and also create an error file
                Console.WriteLine("Exception - " + ex.ToString());
            }
        }



        private RFDSFile GetFileObject(String md5)
        {
            if(FileObjectMap.TryGetValue(md5, out RFDSFile file))
                return file;
            else
                return null;
        }

        private RFDSFile[] GetAllFile()
        {
            return FileObjectMap.Values.ToArray<RFDSFile>();
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




        public String SendReceiveMessage(String sendMSG, String ExpectedRerturnTypeStr, String TargetHost)
        {
            return NetworkComms.SendReceiveObject<String, String>("Message", GetIP(TargetHost), GetPort(TargetHost), ExpectedRerturnTypeStr, 1000, sendMSG);
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

        public bool BroadCastToOtherServer<T>(String broadcastTypeStr, T ObjectSent)
        {
            GetAliveServerCount();

            if (UsableServer.Count == 0)
                return false;

            foreach (String server in UsableServer)
            {
                if (server.Equals("127.0.0.1:" + this.port))
                    continue;

                try
                {
                    NetworkComms.SendObject<T>("Server"+broadcastTypeStr, GetIP(server), GetPort(server), ObjectSent);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                    return false;
                }

            }

            return true;
        }

        public bool SendFileObjectToOtherServer(RFDSFile fo)
        {
            GetAliveServerCount();

            if (UsableServer.Count == 0)
                return false;

            foreach (String server in UsableServer)
            {
                if(server.Equals("127.0.0.1:"+this.port))
                    continue;

                try
                {
                    NetworkComms.SendObject<byte[]>("NewFileMetaData", GetIP(server), GetPort(server), LocalFileServer.ObjectToByteArray(fo));
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                    return false;
                }

            }


            return true;
        }

        public bool SendFileBinaryToOtherServer(byte[] sendBytes)
        {

            GetAliveServerCount();

            if (UsableServer.Count == 0)
                return false;

            foreach (String server in UsableServer) {

                if (server.Equals("127.0.0.1:" + this.port))
                    continue;

                try
                {
                    NetworkComms.SendObject<byte[]>("NewFileData", GetIP(server), GetPort(server), sendBytes);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                    continue;
                }

            }
            return true;
        }

    }
}
