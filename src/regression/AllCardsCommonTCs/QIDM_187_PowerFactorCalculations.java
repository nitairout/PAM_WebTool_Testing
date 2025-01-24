package regression.AllCardsCommonTCs;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies power factor is calculated when Energy and Reactive Energy measurements are selected. 
 * It verifies statistics and table data values in Trend and Load Profile cards
 */
public class QIDM_187_PowerFactorCalculations extends TestBase {
	LoginTC login = null;

	@Test
	public void powerFactorCalculations() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Select Energy, Reactive Energy and Power Factor measurements
			Utility.selectMultipleMeasurements("Electricity#Energy~standard|Electricity#Reactive Energy~standard|Electricity#Power Factor~standard");
			
			//Search site 'QA Site 1'and select measurements
			searchSiteInLocationList("QA Site 1");			
			getWebElementXpath_D("//td[normalize-space()='QA Site 1']/following-sibling::td[2]/child::span").click();

			// Select the Fixed date range of '01/01/2017' - '01/10/2017'
			addFixedDateRange("01/01/2017", "01/10/2017");
			refreshToLoadTheChart();
			
			// Verify the legends display under the chart.
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), "QA Site 1 kWh");
			Assert.assertEquals(getWebElementXpath("ColumnLegendTwo").getText(), "QA Site 1 kVARh");
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), "QA Site 1 PF");
			printLog("Verified all the three legends from the chart.");
			
			//Hide option and location panel
			Utility.hideOptionLocationPanelForCards();
			//Enlarge the bottom panel 
			enlargeBottomTabsPanel();
			
			//Verify the Statistics value
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementTitle").getText(), "Total Usage");
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementValue").getText(), "21,208");
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementUnit").getText(), "kWh");
			
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementTitle").getText(), "Total Reactive Energy");
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementValue").getText(), "7,136");
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementUnit").getText(), "kVARh");
			
			Assert.assertEquals(getWebElementXpath("Statistics_FifthElementTitle").getText(), "Avg Daily Power Factor");
			Assert.assertEquals(getWebElementXpath("Statistics_FifthElementValue").getText(), "0.9472");
			Assert.assertEquals(getWebElementXpath("Statistics_FifthElementUnit").getText(), "PF");
			// Clicked on Data tab and Verified Headers of the table
			getWebElementXpath("DataTableTab").click();
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "QA Site 1");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), "Reactive Energy (kVARh)");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[3]").getText(), "Power Factor");
			printLog("Table header verification completed");
			
			//Verified the 4 rows data from the table
			String tableData = "1/1/2017~1,291~465.2~0.9408|1/2/2017~1,507~525.2~0.9443|1/3/2017~2,364~837.5~0.9426|1/4/2017~2,546~832.8~0.9504";
			verifyTableDataWithExpected(tableData, 4, "QIDM_187_PowerFactorCalculations");
			printLog("Table data verification successfully for kW-Only site");
			
			// Navigate to Load Profile Analysis card using C2C
			card2Card("Load Profile Analysis");
			
			// Verify the legends display under the chart.
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "QA Site 1 kWh");
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText(), "QA Site 1 kVARh");
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), "QA Site 1 PF");
			printLog("Verified all the three legends from the chart.");

			//Enlarge the bottom panel 
			enlargeBottomTabsPanel();
			
			//Verify the Statistics value
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementTitle").getText(), "Total Usage");
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementValue").getText(), "21,208");
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementUnit").getText(), "kWh");
			
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementTitle").getText(), "Total Reactive Energy");
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementValue").getText(), "7,136");
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementUnit").getText(), "kVARh");
			
			Assert.assertEquals(getWebElementActionXpath_D("(//pam-statistic//*[@class='se-statistic-title'])[5]").getText(), "Avg Power Factor");
			Assert.assertEquals(getWebElementActionXpath_D("(//pam-statistic//*[@class='se-statistic-value'])[5]").getText(), "0.9461");
			Assert.assertEquals(getWebElementActionXpath_D("(//pam-statistic//*[@class='se-statistic-unit'])[5]").getText(), "PF");
			
			Utility.moveTheScrollToTheTop();
			// Clicked on Data tab and Verified Headers of the table
			getWebElementActionXpath("DataTableTab").click();
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "QA Site 1");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), "Reactive Energy (kVARh)");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[3]").getText(), "Power Factor");
			printLog("Table header verification completed");
			
			//Verified the 4 rows data from the table
			String tableData1 = "1/1/2017 12:15 AM~13.50~5.432~0.9278|1/1/2017 12:30 AM~13.40~5.349~0.9288|1/1/2017 12:45 AM~13.41~5.326~0.9294|1/1/2017 01:00 AM~13.07~5.041~0.9331";
			verifyTableDataWithExpected(tableData1, 4, "QIDM_187_PowerFactorCalculations");
			printLog("Table data verification successfully for kW-Only site");
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}