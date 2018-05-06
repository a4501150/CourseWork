using DataType;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Runtime.Serialization.Formatters.Binary;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace SimpleRFDS.Client.Forms
{
    public partial class MainForm : Form
    {

        ClientRequest cr;

        public MainForm()
        {
            InitializeComponent();
        }

        private void MainFormOnLoad(object sender, EventArgs e)
        {
            cr = new ClientRequest();
            FileListView.View = View.Details;
            FileListView.HideSelection = false;
            FileListView.AutoResizeColumns(ColumnHeaderAutoResizeStyle.ColumnContent);
            FileListView.AutoResizeColumns(ColumnHeaderAutoResizeStyle.HeaderSize);

            OpenFileBtn.Enabled = false;
            CloseFileBtn.Enabled = false;
            FileContentTextBox.ReadOnly = true;

            Thread tUpdateServerStatus = new Thread(UpdateServerStatusAsync);
            tUpdateServerStatus.Start();
        }

        private async void UpdateServerStatusAsync()
        {
            while (true)
            {
                var aliveCount = cr.GetAliveServerCount();
                ServerStatusOutputLabel.Text = aliveCount + " Server(s) Online";
                ServerStatusOutputLabel.ForeColor = Color.Red;
            }
        }

        private async Task UpdateFileListAsync()
        {
            OpenFileBtn.Enabled = false;
            CloseFileBtn.Enabled = false;

            FileListView.Items.Clear();

            var FileObjectArray = cr.GetFileList();

            if (FileObjectArray == null || FileObjectArray.Length == 0)
                return;

            OpenFileBtn.Enabled = true;
            CloseFileBtn.Enabled = true;

            foreach (RFDSFile f in FileObjectArray)
            {
                string[] row = { f.FileName, f.FileSize.ToString(), f.FileMD5 , f.IsReading.ToString() , f.IsWritting.ToString() };
                var listViewItem = new ListViewItem(row);
                FileListView.Items.Add(listViewItem);
            }
            
        }

        //helpers
        public static Byte[] GetFileBytes(String path)
        {
            if (!CheckFileExists(path))
            {
                Console.WriteLine("File Does Not Exist " + path);
                return null;
            }

            try
            {
                File.ReadAllBytes(path);
                return File.ReadAllBytes(path);
            }
            catch (Exception e)
            {
                Console.WriteLine("GetFileBytes Failed "+e.Message);
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

        private void FileListSelectItem(object sender, EventArgs e)
        {

        }

        private void OpenFileBtn_Click(object sender, EventArgs e)
        {
            if (FileListView.SelectedItems.Count == 0)
                OperationLabel.Text = "Please Select A File First";
            else
            {
                OperationLabel.Text = FileListView.SelectedItems[0].Text + "\r\nMD5: "+ FileListView.SelectedItems[0].SubItems[2].Text + " Opened!";
                cr.OpenFile(FileListView.SelectedItems[0].SubItems[2].Text);
                FileListView.SelectedItems[0].Focused = true;
                FileListView.SelectedItems[0].Selected = true;
            }
                
        }

        private void CloseFileBtn_Click(object sender, EventArgs e)
        {
            if (FileListView.SelectedItems.Count == 0)
                OperationLabel.Text = "Please Select A File First";
            else
            {
                OperationLabel.Text = FileListView.SelectedItems[0].Text + "\r\nMD5: " + FileListView.SelectedItems[0].SubItems[2].Text + " Closed!";
                cr.CloseFile(FileListView.SelectedItems[0].SubItems[2].Text);
                FileListView.SelectedItems[0].Focused = true;
                FileListView.SelectedItems[0].Selected = true;
            } 
        }

        private void ReadBtn_Click(object sender, EventArgs e)
        {
            if (FileListView.SelectedItems.Count == 0)
                OperationLabel.Text = "Please Select A File First";
            else
            {
                FileContentTextBox.Text = cr.ReadFile(FileListView.SelectedItems[0].SubItems[2].Text);
                FileListView.SelectedItems[0].Focused = true;
                FileListView.SelectedItems[0].Selected = true;
                OperationLabel.Text = FileListView.SelectedItems[0].Text + "\r\nMD5: " + FileListView.SelectedItems[0].SubItems[2].Text + " Readed!";
            }
        }

        private void WriteSimpleLineBtn_Click(object sender, EventArgs e)
        {
            if (FileListView.SelectedItems.Count == 0)
                OperationLabel.Text = "Please Select A File First";
            else
            {
                var newMD5 = cr.AppendFile(FileListView.SelectedItems[0].SubItems[2].Text, "\r\nIm a new line");
                if(newMD5.Equals("file_locked"))
                {
                    OperationLabel.Text = "File Is Locked! Cannot Append.";
                    return;
                }

                FileListView.SelectedItems[0].SubItems[2].Text = newMD5;
                Thread.Sleep(200);

                cr.CloseFile(FileListView.SelectedItems[0].SubItems[2].Text);
                Thread.Sleep(200);

                cr.OpenFile(FileListView.SelectedItems[0].SubItems[2].Text);
                Thread.Sleep(200);

                FileContentTextBox.Text = cr.ReadFile(FileListView.SelectedItems[0].SubItems[2].Text);
                Thread.Sleep(200);

                cr.CloseFile(FileListView.SelectedItems[0].SubItems[2].Text);
                Thread.Sleep(200);

                FileListView.SelectedItems[0].Focused = true;
                FileListView.SelectedItems[0].Selected = true;
                OperationLabel.Text = FileListView.SelectedItems[0].Text + "\r\nMD5: " + FileListView.SelectedItems[0].SubItems[2].Text + " Added A New Line!";
            }
        }

        private void WriteBtn_Click(object sender, EventArgs e)
        {
            if (FileListView.SelectedItems.Count == 0)
                OperationLabel.Text = "Please Select A File First";
            else
            {
                if(((Button)sender).Text == "Write")
                {
                    var status = cr.QueryFileLock(FileListView.SelectedItems[0].SubItems[2].Text);

                    if(!status.Equals("ok"))
                    {
                        FileContentTextBox.Text = status;
                        FileListView.SelectedItems[0].Focused = true;
                        FileListView.SelectedItems[0].Selected = true;
                        return;
                    }

                    ((Button)sender).Text = "Confirm";
                    //close for reading then open
                    FileContentTextBox.Text = cr.ReadFile(FileListView.SelectedItems[0].SubItems[2].Text);

                    Thread.Sleep(100);
                    cr.CloseFile(FileListView.SelectedItems[0].SubItems[2].Text);
                    Thread.Sleep(100);
                    cr.OpenFile(FileListView.SelectedItems[0].SubItems[2].Text);
                    Thread.Sleep(100);
                    cr.SetupFileLock(FileListView.SelectedItems[0].SubItems[2].Text);

                    FileContentTextBox.ReadOnly = false;
                    OperationLabel.Text = FileListView.SelectedItems[0].Text + "\r\nMD5: " + FileListView.SelectedItems[0].SubItems[2].Text + " Openned For Write!";
                    FileListView.SelectedItems[0].Focused = true;
                    FileListView.SelectedItems[0].Selected = true;
                    return;
                }
                else
                {
                    ((Button)sender).Text = "Write";
                    FileContentTextBox.ReadOnly = true;
                    var ret = cr.WriteFile(FileListView.SelectedItems[0].SubItems[2].Text, FileContentTextBox.Text);
                    if(ret.Contains( "file_"))
                    {
                        OperationLabel.Text = ret;
                        FileContentTextBox.Text = ret;
                        FileListView.SelectedItems[0].Focused = true;
                        FileListView.SelectedItems[0].Selected = true;
                        return;
                    }
                    else
                    {
                        FileListView.SelectedItems[0].SubItems[2].Text = ret;
                    }
                }
                

                FileListView.SelectedItems[0].Focused = true;
                FileListView.SelectedItems[0].Selected = true;
                OperationLabel.Text = FileListView.SelectedItems[0].Text + "\r\nMD5: " + FileListView.SelectedItems[0].SubItems[2].Text + " Writted!";
            }
        }


        private async void UploadFileBtn_ClickAsync(object sender, EventArgs e)
        {
            // Create an instance of the open file dialog box.
            OpenFileDialog openFileDialogToUpload = new OpenFileDialog();

            // Set filter options and filter index.
            openFileDialogToUpload.Filter = "Text Files (.txt)|*.txt|All Files (*.*)|*.*";
            openFileDialogToUpload.FilterIndex = 1;

            openFileDialogToUpload.Multiselect = false;

            // Call the ShowDialog method to show the dialog box.
            bool userClickedOK = openFileDialogToUpload.ShowDialog() == DialogResult.OK;

            // Process input if the user clicked OK.
            if (userClickedOK == true)
            {
                // Open the selected file to read.
                System.IO.Stream fileStream = openFileDialogToUpload.OpenFile();

                RFDSFile fo = new RFDSFile();
                fo.FileMD5 = GetFileMD5(openFileDialogToUpload.FileName);
                fo.FileName = GetFileName(openFileDialogToUpload.FileName);
                fo.FilePath = fo.FileName;
                fo.FileSize = GetFileSize(openFileDialogToUpload.FileName);
                fo.IsWritting = false;
                fo.IsReading = false;
                fo.IsOpened = false;

                cr.SendFileObject(fo);

                using (System.IO.StreamReader reader = new System.IO.StreamReader(fileStream, Encoding.UTF8))
                {
                    Console.WriteLine("Registering FileMetaData");
                    OperationLabel.Text = "Registering FileMetaData";
                }
                fileStream.Close();

                Console.WriteLine("Sending File Binary");
                OperationLabel.Text = "Sending File Binary";

                var ret = cr.SendFileBinary(openFileDialogToUpload.FileName);
                if (ret)
                    OperationLabel.Text = "File Uploaded Successful";
                else
                    OperationLabel.Text = "File Uploaded Failed";
            }

        }

        private async void RefreshListBtn_ClickAsync(object sender, EventArgs e)
        {
            ((Button)sender).Enabled = false;
            await UpdateFileListAsync();
            ((Button)sender).Enabled = true;
        }
    }
}
