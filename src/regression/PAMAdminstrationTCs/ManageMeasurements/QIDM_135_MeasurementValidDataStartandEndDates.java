package regression.PAMAdminstrationTCs.ManageMeasurements;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies functionality of valid start and end data range setting under measurement configuration. 
 * It verifies if data is only present during valid data date range on the chart for both user defined and standard measurements.
 */
public class QIDM_135_MeasurementValidDataStartandEndDates extends TestBase {
	LoginTC login = null;

	@Test
	public void measurementValidDataStartandEndDates() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");

			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");

			//select the measurement 'Auto Test -ValidDataRange' from the list
			Utility.selectSingleMeasurement("Electricity", "Auto Test -ValidDataRange",userDefined);

			// Select Electric measurement for a site PAMTest_Naperville, IL
			searchSiteInLocationList(testData.getProperty("PAMTestNapervilleIL"));
			getWebElementXpath("PAMTest_NapervilleIL_Energy").click();
			refreshToLoadTheChart();
			
			// Select the Fixed date range of '11/01/2022' - '07/31/2023'
			addFixedDateRange("11/01/2022", "07/31/2023");
			//refreshToLoadTheChart();
			
			//Verify the legend
			Assert.assertEquals(getWebElementActionXpath("LineLegendOne").getText(), testData.getProperty("PAMTest_NapervilleILkWh"));

			//Click on table Data tab
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			// Verified the rows data from the table
			String tableData = "Nov 2022~~|Dec 2022~~|Jan 2023~~|Feb 2023~1,893,710|Mar 2023~2,199,363|Apr 2023~2,044,741|May 2023~1,965,656|Jun 2023~2,038,248|Jul 2023~~";
			//verifyTableDataWithExpected(tableData, 9, "QIDM_135_MeasurementValidDataStartandEndDates");
			printLog("Verified the table data for thr measurement'Auto Test -ValidDataRange' for site 'PAMTest_Naperville, IL' ");
			
			// un-select the measurement 'Auto Test -ValidDataRange'
			getWebElementXpath("PAMTest_NapervilleIL_Energy").click();
			refreshToLoadTheChart();
			//select the measurement 'Energy' from the list
			Utility.selectSingleMeasurement("Electricity", "Energy",standard);

			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			// Click on Electric commodity for a subsite 'PAMTest_Main Meter' from site 'PAMTest_Naperville, IL'
			getWebElementXpath("PAMTest_NapervilleIL_PAMTest_MainMeter_Energy").click();
			//getWebElementXpath_D("(//div[@id='se-site-tree-container']//span[@class='pull-right'][1]/i[1]/i[1]/i/i[3])[4]").click();

			// Select the Fixed date range of "01/01/2023", "06/30/2023"
			addFixedDateRange("01/01/2023", "06/30/2023");
			refreshToLoadTheChart();

			//Verify the legend
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText(), "PAMTest_Naperville, IL \\ PAMTest_Main Meter kWh");

			// Verified the all rows data from the table
			String tableData1 = "Jan 2023~~|Feb 2023~~|Mar 2023~2,199,363|Apr 2023~2,044,741|May 2023~1,878,876|Jun 2023~~";
			verifyTableDataWithExpected(tableData1, 6, "QIDM_135_MeasurementValidDataStartandEndDates");
			printLog("Verified the table data for thr measurement'Energy' for subsite 'PAMTest_Main Meter' from site 'PAMTest_Naperville, IL'");

			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

}
