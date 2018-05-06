using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataType
{
    //shared between Servers, file's metadata
    [Serializable]
    public class RFDSFile
    {
        public String FilePath { get; set; }
        public String FileName { get; set; }
        public long FileSize { get; set; }
        public String FileMD5 { get; set; }

        public bool IsWritting { get; set; }
        public bool IsReading { get; set; }
        public bool IsOpened { get; set; }


    }
}
