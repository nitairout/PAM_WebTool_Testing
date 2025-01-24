package regression.ScatterPlotAnalysisCard;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test validates the scatter plot Analysis card and validates the options of Data breakdown and Scatter plot options.
 */
public class QIDM_68_ScatterPlotAnalysisDatabreakdown extends TestBase{
	LoginTC login = null;
	@Test
	public void scatterPlotAnalysisDatabreakdown() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to PAM page
			goToAnalysisPage();
			
			//Navigate to Scatter Plot Analysis Card page
			goToPAMCard("ScatterPlotCard");
			printLog("Navigated to Scatter plot Card page");

			//Selecting Energy and temperature measurements for PAMTestCapriataSaiwa
			Utility.selectMultipleMeasurements("Electricity#Energy~standard|Weather#Temperature~standard");
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			getWebElementXpath("PAMTest_CapriataSaiwa_Weather").click();
			printLog("Selected Electricity Energy & Weather Tempearture with PAMTest_Capriata/Saiwa");
			
			//Added the Fixed date
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"),testData.getProperty("FixedDateEndJan312023"));
			
			refreshToLoadTheChart();
			aJaxWait();//added for chart loading more time
			Thread.sleep(5000);
			//Verify the chart legends
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C");
			Assert.assertEquals(getWebElementXpath("ScatterLegendTwo").getText(), "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C");
			
			//Selected data breakdown as 'Site Schedule'
			
			dataBreakDownSiteSchedule("Site Schedule","AutoTest_WeekdayVsWeekend");
			aJaxWait();
			printLog("Selected AutoTest_WeekdayVsWeekend fom Site Schedule from DataBreakdown");
			
			ArrayList<String> holdTableHeaders = scatterPlotToolTip();
			for (int i = 0; i <20; i++) {
				Assert.assertTrue(holdTableHeaders.get(i).contains("Weekday") || holdTableHeaders.get(i).contains("Weekend"));
			}
			
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/*[@class='highcharts-graph']").getAttribute("stroke"), "#2fb5ea");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/*[@class='highcharts-graph']").getAttribute("d"), "M 0 11 L 16 11");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/span").getText(), "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C (Weekday)");
			
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1']/*[@class='highcharts-point']").getAttribute("fill"), "#f5a300");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1']/*[@class='highcharts-point']").getAttribute("d"), "M 8 7 L 12 11 8 15 4 11 Z");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1']/span").getText(), "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C (Weekday)");
			
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-2']/*[@class='highcharts-graph']").getAttribute("stroke"), "#1a7aaa");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-2']/*[@class='highcharts-graph']").getAttribute("d"), "M 0 11 L 16 11");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-2']/span").getText(), "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C (Weekend)");
			
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-3']/*[@class='highcharts-point']").getAttribute("fill"), "#ec5a25");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-3']/*[@class='highcharts-point']").getAttribute("d"), "M 8 7 L 12 15 4 15 Z");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-3']/span").getText(), "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C (Weekend)");
			
			Assert.assertEquals(getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[1]").getText(), "Weekday");
			Assert.assertEquals(getWebElementXpath_D("//table[@id='maintable']/thead/tr[2]/th[2]").getText(), "Weekend");
			Assert.assertEquals(getWebElementXpath_D("//table[@id='maintable']/thead/tr[3]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementXpath_D("//table[@id='maintable']/thead/tr[3]/th[2]").getText(), testData.getProperty("TableHeaderTemperatureC"));
			Assert.assertEquals(getWebElementXpath_D("//table[@id='maintable']/thead/tr[3]/th[3]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementXpath_D("//table[@id='maintable']/thead/tr[3]/th[4]").getText(), testData.getProperty("TableHeaderTemperatureC"));

			String byDayTableData="1/1/2023~~~9,459~14.28|1/2/2023~11,854~15.00~~|1/3/2023~3,847~12.94~~|1/4/2023~2,246~13.80~~|1/5/2023~1,963~13.68~~|1/6/2023~7,773~13.61~~|1/7/2023~~~8,726~12.48|1/8/2023~~~9,157~12.92|1/9/2023~13,274~12.01~~|1/10/2023~7,973~12.69~~";
			verifyTableDataWithExpected(byDayTableData,10,"QIDM_68_ScatterPlotAnalysisDatabreakdown");
			
			login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}