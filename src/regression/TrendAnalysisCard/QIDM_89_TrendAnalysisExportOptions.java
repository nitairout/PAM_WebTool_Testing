package regression.TrendAnalysisCard;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.Constant;
import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * Verify CSV and PDF export options
 */
public class QIDM_89_TrendAnalysisExportOptions extends TestBase {
	LoginTC login = null;

	@Test
	public void trendAnalysisExportOptions() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Select 'Electricity', 'Gas' and 'Water' commodities for a sub-site 'PAMTest_Main Meter' under 'PAMTest_Capriata/Saiwa' site
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			getWebElementXpath("PAMTest_MainMeter_Energy").click();
			getWebElementXpath("PAMTest_MainMeter_Gas").click();
			getWebElementXpath("PAMTest_MainMeter_Water").click();
			
			// Added fixed date range
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			
			comparisionDateRange("Previous Month");
			
			refreshToLoadTheChart();
			
			Utility.moveTheScrollToTheTop();
			
			//delete all files based on name so that we will get fresh file everytime
			Utility.deleteDownloadFiles("trendAnalysis",".csv");
			
			// Select "Export Chart Data to CSV" from chart menu options to download "calendar.csv" file
			kabobMenuOptions("Export Chart Data to CSV");

			// Get the data from CSV file
			ArrayList<List<String>> csvDataToCheckTheMsg = Utility.returnNumberOfRowsFromcsv("trendAnalysis", 6);
			List<String> firstRowData = null;

			getWebElementXpath("DataTableTab").click();
			Thread.sleep(1000);

			//Convert CSV date to support chart date
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        LocalDateTime localDateTime = null;
	        DateTimeFormatter outputFormatter = null;
	        String formattedDate = null;
	        LocalDate localDate =null;
			
			// Get the data from Table data
			ArrayList<ArrayList<String>> tabledata = Utility.returnPamTableData(4);
			ArrayList<String> tableCellData = null;
			// Verified the table data and csv file data as expected
			for (int i = 0; i < tabledata.size(); i++) {
				tableCellData = tabledata.get(i);
				firstRowData = csvDataToCheckTheMsg.get(2 + i);
				Assert.assertEquals(tableCellData.get(1), firstRowData.get(1));
				Assert.assertEquals(tableCellData.get(2), firstRowData.get(2));
				Assert.assertEquals(tableCellData.get(3), firstRowData.get(3));
				tableCellData = null;
				firstRowData = null;
			}
			printLog("Verified the table data and csv data as expected");
			
			//delete all files based on name so that we will get fresh file everytime
			Utility.deleteDownloadFiles("trendAnalysis",".pdf");
			
			// Click on KabobMenu and select 'Export to PDF'.
			kabobMenuOptions("Export to PDF");			
			String fileName =Utility.latestDownLoadedFileFromDownload("trendAnalysis", "pdf");
			File fileLenth=new File(Constant.DOWNLOAD_PATH+"/"+fileName);
			if(fileLenth.length()<=0){
				throw new Exception("check if the file is not downloaded of the file doesnot contain anything");
			}
			printLog("Successfully downloaded PDF file");
			
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
