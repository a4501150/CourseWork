namespace SimpleRFDS.Client.Forms
{
    partial class MainForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.WriteBtn = new System.Windows.Forms.Button();
            this.ReadBtn = new System.Windows.Forms.Button();
            this.FileListLabel = new System.Windows.Forms.Label();
            this.FileContentTextBox = new System.Windows.Forms.TextBox();
            this.WriteSimpleLineBtn = new System.Windows.Forms.Button();
            this.FileContentLabel = new System.Windows.Forms.Label();
            this.UploadFileBtn = new System.Windows.Forms.Button();
            this.ServerStatusLabel = new System.Windows.Forms.Label();
            this.ProgressLabel = new System.Windows.Forms.Label();
            this.ServerStatusOutputLabel = new System.Windows.Forms.Label();
            this.RefreshListBtn = new System.Windows.Forms.Button();
            this.OpenFileBtn = new System.Windows.Forms.Button();
            this.CloseFileBtn = new System.Windows.Forms.Button();
            this.FileListView = new System.Windows.Forms.ListView();
            this.FileNameCol = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.FileSizeCol = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.FileMD5Col = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.IsReadingCol = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.IsWrittingCol = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.OperationLabel = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // WriteBtn
            // 
            this.WriteBtn.Location = new System.Drawing.Point(642, 293);
            this.WriteBtn.Name = "WriteBtn";
            this.WriteBtn.Size = new System.Drawing.Size(75, 40);
            this.WriteBtn.TabIndex = 0;
            this.WriteBtn.Text = "Write";
            this.WriteBtn.UseVisualStyleBackColor = true;
            this.WriteBtn.Click += new System.EventHandler(this.WriteBtn_Click);
            // 
            // ReadBtn
            // 
            this.ReadBtn.Location = new System.Drawing.Point(568, 293);
            this.ReadBtn.Name = "ReadBtn";
            this.ReadBtn.Size = new System.Drawing.Size(68, 40);
            this.ReadBtn.TabIndex = 1;
            this.ReadBtn.Text = "Read";
            this.ReadBtn.UseVisualStyleBackColor = true;
            this.ReadBtn.Click += new System.EventHandler(this.ReadBtn_Click);
            // 
            // FileListLabel
            // 
            this.FileListLabel.AutoSize = true;
            this.FileListLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.FileListLabel.Location = new System.Drawing.Point(88, 9);
            this.FileListLabel.Name = "FileListLabel";
            this.FileListLabel.Size = new System.Drawing.Size(63, 20);
            this.FileListLabel.TabIndex = 3;
            this.FileListLabel.Text = "File List";
            // 
            // FileContentTextBox
            // 
            this.FileContentTextBox.Location = new System.Drawing.Point(475, 39);
            this.FileContentTextBox.Multiline = true;
            this.FileContentTextBox.Name = "FileContentTextBox";
            this.FileContentTextBox.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.FileContentTextBox.Size = new System.Drawing.Size(332, 251);
            this.FileContentTextBox.TabIndex = 4;
            // 
            // WriteSimpleLineBtn
            // 
            this.WriteSimpleLineBtn.Location = new System.Drawing.Point(475, 293);
            this.WriteSimpleLineBtn.Name = "WriteSimpleLineBtn";
            this.WriteSimpleLineBtn.Size = new System.Drawing.Size(87, 40);
            this.WriteSimpleLineBtn.TabIndex = 5;
            this.WriteSimpleLineBtn.Text = "WriteSimpleNewLine";
            this.WriteSimpleLineBtn.UseVisualStyleBackColor = true;
            this.WriteSimpleLineBtn.Click += new System.EventHandler(this.WriteSimpleLineBtn_Click);
            // 
            // FileContentLabel
            // 
            this.FileContentLabel.AutoSize = true;
            this.FileContentLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.FileContentLabel.Location = new System.Drawing.Point(570, 9);
            this.FileContentLabel.Name = "FileContentLabel";
            this.FileContentLabel.Size = new System.Drawing.Size(141, 20);
            this.FileContentLabel.TabIndex = 6;
            this.FileContentLabel.Text = "File Content Editor";
            // 
            // UploadFileBtn
            // 
            this.UploadFileBtn.Location = new System.Drawing.Point(723, 293);
            this.UploadFileBtn.Name = "UploadFileBtn";
            this.UploadFileBtn.Size = new System.Drawing.Size(84, 40);
            this.UploadFileBtn.TabIndex = 7;
            this.UploadFileBtn.Text = "UploadFile";
            this.UploadFileBtn.UseVisualStyleBackColor = true;
            this.UploadFileBtn.Click += new System.EventHandler(this.UploadFileBtn_ClickAsync);
            // 
            // ServerStatusLabel
            // 
            this.ServerStatusLabel.AutoSize = true;
            this.ServerStatusLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.ServerStatusLabel.Location = new System.Drawing.Point(12, 343);
            this.ServerStatusLabel.Name = "ServerStatusLabel";
            this.ServerStatusLabel.Size = new System.Drawing.Size(102, 20);
            this.ServerStatusLabel.TabIndex = 8;
            this.ServerStatusLabel.Text = "ServerStatus";
            // 
            // ProgressLabel
            // 
            this.ProgressLabel.AutoSize = true;
            this.ProgressLabel.Location = new System.Drawing.Point(403, 349);
            this.ProgressLabel.Name = "ProgressLabel";
            this.ProgressLabel.Size = new System.Drawing.Size(57, 13);
            this.ProgressLabel.TabIndex = 10;
            this.ProgressLabel.Text = "Progeress:";
            // 
            // ServerStatusOutputLabel
            // 
            this.ServerStatusOutputLabel.AutoSize = true;
            this.ServerStatusOutputLabel.Location = new System.Drawing.Point(120, 349);
            this.ServerStatusOutputLabel.Name = "ServerStatusOutputLabel";
            this.ServerStatusOutputLabel.Size = new System.Drawing.Size(37, 13);
            this.ServerStatusOutputLabel.TabIndex = 11;
            this.ServerStatusOutputLabel.Text = "Ping...";
            // 
            // RefreshListBtn
            // 
            this.RefreshListBtn.Location = new System.Drawing.Point(16, 297);
            this.RefreshListBtn.Name = "RefreshListBtn";
            this.RefreshListBtn.Size = new System.Drawing.Size(164, 36);
            this.RefreshListBtn.TabIndex = 13;
            this.RefreshListBtn.Text = "Refresh List";
            this.RefreshListBtn.UseVisualStyleBackColor = true;
            this.RefreshListBtn.Click += new System.EventHandler(this.RefreshListBtn_ClickAsync);
            // 
            // OpenFileBtn
            // 
            this.OpenFileBtn.Location = new System.Drawing.Point(187, 297);
            this.OpenFileBtn.Name = "OpenFileBtn";
            this.OpenFileBtn.Size = new System.Drawing.Size(140, 36);
            this.OpenFileBtn.TabIndex = 14;
            this.OpenFileBtn.Text = "OpenFile";
            this.OpenFileBtn.UseVisualStyleBackColor = true;
            this.OpenFileBtn.Click += new System.EventHandler(this.OpenFileBtn_Click);
            // 
            // CloseFileBtn
            // 
            this.CloseFileBtn.Location = new System.Drawing.Point(333, 297);
            this.CloseFileBtn.Name = "CloseFileBtn";
            this.CloseFileBtn.Size = new System.Drawing.Size(127, 36);
            this.CloseFileBtn.TabIndex = 15;
            this.CloseFileBtn.Text = "CloseFile";
            this.CloseFileBtn.UseVisualStyleBackColor = true;
            this.CloseFileBtn.Click += new System.EventHandler(this.CloseFileBtn_Click);
            // 
            // FileListView
            // 
            this.FileListView.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.FileNameCol,
            this.FileSizeCol,
            this.FileMD5Col,
            this.IsReadingCol,
            this.IsWrittingCol});
            this.FileListView.FullRowSelect = true;
            this.FileListView.GridLines = true;
            this.FileListView.Location = new System.Drawing.Point(13, 39);
            this.FileListView.Name = "FileListView";
            this.FileListView.Size = new System.Drawing.Size(447, 251);
            this.FileListView.TabIndex = 16;
            this.FileListView.UseCompatibleStateImageBehavior = false;
            this.FileListView.SelectedIndexChanged += new System.EventHandler(this.FileListSelectItem);
            // 
            // FileNameCol
            // 
            this.FileNameCol.Text = "FileName";
            // 
            // FileSizeCol
            // 
            this.FileSizeCol.Text = "FileSize";
            // 
            // FileMD5Col
            // 
            this.FileMD5Col.Text = "FileMD5";
            // 
            // IsReadingCol
            // 
            this.IsReadingCol.Text = "IsReading?";
            // 
            // IsWrittingCol
            // 
            this.IsWrittingCol.Text = "IsWritting?";
            // 
            // OperationLabel
            // 
            this.OperationLabel.AutoSize = true;
            this.OperationLabel.Location = new System.Drawing.Point(483, 348);
            this.OperationLabel.Name = "OperationLabel";
            this.OperationLabel.Size = new System.Drawing.Size(79, 13);
            this.OperationLabel.TabIndex = 17;
            this.OperationLabel.Text = "OperationLabel";
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(817, 377);
            this.Controls.Add(this.OperationLabel);
            this.Controls.Add(this.FileListView);
            this.Controls.Add(this.CloseFileBtn);
            this.Controls.Add(this.OpenFileBtn);
            this.Controls.Add(this.RefreshListBtn);
            this.Controls.Add(this.ServerStatusOutputLabel);
            this.Controls.Add(this.ProgressLabel);
            this.Controls.Add(this.ServerStatusLabel);
            this.Controls.Add(this.UploadFileBtn);
            this.Controls.Add(this.FileContentLabel);
            this.Controls.Add(this.WriteSimpleLineBtn);
            this.Controls.Add(this.FileContentTextBox);
            this.Controls.Add(this.FileListLabel);
            this.Controls.Add(this.ReadBtn);
            this.Controls.Add(this.WriteBtn);
            this.Name = "MainForm";
            this.Text = "MainForm";
            this.Load += new System.EventHandler(this.MainFormOnLoad);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button WriteBtn;
        private System.Windows.Forms.Button ReadBtn;
        private System.Windows.Forms.Label FileListLabel;
        private System.Windows.Forms.TextBox FileContentTextBox;
        private System.Windows.Forms.Button WriteSimpleLineBtn;
        private System.Windows.Forms.Label FileContentLabel;
        private System.Windows.Forms.Button UploadFileBtn;
        private System.Windows.Forms.Label ServerStatusLabel;
        private System.Windows.Forms.Label ProgressLabel;
        private System.Windows.Forms.Label ServerStatusOutputLabel;
        private System.Windows.Forms.Button RefreshListBtn;
        private System.Windows.Forms.Button OpenFileBtn;
        private System.Windows.Forms.Button CloseFileBtn;
        private System.Windows.Forms.ListView FileListView;
        private System.Windows.Forms.ColumnHeader FileNameCol;
        private System.Windows.Forms.ColumnHeader FileSizeCol;
        private System.Windows.Forms.ColumnHeader FileMD5Col;
        private System.Windows.Forms.ColumnHeader IsReadingCol;
        private System.Windows.Forms.ColumnHeader IsWrittingCol;
        private System.Windows.Forms.Label OperationLabel;
    }
}