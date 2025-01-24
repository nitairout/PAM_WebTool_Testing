package regression.TrendAnalysisCard;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies all chart options available in Trend card. 
 * Selects each option one by one and loads chart for default Energy measurement and verifies chart is loaded as expected 
 * by verifying the chart options from Serises Type/Color tab.
 */

public class QIDM_70_TrendAnalysisChartOptionsSeriestab extends TestBase {
	LoginTC login = null;

	@Test
	public void trendAnalysisChartOptionsSeriestab() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			
			// Navigate to TrendAnalysis Card
			goToPAMCard("TrendAnalysisCard");

			Utility.selectMultipleMeasurements("Electricity#Energy~standard");
			
			//Searching with "PAMTest_Capriata/Saiwa" and expanding to select PAMTest_Energy Balance & PAMTest_Main Meter energy
			searchSiteInLocationList("PAMTest_Capriata/Saiwa");
			getWebElementActionXpath_D("//td[normalize-space()='PAMTest_Capriata/Saiwa']//*[contains(@class,'k-svg-icon')]").click();
			Thread.sleep(1000);
			getWebElementXpath_D("//td[normalize-space()='PAMTest_Energy Balance']/following-sibling::td[2]/child::span").click();
			Thread.sleep(1000);
			getWebElementXpath_D("//td[normalize-space()='PAMTest_Main Meter']/following-sibling::td[2]/child::span").click();
			Thread.sleep(1000);
			
			// Select the Fixed date range of '01/01/2023' - '01/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();

			Utility.moveTheScrollToTheTop();
			// verify start and end date from chart
			Assert.assertEquals(getWebElementXpath("ChartStartDate").getText(), testData.getProperty("FixedDateStartJan012023"));
			Assert.assertEquals(getWebElementXpath("ChartEndDate").getText(), testData.getProperty("FixedDateEndJan312023"));

			// Verify the legends display under the chart.
			List<String> legendList=Arrays.asList("PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance kWh","PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kWh");
			Assert.assertTrue(legendList.contains(getWebElementXpath("ColumnLegendOne").getText()));
			Assert.assertTrue(legendList.contains(getWebElementXpath("ColumnLegendTwo").getText()));
			printLog("Verified legends from the chart should be bar");
			
			Utility.moveTheScrollToTheTop();
			String seriesLegendsPath = "//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']";
			//Verify the legends should not be series
			List<WebElement> seriesLegends = d.findElements(By.xpath(seriesLegendsPath));
			Assert.assertTrue(seriesLegends.size()==0);
			printLog("Verified the legends should not be series");
			
			// Click on KabobMenu and select Chart Options.
			kabobMenuOptions("Chart Options");

			// Change Series Type from bar to line and selected Hide Point Markers On Line Series Checkbox
			getWebElementXpath("SeriesTypeRadioButton").click();
			getWebElementXpath("HidePointMarkersOnLineSeriesCheckbox").click();
			getWebElementXpath("SaveAndClose").click();
			aJaxWait();
			Thread.sleep(10000);

			//Verify the legends should be series
			seriesLegends = d.findElements(By.xpath(seriesLegendsPath));
			Assert.assertTrue(seriesLegends.size()>0);
			printLog("Verified the legends should be series");
			
			//Verify that no markers on the Series Chart
			List<WebElement> markers = d.findElements(By.xpath("//*[@class='highcharts-point ']"));
			Assert.assertTrue(markers.size()==0);
			printLog("Verify that no markers on the Series Chart");
			
			//Verify the legend Colors before change
			Assert.assertEquals(d.findElement(By.xpath("(//*[@class='highcharts-point'])[1]")).getAttribute("fill"), "#2fb5ea");
			Assert.assertEquals(d.findElement(By.xpath("(//*[@class='highcharts-point'])[2]")).getAttribute("fill"), "#f5a300");
			
			// Click on KabobMenu and select Chart Options.
			kabobMenuOptions("Chart Options");
			//Go to Series Color tab
			getWebElementXpath("ChartOptionsSeriesColorTab").click();
			Thread.sleep(2000);
			//Energy
			getWebElementActionXpath_D("(//div[@id='se-chartop-colorby-mode']/*[@role='combobox'])[1]/span").click();
			Thread.sleep(1000);
			getWebElementXpath_D("//span[normalize-space()='Use Tag Colors']").click();
			Thread.sleep(1000);
			
			new Select(getWebElementActionXpath_D("(//div[@id='se-chartop-colorby-mode']/select)[1]")).selectByVisibleText("Autotag");
			Thread.sleep(1000);
			
			getWebElementXpath("SaveAndClose").click();
			Thread.sleep(10000);
			
			//Verify the legend Colors after change
			Assert.assertEquals(d.findElement(By.xpath("(//*[@class='highcharts-point'])[1]")).getAttribute("fill"), "rgb(255, 127, 237)");
			Assert.assertEquals(d.findElement(By.xpath("(//*[@class='highcharts-point'])[2]")).getAttribute("fill"), "rgb(178, 0, 255)");
			
			//UnSelect Electric commodity for a site PAMTest_Energy Balance
			searchSiteInLocationList(testData.getProperty("PAMTest_Energy_Balance"));
			getWebElementXpath("PAMTest_EnergyBalance_Energy").click();
			
			Utility.selectMultipleMeasurements("Electricity#Energy~standard|Gas#Volume~standard");
			//UnSelect Electric commodity for a site PAMTest_Main Meter
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			//Here it required 2 measurement to be select Energy and Gas. Since we are already selected Energy we are only selecting Gas.
			getWebElementXpath("PAMTest_MainMeter_Gas").click();
			refreshToLoadTheChart();
			
			// Click on KabobMenu and select Chart Options.
			kabobMenuOptions("Chart Options");
			//Go to Series Color tab
			getWebElementXpath("ChartOptionsSeriesColorTab").click();
			Thread.sleep(2000);
			
			//Click on Commodity radio button
			getWebElementXpath_D("//input[@id='chartOptions_colorByCommodity']").click();
			Thread.sleep(2000);
			
			//Select Yellow color for Energy commodity
			getWebElementActionXpath_D("(//div[@id='se-chartop-colorby-mode']/*[@role='combobox'])[1]").click();
			getWebElementXpath_D("//li/span[normalize-space()='Palette']").click();
			Thread.sleep(1000);		
			//siteDD.selectByVisibleText("Palette");
			Thread.sleep(1000);
			
			new Select(getWebElementActionXpath_D("(//div[@id='se-chartop-colorby-mode'])[2]/select")).selectByValue("3: 3");
			Thread.sleep(1000);
			
			//Select Green color for Electricity commodity
			getWebElementActionXpath_D("(//div[@id='se-chartop-colorby-mode']/*[@role='combobox'])[2]").click();
			getWebElementXpath_D("//li/span[normalize-space()='Palette']").click();
			Thread.sleep(1000);	
			Thread.sleep(1000);
			
			new Select(getWebElementActionXpath_D("(//div[@id='se-chartop-colorby-detail'])[2]//select[1]")).selectByValue("5: 5");
			Thread.sleep(1000);
			
			getWebElementXpath("SaveAndClose").click();
			Thread.sleep(10000);
			
			//Verify the legend Colors after change
			Assert.assertEquals(d.findElement(By.xpath("(//*[@class='highcharts-graph'])[1]")).getAttribute("stroke"), "#3dcd58");
			Assert.assertEquals(d.findElement(By.xpath("(//*[@class='highcharts-point'])[2]")).getAttribute("fill"), "#ffd100");
			
			login.logout();
			
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

}

