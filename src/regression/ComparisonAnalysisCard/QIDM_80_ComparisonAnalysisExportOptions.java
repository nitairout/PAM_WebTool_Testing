package regression.ComparisonAnalysisCard;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.Constant;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies the Export options available for the Comparison Analysis which includes exporting to CSV and PDF. 
 */

public class QIDM_80_ComparisonAnalysisExportOptions extends TestBase {

	LoginTC login = null;

	@Test
	public void comparisonAnalysisExportOptions() throws Throwable {
		try {

			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();

			// Navigate to Comparison Analysis Card
			goToPAMCard("ComparisonAnalysisCard");

			// Search for Site'PAMTest_Capriata/Saiwa' and click on Electric Measurement
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			refreshToLoadTheChart();
			// Search for Site'PAMTest_Herentals, Biscuits' and click on Electric Measurement
			searchSiteInLocationList(testData.getProperty("PAMTestHerentalsBiscuits"));
			getWebElementXpath("PAMTest_HerentalsBiscuits_Energy").click();

			refreshToLoadTheChart();
			
			//Verified the sites on the chart
			String[] sitesList = getWebElementXpath("ChartXaxisLabels").getText().split("\\n");
			ArrayList<String> actualXaxisLabels = new ArrayList<String>();
			actualXaxisLabels.add(sitesList[0]);
			actualXaxisLabels.add(sitesList[1]);
			
			ArrayList<String> expectedXaxisLabels = new ArrayList<String>();
			expectedXaxisLabels.add("PAMTest_Herentals, Biscuits");
			expectedXaxisLabels.add(testData.getProperty("PAMTestCapriataSaiwa"));
			
			Collections.sort(actualXaxisLabels);
			boolean flag;
			flag = actualXaxisLabels.containsAll(expectedXaxisLabels);
			Assert.assertTrue(flag);			
			printLog("Verified the sites on the chart as "+sitesList);

			//Delete "comparisonAnalysis.csv" file if exists
			Utility.deleteDownloadFiles("comparisonAnalysis", ".csv");

			//Select "Export Chart Data to CSV" from chart menu options to download "comparisonAnalysis.csv" file
			kabobMenuOptions("Export Chart Data to CSV");
			
			//Get the data from CSV file
			ArrayList<List<String>> csvDataToCheckTheMsg = Utility.returnNumberOfRowsFromcsv("comparisonAnalysis", 4);
			List<String> firstRowData = null;

			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);

			//Get the data from Table data
			ArrayList<ArrayList<String>> tabledata = Utility.returnPamTableData(3);
			ArrayList<String> tableCellData = null;
			
			// Verified the table data and csv data
			for (int i = 0; i < tabledata.size(); i++) {
				tableCellData = tabledata.get(i);
				firstRowData = csvDataToCheckTheMsg.get(i + 1);

				Assert.assertEquals(tableCellData.get(0), firstRowData.get(0));
				Assert.assertEquals(tableCellData.get(1), firstRowData.get(1));
				Assert.assertEquals(tableCellData.get(2), firstRowData.get(2));

				tableCellData = null;
				firstRowData = null;
			}
			printLog("Verified the table data and csv data as expected");

			//Delete "comparisonAnalysis.pdf" file if exists
			Utility.deleteDownloadFiles("comparisonAnalysis", ".pdf");

			//Select "Export to PDF" from chart menu options to download "comparisonAnalysis.pdf" file
			kabobMenuOptions("Export to PDF");

			//Verified the 'comparisonAnalysis.pdf' downloaded
			String fileName = Utility.latestDownLoadedFileFromDownload("comparisonAnalysis", "pdf");
			File fileLenth = new File(Constant.DOWNLOAD_PATH + "/" + fileName);
			if (fileLenth.length() <= 0) {
				throw new Exception("check if the file is not downloaded or the file doesnot contain anything");
			}

			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
