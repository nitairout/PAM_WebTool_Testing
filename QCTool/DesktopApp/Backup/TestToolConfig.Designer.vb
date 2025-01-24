<Global.Microsoft.VisualBasic.CompilerServices.DesignerGenerated()> _
Partial Class TestToolConfig
    Inherits System.Windows.Forms.Form

    'Form overrides dispose to clean up the component list.
    <System.Diagnostics.DebuggerNonUserCode()> _
    Protected Overrides Sub Dispose(ByVal disposing As Boolean)
        Try
            If disposing AndAlso components IsNot Nothing Then
                components.Dispose()
            End If
        Finally
            MyBase.Dispose(disposing)
        End Try
    End Sub

    'Required by the Windows Form Designer
    Private components As System.ComponentModel.IContainer

    'NOTE: The following procedure is required by the Windows Form Designer
    'It can be modified using the Windows Form Designer.  
    'Do not modify it using the code editor.
    <System.Diagnostics.DebuggerStepThrough()> _
    Private Sub InitializeComponent()
        Me.components = New System.ComponentModel.Container()
        Dim resources As System.ComponentModel.ComponentResourceManager = New System.ComponentModel.ComponentResourceManager(GetType(TestToolConfig))
        Me.Label1 = New System.Windows.Forms.Label()
        Me.Button1 = New System.Windows.Forms.Button()
        Me.TabControl1 = New System.Windows.Forms.TabControl()
        Me.TabPage1 = New System.Windows.Forms.TabPage()
        Me.cmbApp = New System.Windows.Forms.ComboBox()
        Me.lblApp = New System.Windows.Forms.Label()
        Me.cmbLanguage = New System.Windows.Forms.ComboBox()
        Me.lblLanguage = New System.Windows.Forms.Label()
        Me.lblIE = New System.Windows.Forms.Label()
        Me.cmbClient = New System.Windows.Forms.ComboBox()
        Me.cmbEnv = New System.Windows.Forms.ComboBox()
        Me.cmbWidgetPageNames = New System.Windows.Forms.ComboBox()
        Me.rdbWidget = New System.Windows.Forms.RadioButton()
        Me.rdbPage = New System.Windows.Forms.RadioButton()
        Me.rdbALL = New System.Windows.Forms.RadioButton()
        Me.cmbBrowser = New System.Windows.Forms.ComboBox()
        Me.txtEmail = New System.Windows.Forms.TextBox()
        Me.txtPassword = New System.Windows.Forms.TextBox()
        Me.txtUserName = New System.Windows.Forms.TextBox()
        Me.lblEmail = New System.Windows.Forms.Label()
        Me.lblWidgetPage = New System.Windows.Forms.Label()
        Me.lblPassword = New System.Windows.Forms.Label()
        Me.lblUserName = New System.Windows.Forms.Label()
        Me.lblClient = New System.Windows.Forms.Label()
        Me.lblBrowser = New System.Windows.Forms.Label()
        Me.lblEnv = New System.Windows.Forms.Label()
        Me.TabPage2 = New System.Windows.Forms.TabPage()
        Me.lnkUnCheckAll = New System.Windows.Forms.LinkLabel()
        Me.lnkCheckAll = New System.Windows.Forms.LinkLabel()
        Me.lstTestCaseConfig = New System.Windows.Forms.ListView()
        Me.ColumnHeader1 = CType(New System.Windows.Forms.ColumnHeader(), System.Windows.Forms.ColumnHeader)
        Me.ColumnHeader2 = CType(New System.Windows.Forms.ColumnHeader(), System.Windows.Forms.ColumnHeader)
        Me.TabPage3 = New System.Windows.Forms.TabPage()
        Me.txtIePath = New System.Windows.Forms.TextBox()
        Me.lblIePath = New System.Windows.Forms.Label()
        Me.txtChromeProfilePath = New System.Windows.Forms.TextBox()
        Me.lblChromeProfilePath = New System.Windows.Forms.Label()
        Me.txtFireFoxProfilePath = New System.Windows.Forms.TextBox()
        Me.lblFireFoxProfilePath = New System.Windows.Forms.Label()
        Me.txtChromeDriverPath = New System.Windows.Forms.TextBox()
        Me.lblChromeDriverPath = New System.Windows.Forms.Label()
        Me.lblNote = New System.Windows.Forms.Label()
        Me.ToolTip1 = New System.Windows.Forms.ToolTip(Me.components)
        Me.lblPhantomJSPath = New System.Windows.Forms.Label()
        Me.txtPhantomJSPath = New System.Windows.Forms.TextBox()
        Me.TabControl1.SuspendLayout()
        Me.TabPage1.SuspendLayout()
        Me.TabPage2.SuspendLayout()
        Me.TabPage3.SuspendLayout()
        Me.SuspendLayout()
        '
        'Label1
        '
        Me.Label1.AutoSize = True
        Me.Label1.Font = New System.Drawing.Font("Microsoft Sans Serif", 12.0!, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.Label1.Location = New System.Drawing.Point(194, 9)
        Me.Label1.Name = "Label1"
        Me.Label1.Size = New System.Drawing.Size(281, 20)
        Me.Label1.TabIndex = 0
        Me.Label1.Text = "Schneider - Automated Test Suite"
        '
        'Button1
        '
        Me.Button1.Font = New System.Drawing.Font("Microsoft Sans Serif", 8.25!, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.Button1.Location = New System.Drawing.Point(234, 474)
        Me.Button1.Name = "Button1"
        Me.Button1.Size = New System.Drawing.Size(174, 24)
        Me.Button1.TabIndex = 3
        Me.Button1.Text = "Save and Run Test Suite"
        Me.Button1.UseVisualStyleBackColor = True
        '
        'TabControl1
        '
        Me.TabControl1.Controls.Add(Me.TabPage1)
        Me.TabControl1.Controls.Add(Me.TabPage2)
        Me.TabControl1.Controls.Add(Me.TabPage3)
        Me.TabControl1.Location = New System.Drawing.Point(43, 47)
        Me.TabControl1.Multiline = True
        Me.TabControl1.Name = "TabControl1"
        Me.TabControl1.SelectedIndex = 0
        Me.TabControl1.Size = New System.Drawing.Size(632, 411)
        Me.TabControl1.TabIndex = 7
        '
        'TabPage1
        '
        Me.TabPage1.Controls.Add(Me.cmbApp)
        Me.TabPage1.Controls.Add(Me.lblApp)
        Me.TabPage1.Controls.Add(Me.cmbLanguage)
        Me.TabPage1.Controls.Add(Me.lblLanguage)
        Me.TabPage1.Controls.Add(Me.lblIE)
        Me.TabPage1.Controls.Add(Me.cmbClient)
        Me.TabPage1.Controls.Add(Me.cmbEnv)
        Me.TabPage1.Controls.Add(Me.cmbWidgetPageNames)
        Me.TabPage1.Controls.Add(Me.rdbWidget)
        Me.TabPage1.Controls.Add(Me.rdbPage)
        Me.TabPage1.Controls.Add(Me.rdbALL)
        Me.TabPage1.Controls.Add(Me.cmbBrowser)
        Me.TabPage1.Controls.Add(Me.txtEmail)
        Me.TabPage1.Controls.Add(Me.txtPassword)
        Me.TabPage1.Controls.Add(Me.txtUserName)
        Me.TabPage1.Controls.Add(Me.lblEmail)
        Me.TabPage1.Controls.Add(Me.lblWidgetPage)
        Me.TabPage1.Controls.Add(Me.lblPassword)
        Me.TabPage1.Controls.Add(Me.lblUserName)
        Me.TabPage1.Controls.Add(Me.lblClient)
        Me.TabPage1.Controls.Add(Me.lblBrowser)
        Me.TabPage1.Controls.Add(Me.lblEnv)
        Me.TabPage1.Location = New System.Drawing.Point(4, 22)
        Me.TabPage1.Name = "TabPage1"
        Me.TabPage1.Padding = New System.Windows.Forms.Padding(3)
        Me.TabPage1.Size = New System.Drawing.Size(624, 385)
        Me.TabPage1.TabIndex = 0
        Me.TabPage1.Text = "Input"
        Me.TabPage1.UseVisualStyleBackColor = True
        '
        'cmbApp
        '
        Me.cmbApp.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList
        Me.cmbApp.FormattingEnabled = True
        Me.cmbApp.Items.AddRange(New Object() {"CBMS", "Resource Advisor"})
        Me.cmbApp.Location = New System.Drawing.Point(216, 27)
        Me.cmbApp.Name = "cmbApp"
        Me.cmbApp.Size = New System.Drawing.Size(225, 21)
        Me.cmbApp.TabIndex = 30
        '
        'lblApp
        '
        Me.lblApp.AutoSize = True
        Me.lblApp.Location = New System.Drawing.Point(60, 31)
        Me.lblApp.Name = "lblApp"
        Me.lblApp.Size = New System.Drawing.Size(59, 13)
        Me.lblApp.TabIndex = 29
        Me.lblApp.Text = "Application"
        '
        'cmbLanguage
        '
        Me.cmbLanguage.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList
        Me.cmbLanguage.FormattingEnabled = True
        Me.cmbLanguage.Location = New System.Drawing.Point(216, 105)
        Me.cmbLanguage.Name = "cmbLanguage"
        Me.cmbLanguage.Size = New System.Drawing.Size(225, 21)
        Me.cmbLanguage.TabIndex = 28
        '
        'lblLanguage
        '
        Me.lblLanguage.AutoSize = True
        Me.lblLanguage.Location = New System.Drawing.Point(58, 109)
        Me.lblLanguage.Name = "lblLanguage"
        Me.lblLanguage.Size = New System.Drawing.Size(55, 13)
        Me.lblLanguage.TabIndex = 27
        Me.lblLanguage.Text = "Language"
        '
        'lblIE
        '
        Me.lblIE.AutoSize = True
        Me.lblIE.ForeColor = System.Drawing.Color.Red
        Me.lblIE.Location = New System.Drawing.Point(447, 102)
        Me.lblIE.Name = "lblIE"
        Me.lblIE.Size = New System.Drawing.Size(172, 104)
        Me.lblIE.TabIndex = 26
        Me.lblIE.Text = resources.GetString("lblIE.Text")
        '
        'cmbClient
        '
        Me.cmbClient.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList
        Me.cmbClient.FormattingEnabled = True
        Me.cmbClient.Location = New System.Drawing.Point(216, 243)
        Me.cmbClient.Name = "cmbClient"
        Me.cmbClient.Size = New System.Drawing.Size(225, 21)
        Me.cmbClient.TabIndex = 25
        '
        'cmbEnv
        '
        Me.cmbEnv.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList
        Me.cmbEnv.FormattingEnabled = True
        Me.cmbEnv.Items.AddRange(New Object() {"Track1", "Track2", "Track3", "Track5", "Staging", "Staging2", "Production", "Production - John Deere"})
        Me.cmbEnv.Location = New System.Drawing.Point(216, 70)
        Me.cmbEnv.Name = "cmbEnv"
        Me.cmbEnv.Size = New System.Drawing.Size(225, 21)
        Me.cmbEnv.TabIndex = 24
        '
        'cmbWidgetPageNames
        '
        Me.cmbWidgetPageNames.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList
        Me.cmbWidgetPageNames.FormattingEnabled = True
        Me.cmbWidgetPageNames.Location = New System.Drawing.Point(216, 298)
        Me.cmbWidgetPageNames.Name = "cmbWidgetPageNames"
        Me.cmbWidgetPageNames.Size = New System.Drawing.Size(225, 21)
        Me.cmbWidgetPageNames.TabIndex = 23
        '
        'rdbWidget
        '
        Me.rdbWidget.AutoSize = True
        Me.rdbWidget.Location = New System.Drawing.Point(318, 272)
        Me.rdbWidget.Name = "rdbWidget"
        Me.rdbWidget.Size = New System.Drawing.Size(59, 17)
        Me.rdbWidget.TabIndex = 22
        Me.rdbWidget.Text = "Widget"
        Me.rdbWidget.UseVisualStyleBackColor = True
        '
        'rdbPage
        '
        Me.rdbPage.AutoSize = True
        Me.rdbPage.Location = New System.Drawing.Point(262, 272)
        Me.rdbPage.Name = "rdbPage"
        Me.rdbPage.Size = New System.Drawing.Size(50, 17)
        Me.rdbPage.TabIndex = 22
        Me.rdbPage.Text = "Page"
        Me.rdbPage.UseVisualStyleBackColor = True
        '
        'rdbALL
        '
        Me.rdbALL.AutoSize = True
        Me.rdbALL.Checked = True
        Me.rdbALL.Location = New System.Drawing.Point(216, 272)
        Me.rdbALL.Name = "rdbALL"
        Me.rdbALL.Size = New System.Drawing.Size(36, 17)
        Me.rdbALL.TabIndex = 22
        Me.rdbALL.TabStop = True
        Me.rdbALL.Text = "All"
        Me.rdbALL.UseVisualStyleBackColor = True
        '
        'cmbBrowser
        '
        Me.cmbBrowser.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList
        Me.cmbBrowser.FormattingEnabled = True
        Me.cmbBrowser.Items.AddRange(New Object() {"Chrome", "FireFox", "IE"})
        Me.cmbBrowser.Location = New System.Drawing.Point(216, 140)
        Me.cmbBrowser.Name = "cmbBrowser"
        Me.cmbBrowser.Size = New System.Drawing.Size(225, 21)
        Me.cmbBrowser.TabIndex = 21
        '
        'txtEmail
        '
        Me.txtEmail.Location = New System.Drawing.Point(216, 333)
        Me.txtEmail.Name = "txtEmail"
        Me.txtEmail.Size = New System.Drawing.Size(225, 20)
        Me.txtEmail.TabIndex = 16
        '
        'txtPassword
        '
        Me.txtPassword.Location = New System.Drawing.Point(216, 209)
        Me.txtPassword.Name = "txtPassword"
        Me.txtPassword.PasswordChar = Global.Microsoft.VisualBasic.ChrW(42)
        Me.txtPassword.Size = New System.Drawing.Size(225, 20)
        Me.txtPassword.TabIndex = 14
        '
        'txtUserName
        '
        Me.txtUserName.Location = New System.Drawing.Point(216, 175)
        Me.txtUserName.Name = "txtUserName"
        Me.txtUserName.Size = New System.Drawing.Size(225, 20)
        Me.txtUserName.TabIndex = 20
        '
        'lblEmail
        '
        Me.lblEmail.AutoSize = True
        Me.lblEmail.Location = New System.Drawing.Point(58, 337)
        Me.lblEmail.Name = "lblEmail"
        Me.lblEmail.Size = New System.Drawing.Size(97, 13)
        Me.lblEmail.TabIndex = 8
        Me.lblEmail.Text = "Email QC Report to"
        '
        'lblWidgetPage
        '
        Me.lblWidgetPage.AutoSize = True
        Me.lblWidgetPage.Location = New System.Drawing.Point(58, 302)
        Me.lblWidgetPage.Name = "lblWidgetPage"
        Me.lblWidgetPage.Size = New System.Drawing.Size(71, 13)
        Me.lblWidgetPage.TabIndex = 9
        Me.lblWidgetPage.Text = "Widget/Page"
        '
        'lblPassword
        '
        Me.lblPassword.AutoSize = True
        Me.lblPassword.Location = New System.Drawing.Point(58, 213)
        Me.lblPassword.Name = "lblPassword"
        Me.lblPassword.Size = New System.Drawing.Size(53, 13)
        Me.lblPassword.TabIndex = 6
        Me.lblPassword.Text = "Password"
        '
        'lblUserName
        '
        Me.lblUserName.AutoSize = True
        Me.lblUserName.Location = New System.Drawing.Point(58, 179)
        Me.lblUserName.Name = "lblUserName"
        Me.lblUserName.Size = New System.Drawing.Size(60, 13)
        Me.lblUserName.TabIndex = 12
        Me.lblUserName.Text = "User Name"
        '
        'lblClient
        '
        Me.lblClient.AutoSize = True
        Me.lblClient.Location = New System.Drawing.Point(58, 247)
        Me.lblClient.Name = "lblClient"
        Me.lblClient.Size = New System.Drawing.Size(136, 13)
        Me.lblClient.TabIndex = 13
        Me.lblClient.Text = "Client (only for internal user)"
        '
        'lblBrowser
        '
        Me.lblBrowser.AutoSize = True
        Me.lblBrowser.Location = New System.Drawing.Point(58, 144)
        Me.lblBrowser.Name = "lblBrowser"
        Me.lblBrowser.Size = New System.Drawing.Size(45, 13)
        Me.lblBrowser.TabIndex = 10
        Me.lblBrowser.Text = "Browser"
        '
        'lblEnv
        '
        Me.lblEnv.AutoSize = True
        Me.lblEnv.Location = New System.Drawing.Point(58, 74)
        Me.lblEnv.Name = "lblEnv"
        Me.lblEnv.Size = New System.Drawing.Size(66, 13)
        Me.lblEnv.TabIndex = 11
        Me.lblEnv.Text = "Environment"
        '
        'TabPage2
        '
        Me.TabPage2.Controls.Add(Me.lnkUnCheckAll)
        Me.TabPage2.Controls.Add(Me.lnkCheckAll)
        Me.TabPage2.Controls.Add(Me.lstTestCaseConfig)
        Me.TabPage2.Location = New System.Drawing.Point(4, 22)
        Me.TabPage2.Name = "TabPage2"
        Me.TabPage2.Padding = New System.Windows.Forms.Padding(3)
        Me.TabPage2.Size = New System.Drawing.Size(624, 385)
        Me.TabPage2.TabIndex = 1
        Me.TabPage2.Text = "Test case configuration"
        Me.TabPage2.UseVisualStyleBackColor = True
        '
        'lnkUnCheckAll
        '
        Me.lnkUnCheckAll.AutoSize = True
        Me.lnkUnCheckAll.Location = New System.Drawing.Point(64, 7)
        Me.lnkUnCheckAll.Name = "lnkUnCheckAll"
        Me.lnkUnCheckAll.Size = New System.Drawing.Size(65, 13)
        Me.lnkUnCheckAll.TabIndex = 8
        Me.lnkUnCheckAll.TabStop = True
        Me.lnkUnCheckAll.Text = "Uncheck All"
        '
        'lnkCheckAll
        '
        Me.lnkCheckAll.AutoSize = True
        Me.lnkCheckAll.Location = New System.Drawing.Point(6, 7)
        Me.lnkCheckAll.Name = "lnkCheckAll"
        Me.lnkCheckAll.Size = New System.Drawing.Size(52, 13)
        Me.lnkCheckAll.TabIndex = 7
        Me.lnkCheckAll.TabStop = True
        Me.lnkCheckAll.Text = "Check All"
        '
        'lstTestCaseConfig
        '
        Me.lstTestCaseConfig.CheckBoxes = True
        Me.lstTestCaseConfig.Columns.AddRange(New System.Windows.Forms.ColumnHeader() {Me.ColumnHeader1, Me.ColumnHeader2})
        Me.lstTestCaseConfig.FullRowSelect = True
        Me.lstTestCaseConfig.GridLines = True
        Me.lstTestCaseConfig.Location = New System.Drawing.Point(-1, 23)
        Me.lstTestCaseConfig.MultiSelect = False
        Me.lstTestCaseConfig.Name = "lstTestCaseConfig"
        Me.lstTestCaseConfig.RightToLeft = System.Windows.Forms.RightToLeft.No
        Me.lstTestCaseConfig.Size = New System.Drawing.Size(624, 362)
        Me.lstTestCaseConfig.TabIndex = 5
        Me.lstTestCaseConfig.UseCompatibleStateImageBehavior = False
        Me.lstTestCaseConfig.View = System.Windows.Forms.View.Details
        '
        'ColumnHeader1
        '
        Me.ColumnHeader1.Text = "Test Case Name"
        Me.ColumnHeader1.Width = 200
        '
        'ColumnHeader2
        '
        Me.ColumnHeader2.Text = "Description"
        Me.ColumnHeader2.Width = 380
        '
        'TabPage3
        '
        Me.TabPage3.Controls.Add(Me.txtPhantomJSPath)
        Me.TabPage3.Controls.Add(Me.lblPhantomJSPath)
        Me.TabPage3.Controls.Add(Me.txtIePath)
        Me.TabPage3.Controls.Add(Me.lblIePath)
        Me.TabPage3.Controls.Add(Me.txtChromeProfilePath)
        Me.TabPage3.Controls.Add(Me.lblChromeProfilePath)
        Me.TabPage3.Controls.Add(Me.txtFireFoxProfilePath)
        Me.TabPage3.Controls.Add(Me.lblFireFoxProfilePath)
        Me.TabPage3.Controls.Add(Me.txtChromeDriverPath)
        Me.TabPage3.Controls.Add(Me.lblChromeDriverPath)
        Me.TabPage3.Location = New System.Drawing.Point(4, 22)
        Me.TabPage3.Name = "TabPage3"
        Me.TabPage3.Size = New System.Drawing.Size(624, 385)
        Me.TabPage3.TabIndex = 2
        Me.TabPage3.Text = "Techinical Configuration"
        Me.TabPage3.UseVisualStyleBackColor = True
        '
        'txtIePath
        '
        Me.txtIePath.Location = New System.Drawing.Point(233, 189)
        Me.txtIePath.Multiline = True
        Me.txtIePath.Name = "txtIePath"
        Me.txtIePath.Size = New System.Drawing.Size(238, 25)
        Me.txtIePath.TabIndex = 7
        '
        'lblIePath
        '
        Me.lblIePath.AutoSize = True
        Me.lblIePath.Location = New System.Drawing.Point(48, 189)
        Me.lblIePath.Name = "lblIePath"
        Me.lblIePath.Size = New System.Drawing.Size(42, 13)
        Me.lblIePath.TabIndex = 6
        Me.lblIePath.Text = "IE Path"
        '
        'txtChromeProfilePath
        '
        Me.txtChromeProfilePath.Location = New System.Drawing.Point(233, 130)
        Me.txtChromeProfilePath.Multiline = True
        Me.txtChromeProfilePath.Name = "txtChromeProfilePath"
        Me.txtChromeProfilePath.Size = New System.Drawing.Size(238, 39)
        Me.txtChromeProfilePath.TabIndex = 5
        '
        'lblChromeProfilePath
        '
        Me.lblChromeProfilePath.AutoSize = True
        Me.lblChromeProfilePath.Location = New System.Drawing.Point(48, 130)
        Me.lblChromeProfilePath.Name = "lblChromeProfilePath"
        Me.lblChromeProfilePath.Size = New System.Drawing.Size(100, 13)
        Me.lblChromeProfilePath.TabIndex = 4
        Me.lblChromeProfilePath.Text = "Chrome Profile Path"
        '
        'txtFireFoxProfilePath
        '
        Me.txtFireFoxProfilePath.Location = New System.Drawing.Point(233, 80)
        Me.txtFireFoxProfilePath.Multiline = True
        Me.txtFireFoxProfilePath.Name = "txtFireFoxProfilePath"
        Me.txtFireFoxProfilePath.Size = New System.Drawing.Size(238, 44)
        Me.txtFireFoxProfilePath.TabIndex = 3
        '
        'lblFireFoxProfilePath
        '
        Me.lblFireFoxProfilePath.AutoSize = True
        Me.lblFireFoxProfilePath.Location = New System.Drawing.Point(48, 87)
        Me.lblFireFoxProfilePath.Name = "lblFireFoxProfilePath"
        Me.lblFireFoxProfilePath.Size = New System.Drawing.Size(101, 13)
        Me.lblFireFoxProfilePath.TabIndex = 2
        Me.lblFireFoxProfilePath.Text = "FireFox Profile Path "
        '
        'txtChromeDriverPath
        '
        Me.txtChromeDriverPath.Location = New System.Drawing.Point(233, 34)
        Me.txtChromeDriverPath.Multiline = True
        Me.txtChromeDriverPath.Name = "txtChromeDriverPath"
        Me.txtChromeDriverPath.Size = New System.Drawing.Size(238, 23)
        Me.txtChromeDriverPath.TabIndex = 1
        '
        'lblChromeDriverPath
        '
        Me.lblChromeDriverPath.AutoSize = True
        Me.lblChromeDriverPath.Location = New System.Drawing.Point(48, 34)
        Me.lblChromeDriverPath.Name = "lblChromeDriverPath"
        Me.lblChromeDriverPath.Size = New System.Drawing.Size(99, 13)
        Me.lblChromeDriverPath.TabIndex = 0
        Me.lblChromeDriverPath.Text = "Chrome Driver Path"
        '
        'lblNote
        '
        Me.lblNote.AutoSize = True
        Me.lblNote.Font = New System.Drawing.Font("Microsoft Sans Serif", 8.25!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.lblNote.ForeColor = System.Drawing.Color.Red
        Me.lblNote.Location = New System.Drawing.Point(64, 521)
        Me.lblNote.Name = "lblNote"
        Me.lblNote.Size = New System.Drawing.Size(527, 13)
        Me.lblNote.TabIndex = 8
        Me.lblNote.Text = "Note: Please maximize the VM to desktop using ‘Ctrl+Alt+Pause/Break’ command befo" & _
    "re starting the test suite "
        '
        'ToolTip1
        '
        Me.ToolTip1.IsBalloon = True
        '
        'lblPhantomJSPath
        '
        Me.lblPhantomJSPath.AutoSize = True
        Me.lblPhantomJSPath.Location = New System.Drawing.Point(48, 236)
        Me.lblPhantomJSPath.Name = "lblPhantomJSPath"
        Me.lblPhantomJSPath.Size = New System.Drawing.Size(86, 13)
        Me.lblPhantomJSPath.TabIndex = 8
        Me.lblPhantomJSPath.Text = "PhantomJS Path"
        '
        'txtPhantomJSPath
        '
        Me.txtPhantomJSPath.Location = New System.Drawing.Point(233, 236)
        Me.txtPhantomJSPath.Multiline = True
        Me.txtPhantomJSPath.Name = "txtPhantomJSPath"
        Me.txtPhantomJSPath.Size = New System.Drawing.Size(238, 39)
        Me.txtPhantomJSPath.TabIndex = 9
        '
        'TestToolConfig
        '
        Me.AutoScaleDimensions = New System.Drawing.SizeF(6.0!, 13.0!)
        Me.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font
        Me.ClientSize = New System.Drawing.Size(727, 543)
        Me.Controls.Add(Me.lblNote)
        Me.Controls.Add(Me.TabControl1)
        Me.Controls.Add(Me.Button1)
        Me.Controls.Add(Me.Label1)
        Me.Name = "TestToolConfig"
        Me.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen
        Me.Text = "TestToolConfig"
        Me.TabControl1.ResumeLayout(False)
        Me.TabPage1.ResumeLayout(False)
        Me.TabPage1.PerformLayout()
        Me.TabPage2.ResumeLayout(False)
        Me.TabPage2.PerformLayout()
        Me.TabPage3.ResumeLayout(False)
        Me.TabPage3.PerformLayout()
        Me.ResumeLayout(False)
        Me.PerformLayout()

    End Sub
    Friend WithEvents Label1 As System.Windows.Forms.Label
    Friend WithEvents Button1 As System.Windows.Forms.Button
    Friend WithEvents TabControl1 As System.Windows.Forms.TabControl
    Friend WithEvents TabPage1 As System.Windows.Forms.TabPage
    Friend WithEvents TabPage2 As System.Windows.Forms.TabPage
    Friend WithEvents cmbBrowser As System.Windows.Forms.ComboBox
    Friend WithEvents txtEmail As System.Windows.Forms.TextBox
    Friend WithEvents txtPassword As System.Windows.Forms.TextBox
    Friend WithEvents txtUserName As System.Windows.Forms.TextBox
    Friend WithEvents lblEmail As System.Windows.Forms.Label
    Friend WithEvents lblWidgetPage As System.Windows.Forms.Label
    Friend WithEvents lblPassword As System.Windows.Forms.Label
    Friend WithEvents lblUserName As System.Windows.Forms.Label
    Friend WithEvents lblClient As System.Windows.Forms.Label
    Friend WithEvents lblBrowser As System.Windows.Forms.Label
    Friend WithEvents lblEnv As System.Windows.Forms.Label
    Friend WithEvents lstTestCaseConfig As System.Windows.Forms.ListView
    Friend WithEvents ColumnHeader1 As System.Windows.Forms.ColumnHeader
    Friend WithEvents rdbWidget As System.Windows.Forms.RadioButton
    Friend WithEvents rdbPage As System.Windows.Forms.RadioButton
    Friend WithEvents rdbALL As System.Windows.Forms.RadioButton
    Friend WithEvents lnkUnCheckAll As System.Windows.Forms.LinkLabel
    Friend WithEvents lnkCheckAll As System.Windows.Forms.LinkLabel
    Friend WithEvents TabPage3 As System.Windows.Forms.TabPage
    Friend WithEvents cmbWidgetPageNames As System.Windows.Forms.ComboBox
    Friend WithEvents cmbEnv As System.Windows.Forms.ComboBox
    Friend WithEvents cmbClient As System.Windows.Forms.ComboBox
    Friend WithEvents txtFireFoxProfilePath As System.Windows.Forms.TextBox
    Friend WithEvents lblFireFoxProfilePath As System.Windows.Forms.Label
    Friend WithEvents txtChromeDriverPath As System.Windows.Forms.TextBox
    Friend WithEvents lblChromeDriverPath As System.Windows.Forms.Label
    Friend WithEvents txtIePath As System.Windows.Forms.TextBox
    Friend WithEvents lblIePath As System.Windows.Forms.Label
    Friend WithEvents txtChromeProfilePath As System.Windows.Forms.TextBox
    Friend WithEvents lblChromeProfilePath As System.Windows.Forms.Label
    Friend WithEvents lblNote As System.Windows.Forms.Label
    Friend WithEvents ColumnHeader2 As System.Windows.Forms.ColumnHeader
    Friend WithEvents ToolTip1 As System.Windows.Forms.ToolTip
    Friend WithEvents lblIE As System.Windows.Forms.Label
    Friend WithEvents cmbLanguage As System.Windows.Forms.ComboBox
    Friend WithEvents lblLanguage As System.Windows.Forms.Label
    Friend WithEvents cmbApp As System.Windows.Forms.ComboBox
    Friend WithEvents lblApp As System.Windows.Forms.Label
    Friend WithEvents txtPhantomJSPath As System.Windows.Forms.TextBox
    Friend WithEvents lblPhantomJSPath As System.Windows.Forms.Label
End Class
