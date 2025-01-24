package regression.AllCardsCommonTCs;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;

/*
 * This test verifies missing interval data in Trend Analysis , Load Profile and Calendar Load Profile cards.
 */
public class QIDM_188_MissingIntervalData extends TestBase {
	LoginTC login = null;

	@Test
	public void missingIntervalData() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Search site ''PAMTest_Capriata/Saiwa \ PAMTest_Main Meter \ PAMTest_Electric \ PAMTest_Shadow 7650' and select measurements
			searchSiteInLocationList("PAMTest_Shadow 7650");			
			getWebElementXpath("PAMTest_Shadow7650_Energy").click();

			// Select the Fixed date range of '10/20/2019 - 11/10/2019'
			addFixedDateRange("10/20/2019", "11/10/2019");
			refreshToLoadTheChart();
			
			// Verify the legend display under the chart.
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650 kWh");
			printLog("Verified the legend from the chart.");
		
			//Verify chart is load in RAW mode
			Assert.assertEquals(getWebElementXpath_D("//*[@class='far fa-wave-triangle']").getAttribute("aria-label"),"Switch from raw to corrected data");
			// Clicked on Data tab and Verified Headers of the table
			getWebElementActionXpath("DataTableTab").click();
			String trendTableData[] = {"10/23/2019","10/24/2019","10/25/2019","10/26/2019","10/27/2019","10/28/2019","10/29/2019","10/30/2019"};
			String dates,values;
			for(int i=0;i<trendTableData.length;i++) {
				dates = getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+4)+"]/th").getText();
				Assert.assertEquals(dates, trendTableData[i]);
				values = getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+4)+"]/td[1]").getText();
				Assert.assertEquals(values, "");		
			}
			
			// Navigate to Load Profile Analysis card using C2C
			card2Card("Load Profile Analysis");
			enlargeBottomTabsPanel();
			// Clicked on Data tab and Verified Headers of the table
			getWebElementActionXpath("DataTableTab").click();
			String lpTableData[] = {"10/20/2019 06:15 AM","10/20/2019 06:30 AM","10/20/2019 06:45 AM","10/20/2019 07:00 AM"};
			for(int i=0;i<lpTableData.length;i++) {
				dates = getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+25)+"]/th").getText();
				Assert.assertEquals(dates, lpTableData[i]);
				values = getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+25)+"]/td[1]").getText();
				Assert.assertEquals(values, "");		
			}
			
			// Navigate to Calendar Load Profile Analysis card using C2C
			card2Card("Calendar Load Profile Analysis");
			// Select a day from the Chart and verify selected date is displayed under 'Selected Days'
			getWebElementActionXpath_D("(//*[@class='highcharts-series-group'])[4]").click();
			Thread.sleep(2000);
			String selectedDay = getWebElementActionXpath_D("//div[@class='groupTree']//span[@class='info']/span").getText();
			Assert.assertEquals(selectedDay,"Nov 4, 2019");
			
			// Clicked on Data tab and Verified the table data
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			//enlargeBottomTabsPanel();
			String clpTableData[] = {"00:15","00:30","00:45","01:00"};
			for(int i=1;i<=clpTableData.length;i++) {//tbody/tr[1]/th
				dates = getWebElementActionXpath_D("//*[@id='tablebody']/tr["+i+"]/th").getText();
				Assert.assertEquals(dates, clpTableData[i-1]);
				values = getWebElementActionXpath_D("//*[@id='tablebody']/tr["+i+"]/td[1]").getText();
				Assert.assertEquals(values, "");		
			}
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}