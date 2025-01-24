package regression.AllCardsCommonTCs;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test case verifies Hide Series functionality and checks hidden series is still hidden 
 * when moved to different card and pushed to widget.
 */
public class QIDM_216_AllCardsHideSeriesFromLegend extends TestBase{
	LoginTC login = null;
	@Test
	public void allCardsHideSeriesFromLegend() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Go to 'Temp Dashboard' under 'Automation Test Dashboards' folder
			Utility.goToPAMDashboard("Automation Test Dashboards", "Temp Dashboard");
			
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Selected Energy measurement for PAMTest_Capriata/Saiwa site
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			refreshToLoadTheChart();
			
			//Selected Energy measurement for PAMTest_Herentals, Biscuits site
			searchSiteInLocationList(testData.getProperty("PAMTestHerentalsBiscuits"));
			getWebElementXpath("PAMTest_HerentalsBiscuits_Energy").click();
			refreshToLoadTheChart();
			
			//Selected Energy measurement for PAMTest_Naperville, IL site
			searchSiteInLocationList(testData.getProperty("PAMTestNapervilleIL"));
			getWebElementXpath("PAMTest_NapervilleIL_Energy").click();
			refreshToLoadTheChart();
			
			// Select the Fixed date range of "01/01/2023" - "01/31/2023"
			addFixedDateRange("01/01/2023", "01/31/2023");
			
			//Verify the legends
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			Assert.assertEquals(getWebElementXpath("ColumnLegendTwo").getText(), testData.getProperty("PAMTest_HerentalsBiscuitskWh"));
			Assert.assertEquals(getWebElementXpath("ColumnLegendThree").getText(), testData.getProperty("PAMTest_NapervilleILkWh"));
			Thread.sleep(8000);
			//Hide the legend of 'PAMTest_Naperville, IL kWh' 
			getWebElementXpath("ColumnLegendThree").click();
			
			//Verify legend should be Hide for 'PAMTest_Naperville, IL kWh'
			List<WebElement> legendHide = d.findElements(By.xpath("//div[@class='highcharts-legend-item highcharts-column-series highcharts-color-undefined highcharts-series-2 highcharts-legend-item-hidden']/span"));
			Assert.assertTrue(legendHide.size()>0);
			
 			List<String> chartTooltipValues=getBarToolTip();
			int dateCounter=0;
			
			//verification of the hide site 'PAMTest_Naperville, IL kWh' should not be display on the chart
			for(String value:chartTooltipValues) {
				if(value.contains("PAMTest_Naperville, IL kWh")) 
					throw new Exception("chart should not populate value for 'PAMTest_Naperville, IL kWh' site");
				dateCounter++;
				if(dateCounter==30)
					break;
			}
			
			//Table data verification started
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), testData.getProperty("PAMTestCapriataSaiwa"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[3]").getText(), "PAMTest_Herentals, Biscuits");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[4]").getText(), testData.getProperty("PAMTestNapervilleIL"));
			printLog("Table header verification completed");
			
			//Navigate to Load Profile Analysis card using C2C
			card2Card("Load Profile Analysis");
			aJaxWait();
			Thread.sleep(8000);
			Thread.sleep(2000);
			//Verify legend should be Hide for 'PAMTest_Naperville, IL kWh' on Load Profile Analysis Card
			
			List<WebElement> legendHideLP = d.findElements(By.xpath("//div[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-2 highcharts-legend-item-hidden']/span"));
			Assert.assertTrue(legendHideLP.size()>0);
			
			//Table data verification started
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), testData.getProperty("PAMTestCapriataSaiwa"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[3]").getText(), testData.getProperty("PAMTestHerentalsBiscuits"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[4]").getText(), testData.getProperty("PAMTestNapervilleIL"));
			printLog("Table header verification completed");
			
			//Push the widget to the dashboard
			pushwidget("Temp Dashboard","Automation Test Dashboards");
			gotoDashBoardHome();
			aJaxWait();
			Thread.sleep(5000);
			//Verify legend should be Hide for 'PAMTest_Naperville, IL kWh' on Load Profile Analysis Card on dashboard widget
			List<WebElement> legendHideLPWidget = d.findElements(By.xpath("//div[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0 highcharts-legend-item-hidden']/span"));
			Assert.assertTrue(legendHideLPWidget.size()>0);
			
			login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}