package regression.PAMAdminstrationTCs.ManageMeasurements;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 *This test verifies data for the measurements having expressions with mixed interval lengths.  
 *Each of below measurements adding up data with different interval lengths and outcome of the 
 *expression is expected at specific interval length as below.
 *For Measurement 'PAMAutoTestIntervals_10+15'  data should be displayed at 30 mins interval length
 *For Measurement 'PAMAutoTestIntervals_15+5' data should be displayed at 15 mins interval length
 *For Measurement 'PAMAutoTestIntervals_30+10' data should be displayed at 30 mins interval length
 *For Measurement 'PAMAutoTestIntervals_60+15' data should be displayed at 60 mins interval length
 *
 *
 */
public class QIDM_137_ExpressionsWithMixedIntervalLengths extends TestBase {
	LoginTC login = null;
	
	@Test
	public void expressionsWithMixedIntervalLengths() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");

			Utility.selectSingleMeasurement("Other","PAMAutoTestIntervals_10+15",userDefined);
			//Search with the sitename from Location panel
			searchSiteInLocationList("QA Test Intervals");
			getWebElementActionXpath_D("//table/tbody/tr[2]/td[9]/child::span").click();		
			refreshToLoadTheChart();
			
			// Select the Fixed date range of '1/1/2017' - '1/31/2017'
			addFixedDateRange("1/1/2017", "1/31/2017");
			
			//refreshToLoadTheChart();
		
			String legendValue=getWebElementXpath("LineLegendOne").getText();
			Assert.assertEquals(legendValue, "QA Site 1 \\ QA Test Intervals kwh");
			printLog("Legend verification completed ");
			
			//Click on the Table Data tab
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "QA Site 1 \\ QA Test Intervals");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th").getText(), "PAMAutoTestIntervals_10+15 (kwh)");
			printLog("Verified table header for PAMAutoTestIntervals_10+15().");
			
			//Table header verification
			String intervals10and15TableData="1/1/2017 12:30 AM~128.8|1/1/2017 01:00 AM~131.0|1/1/2017 01:30 AM~137.4|1/1/2017 02:00 AM~130.5";
			verifyTableDataWithExpected(intervals10and15TableData, 4, "QIDM_137_ExpressionsWithMixedIntervalLengths");
			
			//Selected PAMAutoTestIntervals_15+5
			Utility.selectSingleMeasurement("Other","PAMAutoTestIntervals_15+5",userDefined);
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "QA Site 1 \\ QA Test Intervals");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th").getText(), "PAMAutoTestIntervals_15+5 (kWh)");
			printLog("Verified table header for PAMAutoTestIntervals_15+5().");
			
			//Table header verification
			String intervals15and5TableData="1/1/2017 12:15 AM~14.01|1/1/2017 12:30 AM~13.84|1/1/2017 12:45 AM~13.84|1/1/2017 01:00 AM~13.17";
			verifyTableDataWithExpected(intervals15and5TableData, 4, "QIDM_137_ExpressionsWithMixedIntervalLengths");
			
			//Selected PAMAutoTestIntervals_30+10
			Utility.selectSingleMeasurement("Other","PAMAutoTestIntervals_30+10",userDefined); 
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "QA Site 1 \\ QA Test Intervals");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th").getText(), "PAMAutoTestIntervals_30+10 (kWh)");
			printLog("Verified table header for PAMAutoTestIntervals_30+10().");
			//Table header verification
			
			String intervals30and10TableData="1/1/2017 12:30 AM~164.0|1/1/2017 01:00 AM~167.0|1/1/2017 01:30 AM~171.0|1/1/2017 02:00 AM~166.0";
			verifyTableDataWithExpected(intervals30and10TableData, 4, "QIDM_137_ExpressionsWithMixedIntervalLengths");
			
			//Selected PAMAutoTestIntervals_60+15
			Utility.selectSingleMeasurement("Other","PAMAutoTestIntervals_60+15",userDefined);
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "QA Site 1 \\ QA Test Intervals");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th").getText(), "PAMAutoTestIntervals_60+15 (kWh)");
			printLog("Verified table header for PAMAutoTestIntervals_60+15.");
			//Table header verification
			
			String intervals60and15TableData="1/1/2017 01:00 AM~304.9|1/1/2017 02:00 AM~303.0|1/1/2017 03:00 AM~314.2|1/1/2017 04:00 AM~302.1";
			verifyTableDataWithExpected(intervals60and15TableData, 4, "QIDM_137_ExpressionsWithMixedIntervalLengths");
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}