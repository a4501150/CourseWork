using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization.Formatters.Binary;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace SimpleRFDS.FileServer.FileOperation
{
    public static class LocalFileServer
    {
        public static String LastError;

        public static bool AppendFile(String path, byte[] bytesToWritte)
        {
            if(!File.Exists(path))
            {
                Console.WriteLine("File Does Not Exists");
                LastError = "E_File_Not_Exists";
                return false;
            }

            try
            {
                using (var stream = new FileStream(path, FileMode.Append))
                {
                    stream.Write(bytesToWritte, 0, bytesToWritte.Length);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("Append to " + path + " failed, with reason: " + e.Message);
                LastError = "E_File_Append_" + e.Source;
                return false;
            }

            return true;
        }

        public static bool RewnewFile(String path, byte[] bytesToWritte)
        {
            if (!File.Exists(path))
            {
                Console.WriteLine("File Does Not Exists");
                LastError = "E_File_Not_Exists";
                return false;
            }

            try
            {
                using (var stream = new FileStream(path, FileMode.Truncate))
                {
                    stream.Write(bytesToWritte, 0, bytesToWritte.Length);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("Rewnew to " + path + " failed, with reason: " + e.Message);
                LastError = "E_File_Rewnew_" + e.Source;
                return false;
            }

            return true;
        }

        public static bool CreateFile(String path, byte[] byteToWrite)
        {
            if(CheckFileExists(path))
            {
                Console.WriteLine("File Already Exists For " + path);
                LastError = "E_File_Already_Exists";
                return false;
            }
            else
            {
                using (var stream = File.Create(path))
                {
                    stream.Write(byteToWrite, 0, byteToWrite.Length);
                }
                return true;
            }
        }

        public static bool CreateTempFile()
        {
            File.WriteAllText("temp", "");
            return true;
        }

        public static bool DeleteFile(String path)
        {
            try
            {
                File.Delete(path);
            }
            catch (Exception e)
            {
                Console.WriteLine("DeleteFile failed " + path);
                LastError = "E_File_Delete_" + e.Message;
                return false;
            }
            return true;
        }

        public static Byte[] GetFileBytes(String path)
        {
            if (!CheckFileExists(path))
            {
                Console.WriteLine("File Does Not Exist " + path);
                LastError = "E_File_Not_Exists";
                return null;
            }

            try
            {
                File.ReadAllBytes(path);
                return File.ReadAllBytes(path);
            }
            catch (Exception e)
            {
                LastError = "E_File_GetBytes_"+e.Message;
                return null;
            }
        }

        public static bool CheckFileExists(String path)
        {
            if (!File.Exists(path))
                return false;
            else
                return true;
        }

        public static String GetFileMD5(String path)
        {
            using (var md5 = MD5.Create())
            {
                using (var stream = File.OpenRead(path))
                {
                    var hash = md5.ComputeHash(stream);
                    return BitConverter.ToString(hash).Replace("-", "").ToLowerInvariant();
                }
            }
        }

        public static long GetFileSize(String path)
        {
            return (new FileInfo(path)).Length; 
        }

        public static String GetFileName(String path)
        {
            if (File.Exists(path))
                return Path.GetFileName(path);
            else
                return "";
        }

        public static String GetFileAbsolutePath(String relativePath)
        {
            return Path.GetFullPath(relativePath);
        }

        public static string GetFileSizeInString(long size)
        {
            return size.ToString();
        }

        public static String QueryFileInfo(String path)
        {
            return "";
        }

        // Convert an object to a byte array
        public static byte[] ObjectToByteArray(Object obj)
        {
            BinaryFormatter bf = new BinaryFormatter();
            using (var ms = new MemoryStream())
            {
                bf.Serialize(ms, obj);
                return ms.ToArray();
            }
        }

        // Convert a byte array to an Object
        public static Object ByteArrayToObject(byte[] arrBytes)
        {
            using (var memStream = new MemoryStream())
            {
                var binForm = new BinaryFormatter();
                memStream.Write(arrBytes, 0, arrBytes.Length);
                memStream.Seek(0, SeekOrigin.Begin);
                var obj = binForm.Deserialize(memStream);
                return obj;
            }
        }

    }
}
