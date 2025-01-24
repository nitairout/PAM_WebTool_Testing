package regression.TrendAnalysisCard;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies data breakdown by site schedule in Trend Analysis card , 
 * It verifies chart, legends, statistics and table data and then pushes the chart to dashboard and verifies the same.
 */
public class QIDM_91_TrendAnalysisDataBreakdownBySiteSchedule extends TestBase {
	LoginTC login = null;

	@Test
	public void trendAnalysisDataBreakdownBySiteSchedule() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			//Go to 'Temp Dashboard' under 'Automation Test Dashboards' folder
			Utility.goToPAMDashboard("Automation Test Dashboards", "Temp Dashboard");
			
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Select Electric commodity for a site PAMTest_Capriata/Saiwa
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();

			// Added fixed date range
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();
			
			// Selected AutoTest_StoreHours from Site Schedule from DataBreakdown
			dataBreakDownSiteSchedule("Site Schedule","AutoTest_StoreHours");
			aJaxWait();
			printLog("Selected AutoTestTemplate fom Site Schedule from DataBreakdown");
			
			String[] sitesList = getWebElementXpath("ChartXaxisLabels").getText().split("\\n");
			Assert.assertTrue(sitesList.length > 1);
			
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText(), "PAMTest_Capriata/Saiwa kWh (Close)");
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendTwo").getText(), "PAMTest_Capriata/Saiwa kWh (Open)");
			printLog("Verified legends from chart!!");
			
			//Hide option and location panel
			hideOptionLocationPanel();
			
			//Enlarge bottom panel
			enlargeBottomTabsPanel();

			//Verify statistics value
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementTitle").getText(), "Total Usage");
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementValue").getText(), "261,782");
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementTitle").getText().replaceAll("\\n", " "), "Total Usage Close");
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementValue").getText(), "197,985");
			Assert.assertEquals(getWebElementXpath("Statistics_ThirdElementTitle").getText().replaceAll("\\n", " "), "Total Usage Open");
			Assert.assertEquals(getWebElementXpath("Statistics_ThirdElementValue").getText(), "63,797");
			printLog("Verification of statistics tab is completed!!");
			
			//Table headers verification
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
						
			//1st 4 rows of table data verification
			String expectedSubMeterTableData="1/1/2023~8,417~88.98~1,042~11.02~9,459|1/2/2023~9,580~80.82~2,274~19.18~11,854|1/3/2023~2,479~64.44~1,368~35.56~3,847|1/4/2023~1,303~58.01~943.0~41.99~2,246";
			verifyTableDataWithExpected(expectedSubMeterTableData,4,"QIDM_91_TrendAnalysisDataBreakdownBySiteSchedule");
			printLog("Verification of Table Data is completed!!");
			
			pushwidget("Temp Dashboard","Automation Test Dashboards");
			gotoDashBoardHome();
			aJaxWait();
			Thread.sleep(10000);
			
			//Verify the chart legend
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-column-series highcharts-color-undefined highcharts-series-0']/span").getText(), "PAMTest_Capriata/Saiwa kWh (Close)");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-column-series highcharts-color-undefined highcharts-series-1']/span").getText(), "PAMTest_Capriata/Saiwa kWh (Open)");
			printLog("Verified the legends on the trend chart.");
			
			//Click on Statistics tab on 'Trend Widget'
			getWebElementXpath_D("//gridster/gridster-item[1]//li[2]/a").click();
			Thread.sleep(1000);
			printLog("Click on Statistics tab on 'Trend Widget'");
			
			//Verify the Statistics values
			Assert.assertEquals(getWebElementXpath_D("(//gridster/gridster-item[1]//ra-tab[2]//div[@class='callout-data']//span[contains(@class,'callout-title')])[1]").getText(),"Total Usage");
			Assert.assertEquals(getWebElementXpath_D("(//gridster/gridster-item[1]//ra-tab[2]//div[@class='callout-data']//span[contains(@class,'textFitted text-fitted-overflow')])[1]").getText(),"261,782");
			Assert.assertEquals(getWebElementXpath_D("(//gridster/gridster-item[1]//ra-tab[2]//div[@class='callout-data']//span[contains(@class,'callout-title')])[2]").getText(),"Total Usage - Close");
			Assert.assertEquals(getWebElementXpath_D("(//gridster/gridster-item[1]//ra-tab[2]//div[@class='callout-data']//span[contains(@class,'textFitted text-fitted-overflow')])[2]").getText(),"197,985");
			Assert.assertEquals(getWebElementXpath_D("(//gridster/gridster-item[1]//ra-tab[2]//div[@class='callout-data']//span[contains(@class,'callout-title')])[3]").getText(),"Total Usage - Open");
			Assert.assertEquals(getWebElementXpath_D("(//gridster/gridster-item[1]//ra-tab[2]//div[@class='callout-data']//span[contains(@class,'textFitted text-fitted-overflow')])[3]").getText(),"63,797");
			printLog("Verify the Statistics values on the 'Trend Widget' chart");
			
			//Click on Table tab
			getWebElementXpath_D("//gridster/gridster-item[1]//li[3]/a").click();
			Thread.sleep(1000);
			printLog("Click on Table tab on 'Trend Widget'");
			
			//1st 4 rows of table data verification
			expectedSubMeterTableData="1/1/23~8,417~88.98~1,042~11.02~9,459|1/2/23~9,580~80.82~2,274~19.18~11,854|1/3/23~2,479~64.44~1,368~35.56~3,847|1/4/23~1,303~58.01~943.0~41.99~2,246";
			verifyTableDataWithExpected(expectedSubMeterTableData,4,"QIDM_91_TrendAnalysisDataBreakdownBySiteSchedule");
			printLog("Verification of Table Data is completed!!");
			
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
