Imports System
Imports System.Data
Imports System.Data.OleDb
Imports Microsoft.Office.Interop.Excel
Imports Excel = Microsoft.Office.Interop.Excel
Imports DataTable = System.Data.DataTable
Imports System.IO
Imports System.Xml
Imports System.Collections
Imports System.Data.SqlClient
Imports System.Configuration


Public Class TestToolConfig

    Dim sheets As Excel.Sheets
    Dim app As Excel.Application = New Application()
    Dim workBook As Excel.Workbook

    Dim trackInfo As New Hashtable()
    Dim lstSafeType As New ArrayList()
    Dim prodJohnDeereSafeType As New ArrayList()
    Dim InVisibleModeSafeType As New ArrayList()
    Dim intProcessCount As Integer

    Private Sub TestToolConfig_Disposed(ByVal sender As Object, ByVal e As System.EventArgs) Handles Me.Disposed
        ' release the Excel Application object
        app.Quit()
    End Sub

    Private Sub TestToolConfig_Load(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles MyBase.Load
        'Me.WindowState = FormWindowState.Maximized

        ' select and load Resource Advisor by default
        cmbApp.SelectedItem = "Resource Advisor"
        LoadRA()

    End Sub


    Private Sub cmbApp_SelectedIndexChanged(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles cmbApp.SelectedIndexChanged

        If cmbApp.SelectedItem.ToString() = "CBMS" Then
            ' CBMS selected
            LoadCBMS()

        ElseIf cmbApp.SelectedItem.ToString() = "Resource Advisor" Then
            ' Resource Advisor selected
            LoadRA()

        End If

    End Sub

    Private Sub LoadCBMS()

        ' 1st tab

        ' Clear Environment and fill combobox
        cmbEnv.Items.Clear()
        cmbEnv.Items.Add("Track-1")
        cmbEnv.Items.Add("Track-2")
        cmbEnv.Items.Add("Track-3")
        cmbEnv.Items.Add("Track-5")
        cmbEnv.Items.Add("Staging-1")

        ' Language
        cmbLanguage.DataSource = Nothing
        cmbLanguage.Items.Clear()
        cmbLanguage.Items.Add("English (US)")
        cmbLanguage.SelectedItem = "English (US)"
        cmbLanguage.Enabled = False

        ' Browser
        cmbBrowser.Items.Clear()
        cmbBrowser.Items.Add("IE")
        cmbBrowser.SelectedItem = "IE"
        cmbBrowser.Enabled = False

        ' hide fields
        lblUserName.Visible = False : txtUserName.Visible = False
        lblPassword.Visible = False : txtPassword.Visible = False
        lblClient.Visible = False : cmbClient.Visible = False
        rdbALL.Visible = False : rdbPage.Visible = False : rdbWidget.Visible = False
        lblWidgetPage.Visible = False : cmbWidgetPageNames.Visible = False

        ' arrange fields display
        lblEmail.Location = New System.Drawing.Point(58, 179)
        txtEmail.Location = New System.Drawing.Point(216, 175)

        'lblEmailMsg.Location = New System.Drawing.Point(220, 200)
        'lblEmailMsg.Text = "Email IDs should be separated with Semi-Colon(;)."

        cmbClient.DataSource = Nothing : cmbClient.Items.Clear()
        cmbWidgetPageNames.DataSource = Nothing : cmbWidgetPageNames.Items.Clear()
        txtEmail.Text = ""

        '2nd tab
        lnkCheckAll.Visible = True : lnkUnCheckAll.Visible = True
        lstTestCaseConfig.CheckBoxes = True
        lstTestCaseConfig.Items.Clear()

        '3rd tab = hide
        TabControl1.TabPages.Remove(TabPage3)


        ' get config file path
        Dim path As String = ConfigurationManager.AppSettings("CBMS_ConfigFilePath") & _
                             ConfigurationManager.AppSettings("CBMS_ConfigFileName")

        ' Open the Excel Workbook
        workBook = app.Workbooks.Open(path, UpdateLinks:=False, ReadOnly:=False)

        ' Get the Excel Sheets
        sheets = workBook.Worksheets

        ' Fill Environment
        FillCBMSTrackInfo()

        ' Fill values from Excelsheet
        FillCBMSValuesFromExcel()

        ' Fill Test case Configuration
        FillCBMSTestCaseConfiguration()


    End Sub

    Private Sub FillCBMSTrackInfo()
        trackInfo.Clear()

        'trackInfo.Add(key, value) 
        trackInfo.Add(ConfigurationManager.AppSettings("Track-1"), "Track-1")
        trackInfo.Add(ConfigurationManager.AppSettings("Track-2"), "Track-2")
        trackInfo.Add(ConfigurationManager.AppSettings("Track-3"), "Track-3")
        trackInfo.Add(ConfigurationManager.AppSettings("Track-5"), "Track-5")
        trackInfo.Add(ConfigurationManager.AppSettings("Staging-1"), "Staging-1")

    End Sub

    Private Sub FillCBMSValuesFromExcel()
        ' Read data from Excelsheet => sheet 1
        Dim Sheet1 As Excel.Worksheet = DirectCast(sheets.Item(1), Excel.Worksheet)

        Dim eCell As Excel.Range = Sheet1.UsedRange

        ' read Environment value from excel file
        Dim strval As String = CType(eCell(2, 1), Excel.Range).Value.ToString()

        ' select Environment
        cmbEnv.SelectedItem = trackInfo.Item(CType(eCell(2, 1), Excel.Range).Value).ToString()

        ' fill EmailtoQC textbox
        Dim stremail As String = CType(eCell(2, 5), Excel.Range).Value.ToString()
        txtEmail.Text = CType(eCell(2, 5), Excel.Range).Value.ToString()

    End Sub

    ' Fill CBMS Testcase Configuration
    Private Sub FillCBMSTestCaseConfiguration()

        Dim m_xmld As XmlDocument
        Dim m_nodelist As XmlNodeList
        Dim m_node As XmlNode
        m_xmld = New XmlDocument()
        Try
            ' get xml file path
            m_xmld.Load(ConfigurationManager.AppSettings("CBMS_RunxmlPath"))

            ' read xml
            m_nodelist = m_xmld.SelectNodes("/suite/test")
            For Each m_node In m_nodelist
                Dim testCaseAttribute As String = m_node.Attributes.GetNamedItem("name").Value
                Dim testCaseDesc As String = m_node.Attributes.GetNamedItem("Description").Value
                'Dim testCaseType As String = m_node.Attributes.GetNamedItem("safetype").Value
                'Dim prodJohnDeereTestCaseTYpe As String = m_node.Attributes.GetNamedItem("ProdJohndeere").Value

                Dim lvItem As New ListViewItem(testCaseAttribute)

                lvItem.SubItems.Add("View Description")
                lvItem.ToolTipText = testCaseDesc
                lvItem.SubItems(1).ForeColor = Color.Blue
                lvItem.SubItems(1).Font = New System.Drawing.Font(lstTestCaseConfig.Font, FontStyle.Underline)
                lvItem.UseItemStyleForSubItems = False
                lstTestCaseConfig.Items.Add(lvItem)

                'If testCaseType = "unsafe" Then
                '    lstSafeType.Add(lvItem)
                'End If

                'If prodJohnDeereTestCaseTYpe = "unsafe" Then
                '    prodJohnDeereSafeType.Add(lvItem)
                'End If
            Next

            For Each listitem As ListViewItem In lstTestCaseConfig.Items
                listitem.Checked = True
            Next

            lstTestCaseConfig.AutoResizeColumn(0, ColumnHeaderAutoResizeStyle.ColumnContent)
            'lstTestCaseConfig.AutoResizeColumn(1, ColumnHeaderAutoResizeStyle.ColumnContent)

        Catch ex As Exception
            MsgBox("Error Occured While Adding Test Case Names from Run.xml")
        End Try

        'Dim m_xmld As XmlDocument
        'Dim m_nodelist As XmlNodeList
        'Dim m_node As XmlNode
        'm_xmld = New XmlDocument()
        'Try
        '    ' get xml file path
        '    m_xmld.Load(ConfigurationManager.AppSettings("CBMS_RunxmlPath"))

        '    ' read xml  ' Dim path = "/testcase/test-suite/precondition/wait"
        '    m_nodelist = m_xmld.ChildNodes(1).ChildNodes(1).ChildNodes  'm_nodelist = m_xmld.SelectNodes(path)

        '    For Each m_node In m_nodelist

        '        Dim xe As XmlElement = m_node("wait")
        '        Dim testCaseAttribute As String = xe.Attributes.GetNamedItem("functionId").Value

        '        'Dim testCaseAttribute As String = m_node.Attributes.GetNamedItem("functionId").Value

        '        Dim lvItem As New ListViewItem(testCaseAttribute)

        '        lvItem.SubItems.Add("View Description")
        '        lvItem.ToolTipText = testCaseAttribute
        '        lvItem.SubItems(1).ForeColor = Color.Blue
        '        lvItem.SubItems(1).Font = New System.Drawing.Font(lstTestCaseConfig.Font, FontStyle.Underline)
        '        lvItem.UseItemStyleForSubItems = False

        '        lstTestCaseConfig.Items.Add(lvItem)

        '    Next

        '    lstTestCaseConfig.AutoResizeColumn(0, ColumnHeaderAutoResizeStyle.ColumnContent)
        '    'lstTestCaseConfig.AutoResizeColumn(1, ColumnHeaderAutoResizeStyle.ColumnContent)

        'Catch ex As Exception
        '    MsgBox("Error Occured While Adding Test Case Names from Suite.xml")
        'End Try
    End Sub


    Private Sub LoadRA()
        '1st tab
        Try
            ' Clear Environment and fill combobox
            cmbEnv.Items.Clear()
            cmbEnv.Items.Add("Track1")
            cmbEnv.Items.Add("Track2")
            cmbEnv.Items.Add("Track3")
            cmbEnv.Items.Add("Track5")
            cmbEnv.Items.Add("Staging")
            cmbEnv.Items.Add("Staging2")
            cmbEnv.Items.Add("Production")
            cmbEnv.Items.Add("Production - John Deere")

            ' Language
            cmbLanguage.DataSource = Nothing
            cmbLanguage.Items.Clear()
            cmbLanguage.Enabled = True

            ' Browser
            cmbBrowser.Items.Clear()
            cmbBrowser.Items.Add("Chrome")
            cmbBrowser.Items.Add("FireFox")
            cmbBrowser.Items.Add("IE")
            cmbBrowser.Items.Add("Invisible mode")
            cmbBrowser.Enabled = True

            ' hide fields
            lblUserName.Visible = True : txtUserName.Visible = True
            lblPassword.Visible = True : txtPassword.Visible = True
            lblClient.Visible = True : cmbClient.Visible = True
            rdbALL.Visible = True : rdbPage.Visible = True : rdbWidget.Visible = True
            lblWidgetPage.Visible = True : cmbWidgetPageNames.Visible = True

            ' arrange fields display
            lblEmail.Location = New System.Drawing.Point(58, 337)
            txtEmail.Location = New System.Drawing.Point(216, 333)

            'lblEmailMsg.Location = New System.Drawing.Point(220, 358)
            'lblEmailMsg.Text = "Email IDs should be separated with Comma(,)."

            '2nd tab = move test case configuration out of this funstion
            lnkCheckAll.Visible = True : lnkUnCheckAll.Visible = True
            lstTestCaseConfig.Items.Clear()
            lstTestCaseConfig.CheckBoxes = True


            '3rd tab = show
            If Not TabControl1.TabPages.Contains(TabPage3) Then
                TabControl1.TabPages.Add(TabPage3)
            End If

            ' get config file path
            Dim path As String = ConfigurationManager.AppSettings("ConfigFilePath") & _
                                 ConfigurationManager.AppSettings("ConfigFileName")

            ' Open the Excel Workbook
            workBook = app.Workbooks.Open(path, UpdateLinks:=False, ReadOnly:=False)

            ' Get the Excel Sheets
            sheets = workBook.Worksheets

            ' Fill Environment
            FillTrackInfo()

            ' Fill values from Excelsheet
            FillValuesFromExcel()

            ' Fill Test case Configuration
            FillTestCaseConfiguration()

        Catch ex As Exception
            MsgBox(ex.Message)
        End Try
    End Sub

    ' Fill Environment
    Private Sub FillTrackInfo()
        Try

            trackInfo.Clear()

            trackInfo.Add(ConfigurationManager.AppSettings("Track1"), "Track1")
            trackInfo.Add(ConfigurationManager.AppSettings("Track2"), "Track2")
            trackInfo.Add(ConfigurationManager.AppSettings("Track3"), "Track3")
            trackInfo.Add(ConfigurationManager.AppSettings("Track5"), "Track5")
            trackInfo.Add(ConfigurationManager.AppSettings("Staging"), "Staging")
            trackInfo.Add(ConfigurationManager.AppSettings("Staging2"), "Staging2")
            trackInfo.Add(ConfigurationManager.AppSettings("Production"), "Production")
            trackInfo.Add(ConfigurationManager.AppSettings("Production - John Deere"), "Production - John Deere")

        Catch ex As Exception
            MsgBox(ex.Message)
        End Try
    End Sub

    ' Fill values from Excelsheet
    Private Sub FillValuesFromExcel()
        Try
            ' Read data from Excelsheet => sheet 1
            Dim inputSheet As Excel.Worksheet = DirectCast(sheets.Item(1), Excel.Worksheet)

            ' select Environment
            cmbEnv.SelectedItem = trackInfo.Item(DirectCast(inputSheet.Cells(3, 1), Excel.Range).Value).ToString()

            ' select Browser
            cmbBrowser.Text = DirectCast(inputSheet.Cells(3, 4), Excel.Range).Value

            ' get username / password
            txtUserName.Text = DirectCast(inputSheet.Cells(3, 2), Excel.Range).Value

            ' get username / password from config file
            If cmbEnv.SelectedItem.ToString() = "Production" Then
                txtUserName.Text = ConfigurationManager.AppSettings("Production_UserName")
                txtPassword.Text = ConfigurationManager.AppSettings("Production_Password")

            ElseIf cmbEnv.SelectedItem.ToString() = "Production - John Deere" Then
                txtUserName.Text = ConfigurationManager.AppSettings("ProductionJohnDeere_UserName")
                txtPassword.Text = ConfigurationManager.AppSettings("ProductionJohnDeere_PassWord")

            Else
                ' get username / password from excel file
                txtPassword.Text = DirectCast(inputSheet.Cells(3, 3), Excel.Range).Value
                txtUserName.Text = DirectCast(inputSheet.Cells(3, 2), Excel.Range).Value
            End If

            ' fill EmailtoQC textbox
            txtEmail.Text = DirectCast(inputSheet.Cells(3, 6), Excel.Range).Value

            ' fill client dropdown
            FillClients(CType(inputSheet.Cells(3, 5), Excel.Range).Value)

            ' fill widgets/pages dropdown based on radio button selection (All / Page / Widget) => read value from excel
            FillPageNames(DirectCast(inputSheet.Cells(7, 1), Excel.Range).Value.ToString)

            ' fill language dropdown
            FillLanguageDropDown(CType(inputSheet.Cells(3, 7), Excel.Range).Value)

            ' Reading Tech Config Values = 3rd tab
            Dim techConfig As Excel.Worksheet = DirectCast(sheets.Item(2), Excel.Worksheet)
            ' Fill text box values from excel
            txtChromeDriverPath.Text = DirectCast(techConfig.Cells(2, 1), Excel.Range).Value
            txtFireFoxProfilePath.Text = DirectCast(techConfig.Cells(2, 2), Excel.Range).Value
            txtChromeProfilePath.Text = DirectCast(techConfig.Cells(2, 3), Excel.Range).Value
            txtIePath.Text = DirectCast(techConfig.Cells(2, 4), Excel.Range).Value
            txtPhantomJSPath.Text = DirectCast(techConfig.Cells(2, 5), Excel.Range).Value
        Catch ex As Exception
            MsgBox(ex.Message)
        End Try
    End Sub

    ' Fill Client combobox
    Private Sub FillClients(Optional ByVal StrValue As String = "")

        Dim dtClient As New DataTable
        Dim dsClient As New DataSet
        Try
            Dim conClient As New SqlConnection(GetConnectionString())
            Dim adpClient As New SqlDataAdapter("Get_AllClient_For_ClientHier", conClient)
            adpClient.SelectCommand.CommandType = CommandType.StoredProcedure
            adpClient.Fill(dsClient, "Clients")
            dtClient = dsClient.Tables("Clients")

            ' Fill Client combobox
            cmbClient.DataSource = dtClient
            cmbClient.DisplayMember = "client_name"
            cmbClient.ValueMember = "client_id"

            If cmbEnv.SelectedItem.ToString() = "Production" Then
                cmbClient.SelectedValue = "11278"
            ElseIf StrValue.Length > 0 Then
                ' select Client
                cmbClient.SelectedIndex = cmbClient.FindStringExact(StrValue)
                '(cmbClient.Items.IndexOf(StrValue))
            End If


        Catch ex As Exception
            MsgBox("Error Occured While Accessing Client Details")
        End Try

    End Sub

    ' Get Connection String base on Environment selected
    Private Function GetConnectionString()

        Select Case cmbEnv.SelectedItem.ToString()
            Case "Track1"
                Return ConfigurationManager.AppSettings("Track1_ConnectionString")
            Case "Track2"
                Return ConfigurationManager.AppSettings("Track2_ConnectionString")
            Case "Track3"
                Return ConfigurationManager.AppSettings("Track3_ConnectionString")
            Case "Track5"
                Return ConfigurationManager.AppSettings("Track5_ConnectionString")
            Case "Staging"
                Return ConfigurationManager.AppSettings("Staging_ConnectionString")
            Case "Staging2"
                Return ConfigurationManager.AppSettings("Staging2_ConnectionString")
            Case "Production"
                Return ConfigurationManager.AppSettings("Staging_ConnectionString")
            Case "Production - John Deere"
                Return ConfigurationManager.AppSettings("Track5_ConnectionString")
        End Select

    End Function

    ' Fill Widget/Pages dropdown
    Private Sub FillPageNames(Optional ByVal StrValue As String = "")
        Dim widgetANDPageNAmes As New Microsoft.Office.Interop.Excel.Worksheet

        Try
            If sheets IsNot Nothing Then
                ' getsheet
                widgetANDPageNAmes = DirectCast(sheets.Item(3), Excel.Worksheet)
            End If

            Dim lstPageNames As New ArrayList

            ' fill cmbWidgetPageNames dropdown
            Select Case StrValue
                Case "All"
                    cmbWidgetPageNames.Enabled = False
                    cmbWidgetPageNames.DataSource = Nothing
                    cmbWidgetPageNames.Items.Clear()
                    cmbWidgetPageNames.Items.Add("All")
                    cmbWidgetPageNames.SelectedIndex = 0

                Case "Page"
                    cmbWidgetPageNames.Enabled = True
                    For i As Integer = 3 To widgetANDPageNAmes.UsedRange.Rows.Count
                        If DirectCast(widgetANDPageNAmes.Cells(i, 1), Excel.Range).Value IsNot Nothing Then
                            lstPageNames.Add(DirectCast(widgetANDPageNAmes.Cells(i, 1), Excel.Range).Value)
                        End If
                    Next
                    cmbWidgetPageNames.DataSource = lstPageNames
                    cmbWidgetPageNames.SelectedItem = 0

                Case "Widget"
                    cmbWidgetPageNames.Enabled = True
                    For i As Integer = 3 To widgetANDPageNAmes.UsedRange.Rows.Count
                        If DirectCast(widgetANDPageNAmes.Cells(i, 2), Excel.Range).Value IsNot Nothing Then
                            lstPageNames.Add(DirectCast(widgetANDPageNAmes.Cells(i, 2), Excel.Range).Value)
                        End If
                    Next
                    cmbWidgetPageNames.DataSource = lstPageNames
                    cmbWidgetPageNames.SelectedItem = 0

                Case Else
                    cmbWidgetPageNames.Enabled = True
                    cmbWidgetPageNames.DataSource = Nothing
                    Dim found As Boolean = False
                    For i As Integer = 3 To widgetANDPageNAmes.UsedRange.Rows.Count
                        If DirectCast(widgetANDPageNAmes.Cells(i, 1), Excel.Range).Value IsNot Nothing Then
                            lstPageNames.Add(DirectCast(widgetANDPageNAmes.Cells(i, 1), Excel.Range).Value)
                            If StrValue = DirectCast(widgetANDPageNAmes.Cells(i, 1), Excel.Range).Value Then
                                found = True
                            End If
                        End If
                    Next
                    If found Then
                        cmbWidgetPageNames.DataSource = lstPageNames
                        cmbWidgetPageNames.SelectedIndex = cmbWidgetPageNames.Items.IndexOf(StrValue)
                        RemoveHandler rdbPage.CheckedChanged, AddressOf rdbPage_CheckedChanged
                        rdbPage.Checked = True
                        AddHandler rdbPage.CheckedChanged, AddressOf rdbPage_CheckedChanged
                    Else
                        lstPageNames = New ArrayList
                        For i As Integer = 3 To widgetANDPageNAmes.UsedRange.Rows.Count
                            If DirectCast(widgetANDPageNAmes.Cells(i, 2), Excel.Range).Value IsNot Nothing Then
                                lstPageNames.Add(DirectCast(widgetANDPageNAmes.Cells(i, 2), Excel.Range).Value)
                            End If
                        Next
                        cmbWidgetPageNames.DataSource = lstPageNames
                        cmbWidgetPageNames.SelectedIndex = cmbWidgetPageNames.Items.IndexOf(StrValue)
                        RemoveHandler rdbWidget.CheckedChanged, AddressOf rdbWidget_CheckedChanged
                        rdbWidget.Checked = True
                        AddHandler rdbWidget.CheckedChanged, AddressOf rdbWidget_CheckedChanged
                    End If
            End Select
        Catch ex As Exception
            MsgBox("Error Occured While Adding Page Names")
        End Try
    End Sub

    ' To Fill the Language Dropdown
    Private Sub FillLanguageDropDown(Optional ByVal StrValue As String = "")
        Dim dsLanguage As New DataSet
        ' Read Language xml file
        Try
            dsLanguage.ReadXml("Languages.xml")
            cmbLanguage.DataSource = dsLanguage.Tables(0)
            cmbLanguage.DisplayMember = "Language_Text"
            cmbLanguage.ValueMember = "Language_Value"

            If cmbLanguage.Items.Count > 0 And StrValue.Length > 0 Then
                cmbLanguage.SelectedValue = StrValue
            End If

        Catch ex As Exception
            MsgBox("Error occured while Filling languages")
        End Try
    End Sub

    ' Fill Testcase Configuration
    Private Sub FillTestCaseConfiguration()

        Dim m_xmld As XmlDocument
        Dim m_nodelist As XmlNodeList
        Dim m_node As XmlNode
        m_xmld = New XmlDocument()
        Try
            ' get xml file path
            m_xmld.Load(ConfigurationManager.AppSettings("RunxmlPath"))

            ' read xml
            m_nodelist = m_xmld.SelectNodes("/suite/test")
            For Each m_node In m_nodelist
                Dim testCaseAttribute As String = m_node.Attributes.GetNamedItem("name").Value
                Dim testCaseDesc As String = m_node.Attributes.GetNamedItem("Description").Value
                Dim testCaseType As String = m_node.Attributes.GetNamedItem("safetype").Value
                Dim prodJohnDeereTestCaseType As String = m_node.Attributes.GetNamedItem("ProdJohndeere").Value
                Dim InVisibleModeTestCaseType As String = m_node.Attributes.GetNamedItem("InvisibleMode").Value

                Dim lvItem As New ListViewItem(testCaseAttribute)

                lvItem.SubItems.Add("View Description")
                lvItem.ToolTipText = testCaseDesc
                lvItem.SubItems(1).ForeColor = Color.Blue
                lvItem.SubItems(1).Font = New System.Drawing.Font(lstTestCaseConfig.Font, FontStyle.Underline)
                lvItem.UseItemStyleForSubItems = False
                lstTestCaseConfig.Items.Add(lvItem)
                If intProcessCount = 0 Then
                    If testCaseType = "unsafe" Then
                        lstSafeType.Add(lvItem)
                    End If

                    If prodJohnDeereTestCaseType = "unsafe" Then
                        prodJohnDeereSafeType.Add(lvItem)
                    End If

                    If InVisibleModeTestCaseType = "unsafe" Then
                        InVisibleModeSafeType.Add(lvItem)
                    End If
                End If
            Next

            For Each listitem As ListViewItem In lstTestCaseConfig.Items
                listitem.Checked = True
            Next

            lstTestCaseConfig.AutoResizeColumn(0, ColumnHeaderAutoResizeStyle.ColumnContent)
            'lstTestCaseConfig.AutoResizeColumn(1, ColumnHeaderAutoResizeStyle.ColumnContent)
            intProcessCount = intProcessCount + 1
        Catch ex As Exception
            MsgBox("Error Occured While Adding Test Case Names from Run.xml")
        End Try
    End Sub


    ' Environment selected index changed
    Private Sub cmbEnv_SelectedIndexChanged(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles cmbEnv.SelectedIndexChanged

        If cmbApp.SelectedItem.ToString() = "CBMS" Then
            ' CBMS selected

        ElseIf cmbApp.SelectedItem.ToString() = "Resource Advisor" Then
            ' Resource Advisor selected

            If cmbEnv.SelectedItem.ToString() = "Production" Then
                cmbClient.Enabled = False
                cmbClient.SelectedValue = "11278" 'Nestle Cookies
                txtUserName.Text = ConfigurationManager.AppSettings("Production_UserName")
                txtUserName.Enabled = False
                txtPassword.Text = ConfigurationManager.AppSettings("Production_Password")
                txtPassword.Enabled = False

                If cmbBrowser.Text = "Invisible mode" Then
                    disableInVisibleModeUnSafeTestCases()
                Else
                    disableProductionUnSafeTestCases()
                End If
            ElseIf cmbEnv.SelectedItem.ToString() = "Production - John Deere" Then
                cmbClient.Enabled = False
                cmbClient.SelectedValue = "12037" 'Deere & Company
                If cmbClient.SelectedValue Is Nothing Then 'checking whether required client details are available in the DB or not.if not,fetch details from again from DB.
                    FillClients()
                    cmbClient.SelectedValue = "12037" 'Deere & Company
                End If
                txtUserName.Text = ConfigurationManager.AppSettings("ProductionJohnDeere_UserName")
                txtUserName.Enabled = False
                txtPassword.Text = ConfigurationManager.AppSettings("ProductionJohnDeere_PassWord")
                txtPassword.Enabled = False
                If cmbBrowser.Text = "Invisible mode" Then
                    disableInVisibleModeUnSafeTestCases()
                Else
                    disableProdJohnDeereUnSafeTestCases()
                End If
            Else
                txtUserName.Enabled = True
                txtPassword.Enabled = True
                cmbClient.Enabled = True
                FillClients()
                Dim inputSheet As Excel.Worksheet = DirectCast(sheets.Item(1), Excel.Worksheet)
                txtPassword.Text = DirectCast(inputSheet.Cells(3, 3), Excel.Range).Value
                txtUserName.Text = DirectCast(inputSheet.Cells(3, 2), Excel.Range).Value
                If cmbBrowser.Text = "Invisible mode" Then
                    disableInVisibleModeUnSafeTestCases()
                Else
                    EnableTestCases()
                End If

            End If

        End If

    End Sub

    Private Sub disableProductionUnSafeTestCases()
        If lstSafeType.Count >= 1 Then
            For Each item In lstSafeType
                lstTestCaseConfig.Items(lstTestCaseConfig.Items.IndexOf(item)).Checked = False
                lstTestCaseConfig.Items(lstTestCaseConfig.Items.IndexOf(item)).ForeColor = Color.Gray
                lstTestCaseConfig.Items(lstTestCaseConfig.Items.IndexOf(item)).Focused = False
            Next
        Else
            EnableTestCases()
        End If
    End Sub

    Private Sub disableProdJohnDeereUnSafeTestCases()
        If prodJohnDeereSafeType.Count >= 1 Then
            For Each item In prodJohnDeereSafeType

                Dim findItem As ListViewItem = lstTestCaseConfig.FindItemWithText(item.Text)
                If findItem IsNot Nothing Then
                    findItem.Checked = False
                    findItem.ForeColor = Color.Gray
                    findItem.Focused = False
                End If

                'lstTestCaseConfig.Items(lstTestCaseConfig.Items.IndexOf(item)).Checked = False
                'lstTestCaseConfig.Items(lstTestCaseConfig.Items.IndexOf(item)).ForeColor = Color.Gray
                'lstTestCaseConfig.Items(lstTestCaseConfig.Items.IndexOf(item)).Focused = False
            Next
        Else
            EnableTestCases()
        End If
    End Sub
    Private Sub disableInVisibleModeUnSafeTestCases()
        If InVisibleModeSafeType.Count >= 1 Then
            For Each item In InVisibleModeSafeType
                Dim findItem As ListViewItem = lstTestCaseConfig.FindItemWithText(item.Text, True, 0, False)
                If findItem IsNot Nothing Then
                    findItem.Checked = False
                    findItem.ForeColor = Color.Gray
                    findItem.Focused = False
                End If
            Next
        Else
            EnableTestCases()
        End If
    End Sub
    Private Sub EnableTestCases()
        For Each listitem As ListViewItem In lstTestCaseConfig.Items
            listitem.Checked = True
            listitem.ForeColor = Color.Black
            listitem.Focused = True
        Next
    End Sub


    ' Browser selection changed
    Private Sub cmbBrowser_SelectedIndexChanged(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles cmbBrowser.SelectedIndexChanged

        If cmbApp.SelectedItem.ToString() = "CBMS" Then
            ' CBMS selected
            lblIE.Visible = False

        ElseIf cmbApp.SelectedItem.ToString() = "Resource Advisor" Then
            ' Resource Advisor selected
            Select Case cmbBrowser.Text
                Case "IE"
                    lblIE.Visible = True
                    EnableTestCases()
                Case "FireFox"
                    lblIE.Visible = False
                    EnableTestCases()
                Case "Chrome"
                    lblIE.Visible = False
                    EnableTestCases()
                Case "Invisible mode"
                    lblIE.Visible = False
                    disableInVisibleModeUnSafeTestCases()
            End Select

        End If

    End Sub


    ' Radio button ALL
    Private Sub rdbALL_CheckedChanged(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles rdbALL.CheckedChanged
        If rdbALL.Checked Then
            FillPageNames("All")
        End If
    End Sub

    ' Radio button Page
    Private Sub rdbPage_CheckedChanged(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles rdbPage.CheckedChanged
        If rdbPage.Checked Then
            FillPageNames("Page")
        End If
    End Sub

    ' Radio button Widget
    Private Sub rdbWidget_CheckedChanged(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles rdbWidget.CheckedChanged
        If rdbWidget.Checked Then
            FillPageNames("Widget")
        End If
    End Sub


    ' List TestCaseConfiguration
    Private Sub lstTestCaseConfig_ItemChecked(ByVal sender As Object, ByVal e As System.Windows.Forms.ItemCheckedEventArgs) Handles lstTestCaseConfig.ItemChecked

        If e.Item.Checked = True And cmbEnv.SelectedItem.ToString() = "Production" And Not cmbBrowser.Text = "Invisible mode" Then
            For Each item As ListViewItem In lstSafeType
                If e.Item.SubItems(0).Text.Contains(item.Text) Then
                    e.Item.Checked = False
                    e.Item.ForeColor = Color.Gray
                End If
            Next

        ElseIf e.Item.Checked = True And cmbEnv.SelectedItem.ToString() = "Production - John Deere" And Not cmbBrowser.Text = "Invisible mode" Then
            For Each item As ListViewItem In prodJohnDeereSafeType
                If e.Item.SubItems(0).Text.Contains(item.Text) Then
                    e.Item.Checked = False
                    e.Item.ForeColor = Color.Gray
                End If
            Next
        ElseIf e.Item.Checked = True And cmbBrowser.Text = "Invisible mode" Then
            For Each item As ListViewItem In InVisibleModeSafeType
                If e.Item.SubItems(0).Text.Contains(item.Text) Then
                    e.Item.Checked = False
                    e.Item.ForeColor = Color.Gray
                End If
            Next
        Else
            e.Item.ForeColor = Color.Black
        End If
    End Sub

    ' List Mouse over
    Private Sub lstTestCaseConfig_ItemMouseHover(ByVal sender As Object, ByVal e As System.Windows.Forms.ListViewItemMouseHoverEventArgs) Handles lstTestCaseConfig.ItemMouseHover
        lstTestCaseConfig.Items(e.Item.Index).Selected = True
        ToolTip1.Active = False

    End Sub

    ' List Mouse Click
    Private Sub lstTestCaseConfig_MouseClick(ByVal sender As Object, ByVal e As System.Windows.Forms.MouseEventArgs) Handles lstTestCaseConfig.MouseClick
        Dim LVHit As ListViewHitTestInfo
        Dim ToolTipText As String = ""
        Dim CurrentPosition As Integer = 0
        Dim LastBreakpoint As Integer = 0
        'Set ToolTipLen​gthPerLine to the size the tool tip should display.
        'It will break the text at that length, at the next space, and add linefeeds
        Dim ToolTipLengthPerLine As Integer = 70
        ToolTip1.Active = True
        Try
            LVHit = lstTestCaseConfig.HitTest(e.X, e.Y)

            If LVHit IsNot Nothing Then
                If LVHit.SubItem IsNot Nothing AndAlso LVHit.SubItem.Text = "View Description" Then
                    ToolTipText = LVHit.Item.ToolTipText
                    If LVHit.Item.Text = "BussinessAnalytics" Then
                        ToolTip1.SetToolTip(lstTestCaseConfig, ToolTipText)
                        Return
                    End If
                    While CurrentPosition + ToolTipLengthPerLine < ToolTipText.Length
                        LastBreakpoint = ToolTipText.Substring(CurrentPosition, ToolTipLengthPerLine).LastIndexOf(" ")
                        ToolTipText = ToolTipText.Insert(CurrentPosition + LastBreakpoint + 1, vbCrLf)
                        CurrentPosition += LastBreakpoint + 3
                    End While
                End If
            End If
            ToolTip1.SetToolTip(lstTestCaseConfig, ToolTipText)
        Catch
            ToolTip1.SetToolTip(lstTestCaseConfig, "")
        End Try
    End Sub

    ' Check All
    Private Sub lnkCheckAll_LinkClicked(ByVal sender As System.Object, ByVal e As System.Windows.Forms.LinkLabelLinkClickedEventArgs) Handles lnkCheckAll.LinkClicked
        For Each listitem As ListViewItem In lstTestCaseConfig.Items
            listitem.Checked = True
        Next
    End Sub

    ' Uncheck All
    Private Sub lnkUnCheckAll_LinkClicked(ByVal sender As System.Object, ByVal e As System.Windows.Forms.LinkLabelLinkClickedEventArgs) Handles lnkUnCheckAll.LinkClicked
        For Each listitem As ListViewItem In lstTestCaseConfig.Items
            listitem.Checked = False
        Next
    End Sub


    Private Sub getsheet()
        Dim path As String = ConfigurationManager.AppSettings("ConfigFilePath") & ConfigurationManager.AppSettings("ConfigFileName")
        app = New Application()
        ' Open the Excel Workbook
        workBook = app.Workbooks.Open(path, UpdateLinks:=False, ReadOnly:=False)
        ' Get the Excel Sheets
        sheets = workBook.Worksheets
    End Sub


    ' Save and Run
    Private Sub Button1_Click(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles Button1.Click

        If cmbEnv.Text = "" Then
            MsgBox("Please Select Environment detail")
            Exit Sub
        End If
        If cmbBrowser.Text = "" Then
            MsgBox("Please Select Browser")
            Exit Sub
        End If

        If cmbApp.SelectedItem.ToString() = "CBMS" Then
            ' CBMS selected
            Try
                Dim Sheet1 As Microsoft.Office.Interop.Excel.Worksheet
                Sheet1 = CType(workBook.Sheets(1), Excel.Worksheet)

                CType(Sheet1.Cells(2, 1), Excel.Range).Value = ConfigurationManager.AppSettings(cmbEnv.SelectedItem.ToString())
                'CType(Sheet1.Cells(2, 2), Excel.Range).Value = ""
                'CType(Sheet1.Cells(2, 3), Excel.Range).Value = ""
                'CType(Sheet1.Cells(2, 4), Excel.Range).Value = ""
                CType(Sheet1.Cells(2, 5), Excel.Range).Value = txtEmail.Text

                app.DisplayAlerts = False
                ' workBook.Save()

                Dim saveFilePath As String
                saveFilePath = ConfigurationManager.AppSettings("CBMS_ConfigFilePath") & _
                               ConfigurationManager.AppSettings("CBMS_ConfigFileName")
                workBook.SaveAs(saveFilePath)
                'workBook.SaveAs(saveFilePath, Excel.XlFileFormat.xlCSVWindows, Type.Missing, Type.Missing, _
                'False, False, Excel.XlSaveAsAccessMode.xlExclusive, Type.Missing, Type.Missing, _
                'Type.Missing, Type.Missing, Type.Missing)

                app.DisplayAlerts = True
                workBook.Close(False)

                app.Quit()

                Dim strtestnames As String = ""

                For Each it As ListViewItem In lstTestCaseConfig.CheckedItems
                    strtestnames = strtestnames & "," & it.SubItems(0).Text
                Next

                'RunCommandCom("ant Suite report_email -Dtest.names=""" & strtestnames.Substring(1) & """ –Demail.report.to=""" & txtEmail.Text & """", "", _
                '              True, ConfigurationManager.AppSettings("CBMS_ANTPath"))

                'RunCommandCom(ConfigurationManager.AppSettings("CBMS_BuildCmd") & " report_email -Dtest.names=""" & strtestnames.Substring(1) & """ –Demail.report.to=""" & txtEmail.Text & """", "", _
                ' True, ConfigurationManager.AppSettings("CBMS_ANTPath"))

                RunCommandCom("ant report_email -Dtest.names=""" & strtestnames.Substring(1) & """ –Demail.report.to=""" & txtEmail.Text & """", "", _
                              True, ConfigurationManager.AppSettings("CBMS_ANTPath"))

                ' load CBMS
                'LoadCBMS()

            Catch ex As Exception
                MsgBox("Error Occured While Creating Report")
            End Try

        ElseIf cmbApp.SelectedItem.ToString() = "Resource Advisor" Then
            ' Resource Advisor selected

            If lstTestCaseConfig.CheckedItems.Count = 0 Then
                MsgBox("Please Select Test cases to run")
                Exit Sub
            End If

            Try
                Dim Sheet1 As Microsoft.Office.Interop.Excel.Worksheet
                Sheet1 = CType(workBook.Sheets(1), Excel.Worksheet)

                CType(Sheet1.Cells(3, 1), Excel.Range).Value = ConfigurationManager.AppSettings(cmbEnv.SelectedItem.ToString())
                CType(Sheet1.Cells(3, 2), Excel.Range).Value = txtUserName.Text
                CType(Sheet1.Cells(3, 3), Excel.Range).Value = txtPassword.Text
                CType(Sheet1.Cells(3, 4), Excel.Range).Value = cmbBrowser.SelectedItem.ToString()
                CType(Sheet1.Cells(3, 5), Excel.Range).Value = cmbClient.Text
                CType(Sheet1.Cells(3, 6), Excel.Range).Value = txtEmail.Text
                CType(Sheet1.Cells(3, 7), Excel.Range).Value = cmbLanguage.SelectedValue
                CType(Sheet1.Cells(7, 1), Excel.Range).Value = cmbWidgetPageNames.Text

                app.DisplayAlerts = False
                'workBook.Save()

                Dim saveFilePath As String
                saveFilePath = ConfigurationManager.AppSettings("ConfigFilePath") & _
                               ConfigurationManager.AppSettings("ConfigFileName")
                workBook.SaveAs(saveFilePath)
                'workBook.SaveAs(saveFilePath, Excel.XlFileFormat.xlWorkbookDefault, Type.Missing, Type.Missing, _
                '                False, False, Excel.XlSaveAsAccessMode.xlNoChange, Type.Missing, Type.Missing, _
                '                Type.Missing, Type.Missing, Type.Missing)

                app.DisplayAlerts = True
                workBook.Close()
                app.Quit()

                Dim strtestnames As String = ""

                For Each it As ListViewItem In lstTestCaseConfig.CheckedItems
                    strtestnames = strtestnames & "," & it.SubItems(0).Text
                Next

                RunCommandCom("ant report_email -Dtest.names=""" & strtestnames.Substring(1) & """ –Demail.report.to=""" & txtEmail.Text & """", "", _
                             True, ConfigurationManager.AppSettings("ANTPath"))

                'RunCommandCom("ant run -Dtest.names='" & strtestnames.Substring(1) & "'", "", True, System.Configuration.ConfigurationSettings.AppSettings("ANTPath"))
                'RunCommandCom("ping google.com -t", "", True, "c:")
                'RunCommandCom("ping google.com", "", True, "c:")
                'RunCommandCom("cd Projects\Summit Automated Test Suite", "", True)
                'RunCommandCom("copy run.xml run22.ml", "", True)
                'RunCommandCom("ant run -Dtest.names='" & strtestnames.Substring(1) & "'", "", True)
                'Shell(System.Configuration.ConfigurationSettings.AppSettings("ANTPath") & "ant run -Dtest.names='" & strtestnames.Substring(1) & "'", AppWinStyle.NormalFocus)
                'RunCommandCom("ant report_email", "", True, System.Configuration.ConfigurationSettings.AppSettings("ANTPath"))

                getsheet()
                ' load Resource Advisor
                'LoadRA()

            Catch ex As Exception
                MsgBox("Error Occured While Creating Report")
            End Try

        End If

    End Sub


    Shared Sub RunCommandCom(ByVal command As String, ByVal arguments As String, ByVal permanent As Boolean, ByVal StartDirectory As String)

        Dim p As Process = New Process()
        Dim pi As ProcessStartInfo = New ProcessStartInfo()
        pi.Arguments = " " + If(permanent = True, "/K", "/C") + " " + command + " " + arguments
        pi.FileName = "cmd.exe"
        pi.WorkingDirectory = StartDirectory
        pi.WindowStyle = ProcessWindowStyle.Normal
        p.StartInfo = pi
        p.Start()
        If ConfigurationManager.AppSettings("ExitAfterCalling") Then
            System.Threading.Thread.Sleep(ConfigurationManager.AppSettings("SleepTime"))
            p.Kill()
        End If

    End Sub

End Class
