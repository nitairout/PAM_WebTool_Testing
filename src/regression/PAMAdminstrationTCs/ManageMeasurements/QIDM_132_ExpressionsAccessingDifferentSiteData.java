package regression.PAMAdminstrationTCs.ManageMeasurements;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies the data for the measurements where expressions are referring data from parent site and using RA metric from one site to another.
 * Measurement 1 'PAMAutoTest-SubmeterAccesingParentData' is configured at subsite where it access the temperature data from parent site
 * Measurement 2 "PAMAutoTest_RAMetricFromOneSiteToAnother" has the expression where it access the RA Metric values from different sites
 */
public class QIDM_132_ExpressionsAccessingDifferentSiteData extends TestBase {
	LoginTC login = null;
	
	@Test
	
	public void expressionsAccessingDifferentSiteData() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Select single measurement under Electricity - 'PAMAutoTest-SubmeterAccesingParentData'
			Utility.selectSingleMeasurement("Electricity","PAMAutoTest-SubmeterAccesingParentData",userDefined);
			
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			//Click on the measurements for subsite
			getWebElementXpath("PAMTest_MainMeter_Energy").click();			
			refreshToLoadTheChart();
			
			// Select the Fixed date range of '01/01/2023' - '01/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			
			//Verify the legend on the chart
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kWh");
			printLog("Verified legend on the chart is displayed for above selected measurment");
			
			//Verify statistics value
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementTitle").getText(), "Total PAMAutoTest-SubmeterAccesingParentData");
			printLog("Verification of statistics tab is completed!!");
			
			//Click on the Table Data tab
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//First 4 rows of table data verification
			String byDayTableData="1/1/2023~660.7|1/2/2023~826.0|1/3/2023~297.1|1/4/2023~161.2";
			verifyTableDataWithExpected(byDayTableData,4,"QIDM_132_ExpressionsAccessingDifferentSiteData");
			printLog("Table Data verification completed!!");
			
			//Select single measurement under Other - 'PAMAutoTest_RAMetricFromOneSiteToAnother'
			Utility.selectSingleMeasurement("Other","PAMAutoTest_RAMetricFromOneSiteToAnother",userDefined);
			
			//Search with the sitename from Location panel
			searchSiteInLocationList("PAMTest_Claremont");
			
			//Click on the measurements for subsite
			getWebElementXpath("PAMTest_Claremont_Other").click();			
			
			// Select the Fixed date range of '01/01/2023' - '06/30/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJun302023"));
			refreshToLoadTheChart();
			
			aJaxWait();//added for chart takes more time to load
			//Verify the legend on the chart
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Claremont units");
			printLog("Verified legend on the chart is displayed for above selected measurment");
			
			//First 6 rows of table data verification
			byDayTableData="Jan 2023~5,000|Feb 2023~5,000|Mar 2023~5,000|Apr 2023~5,000|May 2023~5,000|Jun 2023~5,000";
			verifyTableDataWithExpected(byDayTableData,6,"QIDM_132_ExpressionsAccessingDifferentSiteData");
			printLog("Table Data verification completed!!");
			
			//Click on the Statistics tab
			getWebElementXpath("StatisticsTab").click();
			Thread.sleep(2000);
			
			//Verify statistics value
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementTitle").getText(), "Total PAMAutoTest_RAMetricFromOneSiteToAnother");
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementValue").getText(), "30,000");			
			printLog("Verification of statistics tab is completed!!");
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
