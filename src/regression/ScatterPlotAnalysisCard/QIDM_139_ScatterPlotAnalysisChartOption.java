package regression.ScatterPlotAnalysisCard;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies each chart option available for scatter plot card and verifies chart is displayed as per the selections
 */
public class QIDM_139_ScatterPlotAnalysisChartOption extends TestBase{
	LoginTC login = null;
	@Test
	public void scatterPlotAnalysisChartOption() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to PAM page
			goToAnalysisPage();
			
			//Navigate to Scatter Plot Analysis Card page
			goToPAMCard("ScatterPlotCard");
			printLog("Navigated to Scatter plot Card page");

			//Selecting Energy and Volume measurements for PAMTest_Herentals, Biscuits
			Utility.selectMultipleMeasurements("Electricity#Energy~standard|Gas#Volume~standard");
			searchSiteInLocationList(testData.getProperty("PAMTestHerentalsBiscuits"));
			
			//Enlarge PAMTest_Herentals, Biscuits site
			getWebElementXpath_D("//td[normalize-space()='"+testData.getProperty("PAMTestHerentalsBiscuits")+"']//kendo-svgicon[contains(@class,'k-svg-i-caret')]").click();
			Thread.sleep(1000);
			//Enlarge PAMTest_Energy Balance site
			//td[normalize-space()='PAMTest_Herentals, Biscuits']//kendo-svgicon[contains(@class,'k-svg-icon')][contains(@class,'k-svg-i-caret')]
			getWebElementXpath_D("//td[normalize-space()='PAMTest_Energy Balance']//kendo-svgicon[contains(@class,'k-svg-i-caret')]").click();
			Thread.sleep(1000);
			//Selecting Energy and Volume measurements for 'PAMTest_Powerhouse'
			getWebElementXpath_D("//table/tbody/tr[7]/td[3]/child::span").click();
			getWebElementXpath_D("//table/tbody/tr[7]/td[4]/child::span").click();
			
			//Selecting Energy and Volume measurements for 'PAMTest_Production Line'
			getWebElementXpath_D("//table/tbody/tr[9]/td[3]/child::span").click();
			getWebElementXpath_D("//table/tbody/tr[9]/td[4]/child::span").click();
			printLog("Selected Energy and Volume measurements for PAMTest_Powerhouse & PAMTest_Production Line sites");
			
			//Added the Fixed date
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"),testData.getProperty("FixedDateEndJan312023"));
			
			refreshToLoadTheChart();
			
			//Verify the legend by default
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/span").getText(), "PAMTest_Herentals, Biscuits \\ PAMTest_Energy Balance \\ PAMTest_Powerhouse kWh vs. PAMTest_Herentals, Biscuits \\ PAMTest_Energy Balance \\ PAMTest_Powerhouse m3");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1']/span").getText(), "PAMTest_Herentals, Biscuits \\ PAMTest_Energy Balance \\ PAMTest_Powerhouse kWh vs. PAMTest_Herentals, Biscuits \\ PAMTest_Energy Balance \\ PAMTest_Powerhouse m3");
			
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-2']/span").getText(), "PAMTest_Herentals, Biscuits \\ PAMTest_Energy Balance \\ PAMTest_Production Line kWh vs. PAMTest_Herentals, Biscuits \\ PAMTest_Energy Balance \\ PAMTest_Production Line m3");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-3']/span").getText(), "PAMTest_Herentals, Biscuits \\ PAMTest_Energy Balance \\ PAMTest_Production Line kWh vs. PAMTest_Herentals, Biscuits \\ PAMTest_Energy Balance \\ PAMTest_Production Line m3");
			printLog("Verified the extended legend displays under the chart by default");
			
			// Click on KabobMenu and select Chart Options.
			kabobMenuOptions("Chart Options");
			//Select Hide Location Path
			getWebElementXpath("LegendHideLocationPath").click();
			getWebElementXpath("SaveAndClose").click();
			aJaxWait();		
			//Verify that legend hide location path under the chart.
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/span").getText(), "PAMTest_Powerhouse kWh vs. PAMTest_Powerhouse m3");			
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1']/span").getText(), "PAMTest_Powerhouse kWh vs. PAMTest_Powerhouse m3");			
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-2']/span").getText(), "PAMTest_Production Line kWh vs. PAMTest_Production Line m3");			
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-3']/span").getText(), "PAMTest_Production Line kWh vs. PAMTest_Production Line m3");
			
			kabobMenuOptions("Chart Options");
			Thread.sleep(2000);
			// select Hide Legend checkbox.
			getWebElementXpath("HideLegend").click();
			getWebElementXpath("SaveAndClose").click();
			aJaxWait();
			// Verify that Legend is hidden and no legend displays under the chart.
			int legendsCount = d.findElements(By.xpath("//*[@class='highcharts-legend']/div/div/div/span")).size();
			Assert.assertEquals(legendsCount, 0);
			printLog("Verify that Legend is hidden and no legend displays under the chart");
			
			kabobMenuOptions("Chart Options");
			Thread.sleep(2000);
			getWebElementXpath("ExtendLegend").click();
			// Click on Axes tab
			getWebElementXpath("AxisTabFromChartOptions").click();
			
			getWebElementXpath_D("//input[@id='ymaximum']").sendKeys("15000");
			getWebElementXpath_D("//input[@id='yminimum']").sendKeys("7500");
			getWebElementXpath_D("//input[@id='chartOptions_excludefrombasefit']").click();
			getWebElementXpath("SaveAndClose").click();
			//added more wait time for load the chart
			aJaxWait();
			aJaxWait();
			Dimension currentDimension = d.manage().window().getSize();
			if(currentDimension.getWidth() <= 1900) {
			Assert.assertTrue(d.findElement(By.xpath("//*[@class='highcharts-axis-labels highcharts-yaxis-labels ']")).getText().contains("15k") || d.findElement(By.xpath("//*[@class='highcharts-axis-labels highcharts-yaxis-labels ']")).getText().contains("15000"));
			}
			
			legendsCount = d.findElements(By.xpath("//*[@class='highcharts-legend']/div/div/div/span")).size();
			Assert.assertEquals(legendsCount, 3);
			printLog("Verified that this option excludes any hidden points from the best-fit line calculation.Only 3 legends were displaying.");
			
			login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}