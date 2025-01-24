package regression.LoadProfileAnalysisCard;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies chart options available for Legend and Table data.
 */
public class QIDM_178_LoadProfileChartOptionsLegendandTable extends TestBase{
	LoginTC login = null;
	@Test
	public void loadProfileChartOptionsLegendandTable() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Go to 'Temp Dashboard' under 'Automation Test Dashboards' folder
			Utility.goToPAMDashboard("Automation Test Dashboards", "Temp Dashboard");
			
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Select Electric commodity for a site PAMTest_Energy Balance
			searchSiteInLocationList(testData.getProperty("PAMTest_Energy_Balance"));
			getWebElementXpath("PAMTest_EnergyBalance_Energy").click();
			
			refreshToLoadTheChart();
			
			//Select Electric commodity for a site PAMTest_Main Meter
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			getWebElementXpath("PAMTest_MainMeter_Energy").click();

			// Select the Fixed date range of '01/01/2023' - '01/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();
			
			// verify start and end date from chart
			//String expectedStartAndEndDate = "1/1/2023, 12:00 AM - 2/1/2023, 12:00 AM";
			String expectedStartAndEndDate = "1/1/2023 12:00 AM - 2/1/2023 12:00 AM";
			String actualStartEndDateFromChart=getWebElementActionXpath_D("//*[contains(@class,'highcharts-title')]").getText();
			Assert.assertEquals(actualStartEndDateFromChart, expectedStartAndEndDate);
			printLog("Verified the Fixed date range from chart");
						
			// Verify the legends display under the chart.
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance kW");
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kW");
			printLog("Verified legends from chart!!");
			
			kabobMenuOptions("Chart Options");
			Thread.sleep(2000);
			// Switch to Legend tab and select Hide Location Path checkbox.
			getWebElementXpath("ChartOptionsLegendTab").click();
			getWebElementXpath("LegendHideLocationPath").click();
			getWebElementXpath("SaveAndClose").click();
			aJaxWait();
			//Verify that Legend hide location path testData.getProperty("PAMTestCapriataSaiwa") and displays legends PAMTest_Energy Balance kWh and PAMTest_Main Meter kWh.
			Assert.assertEquals(getWebElementActionXpath("LineLegendOne").getText(), "PAMTest_Energy Balance kW");
			Assert.assertEquals(getWebElementActionXpath("LineLegendTwo").getText(), "PAMTest_Main Meter kW");
			printLog("Verified the legend hide location path displays under the chart");
			
			kabobMenuOptions("Chart Options");
			Thread.sleep(2000);
			// Switch to Legend tab and select Hide Legend checkbox.
			getWebElementXpath("ChartOptionsLegendTab").click();
			Thread.sleep(2000);
			getWebElementXpath("HideLegend").click();
			getWebElementXpath("SaveAndClose").click();
			aJaxWait();
			// Verify that Legend is hidden and no legend displays under the chart.
			int legendsCount = d.findElements(By.xpath("LineLegendOne")).size();
			Assert.assertEquals(legendsCount, 0);
			printLog("Verify that Legend is hidden and no legend displays under the chart");
			
			// Go back to Chart Options and select Table tab.
			kabobMenuOptions("Chart Options");
			Thread.sleep(2000);
			getWebElementActionXpath("TableTabFromChartOptions").click();
			Thread.sleep(2000);
			// Select Hide Location Path.
			getWebElementActionXpath("TableHideLocationPath").click();
			getWebElementXpath("SaveAndClose").click();
			aJaxWait();
			//Verify the Site names after hide the locations from table tab
			Utility.moveTheScrollToTheDown();
			
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			Assert.assertEquals(d.findElement(By.xpath("//*[@id='tablehead']/tr[1]/th[2]")).getText(), "PAMTest_Energy Balance");
			Assert.assertEquals(d.findElement(By.xpath("//*[@id='tablehead']/tr[1]/th[3]")).getText(), testData.getProperty("PAMTest_Main_Meter"));
			
			pushwidget("Temp Dashboard","Automation Test Dashboards");
			gotoDashBoardHome();
			aJaxWait();
			Thread.sleep(5000);
			
			// Verify that Legend is hidden and no legend displays under the chart.
			legendsCount = d.findElements(By.xpath("LineLegendOne")).size();
			Assert.assertEquals(legendsCount, 0);
			printLog("Verify that Legend is hidden and no legend displays under the chart");
			
			getWebElementActionXpath("WidgetTableTab").click();
			Thread.sleep(3000);
			
			Assert.assertEquals(d.findElement(By.xpath("//*[@id='tablehead']/tr[1]/th[2]")).getText(), "PAMTest_Energy Balance");
			Assert.assertEquals(d.findElement(By.xpath("//*[@id='tablehead']/tr[1]/th[3]")).getText(), testData.getProperty("PAMTest_Main_Meter"));
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
