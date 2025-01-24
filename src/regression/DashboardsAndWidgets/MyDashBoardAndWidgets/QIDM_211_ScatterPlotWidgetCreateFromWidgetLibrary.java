package regression.DashboardsAndWidgets.MyDashBoardAndWidgets;
import java.time.LocalDate;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies creating new Scatter Plot widget from widget library and 
 * checks widget is created for selected site with default widget settings. 
 * And it also verifies adding comparison date range for the widget from widget settings. 
 */

public class QIDM_211_ScatterPlotWidgetCreateFromWidgetLibrary extends TestBase{
	LoginTC login = null;
	@Test
	public void scatterPlotWidgetCreateFromWidgetLibrary() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			Utility.pushWidgetFromWidgetLibrary("Scatter Plot Analysis");
			
			//Calculating and verifying last 30 days date
			LocalDate today = LocalDate.now();
			String expectedEndChartDate =changeTheDateFormat("M/d/yy",today);
			String expectedStartChartDate =changeTheDateFormat("M/d/yy",today.minusDays(29));
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-title']").getText(),expectedStartChartDate+" - "+expectedEndChartDate);

			String actualLegendOne=getWebElementXpath_D("//div[contains(@class,'highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0')]/span").getText();
			String actualLegendTwo=getWebElementXpath_D("//div[contains(@class,'highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1')]/span").getText();
			Assert.assertEquals(actualLegendOne, "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C");
			Assert.assertEquals(actualLegendTwo, "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C");
			printLog("Legend and chart date verification completed.");
			
			//On table tab
			getWebElementActionXpath("NewWidgetTableTab").click();
			Thread.sleep(1000);
			
			//Table verification
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[1]/th[2]").getText(),testData.getProperty("PAMTestCapriataSaiwa"));
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[2]/th[1]").getText(),testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[2]/th[2]").getText(),testData.getProperty("TableHeaderTemperatureCDegree"));
			//Checking table data notnull
			for(int i=0;i<2;i++) {
				Assert.assertEquals(changeTheDateFormat("M/d/yy",today.minusDays(29-i)), getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/th[1]").getText());
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/td[1]").getText());
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/td[2]").getText());
			}
			
			Utility.clickOnEditLinkOfWidget("Edit");
			
			//Comparison date range to select Previous Month
			getWebElementActionXpath_D("//pam-comparisondate//div[@class='ra-dropdown-input']/span").click();
			Thread.sleep(1000);
			getWebElementXpath_D("//span[contains(text(),'Previous Month')]").click();
			Thread.sleep(1000);
			//Save from widget library
			getWebElementXpath("WidgetConfigSaveButton").click();
			Thread.sleep(10000);
			printLog("Selected Previous Month from widget library");
			
			//Table header verification for comparison date as previous month
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[1]/th[2]").getText(), testData.getProperty("PAMTestCapriataSaiwa"));
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[1]/th[4]").getText(), testData.getProperty("PAMTestCapriataSaiwa"));
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[2]/th[1]").getText(),testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[2]/th[2]").getText(),testData.getProperty("TableHeaderTemperatureCDegree"));
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[2]/th[3]").getText(),testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[2]/th[4]").getText(),testData.getProperty("TableHeaderTemperatureCDegree"));
			//Checking table data notnull
			for(int i=0;i<2;i++) {
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/th[1]").getText());
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/td[1]").getText());
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/td[2]").getText());
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/td[3]").getText());
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/td[4]").getText());
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/td[5]").getText());
			}
			printLog("Verified Previous Month table data from chart.");
			
			//On Chart tab
			getWebElementActionXpath("NewWidgetChartTab").click();
			Thread.sleep(1000);
			
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-title']").getText(),expectedStartChartDate+" - "+expectedEndChartDate);
			
			actualLegendOne=getWebElementXpath_D("//div[contains(@class,'highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0')]/span").getText();
			String actualLegendThree=getWebElementXpath_D("//div[contains(@class,'highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-2')]/span").getText();
			actualLegendTwo=getWebElementXpath_D("//div[contains(@class,'highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1')]/span").getText();
			String actualLegendFour=getWebElementXpath_D("//div[contains(@class,'highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-3')]/span").getText();
			//
			Assert.assertEquals(actualLegendOne, "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C");
			Assert.assertEquals(actualLegendOne, "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C");
			Assert.assertEquals(actualLegendThree, "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C (Previous Month)");
			Assert.assertEquals(actualLegendFour, "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C (Previous Month)");
			printLog("Legend and chart date verification completed.");
			
			Utility.deleteAllWidgets();
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
