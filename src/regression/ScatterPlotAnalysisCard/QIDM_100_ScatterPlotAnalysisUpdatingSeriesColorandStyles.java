package regression.ScatterPlotAnalysisCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies changing series color and type from legend for each series on PAM card and on widget.
 */
public class QIDM_100_ScatterPlotAnalysisUpdatingSeriesColorandStyles extends TestBase{
	LoginTC login = null;
	@Test
	public void scatterPlotAnalysisUpdatingSeriesColorandStyles() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Go to 'Temp Dashboard' under 'Automation Test Dashboards' folder
			Utility.goToPAMDashboard("Automation Test Dashboards", "Temp Dashboard");
			
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			
			//Navigate to PAM page
			goToAnalysisPage();
			
			//Navigate to Scatter Plot Analysis Card page
			goToPAMCard("ScatterPlotCard");
			printLog("Navigated to Scatter plot Card page");
			//Added the Fixed date
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"),testData.getProperty("FixedDateEndJan312023"));
			//Selecting Energy and Volume measurements for PAMTest_Herentals, Biscuits
			Utility.selectMultipleMeasurements("Electricity#Energy~standard|Gas#Volume~standard");
			searchSiteInLocationList(testData.getProperty("PAMTestHerentalsBiscuits"));
			getWebElementXpath("PAMTest_HerentalsBiscuits_Energy").click();
			getWebElementXpath("PAMTest_HerentalsBiscuits_Gas").click();
			refreshToLoadTheChart();
			
			//Enlarge PAMTest_Herentals, Biscuits site to select PAMTest_Energy Balance energy and gas
			getWebElementXpath_D("//td[normalize-space()='"+testData.getProperty("PAMTestHerentalsBiscuits")+"']//kendo-svgicon[contains(@class,'k-svg-i-caret')]").click();
			//Selecting Energy and Volume measurements for 'PAMTest_Energy Balance'
			getWebElementXpath_D("//table/tbody/tr[2]/td[3]/child::span").click();
			getWebElementXpath_D("//table/tbody/tr[2]/td[4]/child::span").click();
			printLog("Selected Energy and Volume measurements for PAMTest_Herentals, Biscuits & PAMTest_Energy Balance sites");
			refreshToLoadTheChart();
			Thread.sleep(20000);
			
			//Verify default values for Legend color and marker styles
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/*[@class='highcharts-graph']").getAttribute("stroke"), "#2fb5ea");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/*[@class='highcharts-graph']").getAttribute("d"), "M 0 11 L 16 11");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/span").getText(), "PAMTest_Herentals, Biscuits kWh vs. PAMTest_Herentals, Biscuits m3");

			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1']/*[@class='highcharts-point']").getAttribute("fill"), "#f5a300");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1']/*[@class='highcharts-point']").getAttribute("d"), "M 8 7 L 12 11 8 15 4 11 Z");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1']/span").getText(), "PAMTest_Herentals, Biscuits kWh vs. PAMTest_Herentals, Biscuits m3");
			
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-2']/*[@class='highcharts-graph']").getAttribute("stroke"), "#1a7aaa");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-2']/*[@class='highcharts-graph']").getAttribute("d"), "M 0 11 L 16 11");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-2']/span").getText(), "PAMTest_Herentals, Biscuits \\ PAMTest_Energy Balance kWh vs. PAMTest_Herentals, Biscuits \\ PAMTest_Energy Balance m3");
			
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-3']/*[@class='highcharts-point']").getAttribute("fill"), "#ec5a25");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-3']/*[@class='highcharts-point']").getAttribute("d"), "M 8 7 L 12 15 4 15 Z");
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-3']/span").getText(), "PAMTest_Herentals, Biscuits \\ PAMTest_Energy Balance kWh vs. PAMTest_Herentals, Biscuits \\ PAMTest_Energy Balance m3");
			printLog("Verified default values for Legend color and marker styles");
			
			//Click on first legend icon and change the color
			//Utility.moveTheElementandClick("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/*[@class='highcharts-graph']");
			Utility.moveTheElementandClick("(//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0'])[1]");
			Thread.sleep(5000);
			getWebElementXpath_D("//div[@class='colorPicker-picker']").click();
			
			//select orange color (second color)
			getWebElementXpath_D("//div[@class='colorPicker-swatch'][2]").click();
			Thread.sleep(1000);
			
			//Verify the color after change
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/*[@class='highcharts-graph']").getAttribute("stroke"), "#f5a300");
			printLog("Verified modified Legend color.");
			
			//Change the Marker type
			Utility.moveTheElementandClick("(//*[@class='highcharts-legend']//*[@class='highcharts-point'])[1]");
			Thread.sleep(3000);
			//Select marker type as circle
			getWebElementXpath_D("//div[contains(@class,'pam-popover-row')]//i[contains(@class,'fa-circle')]").click();
			//close the popup
			getWebElementXpath_D("//i[@class='fa fa-close pull-right']").click();
			Thread.sleep(4000);
			
			//Verify the marker after change
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1']/*[@class='highcharts-point']").getAttribute("d"), "M 12 11 A 4 4 0 1 1 11.999998000000167 10.996000000666664 Z");
			printLog("Verified modified Legend marker style.");
			
			Utility.moveTheElement(locators.getProperty("toolTipBar"));
			Thread.sleep(1000);
			WebElement tip = explicitWait_Xpath(locators.getProperty("tooltiptext"));
			Thread.sleep(1000);
			List<WebElement> icon = d.findElements(By.xpath("//div[@class='widget-tooltip']/div/span/i"));
			Assert.assertTrue(icon.size() >0);
			String[] toolTipInfo = tip.getText().split("\\n");
			Assert.assertEquals(toolTipInfo[0], "Sun 1/1/2023");
			Assert.assertNotNull(toolTipInfo[1]);
			Assert.assertNotNull(toolTipInfo[2]);
			printLog("Verified tooltip values on the chart");
			
			ArrayList<String> legendNames = new ArrayList<String>();
			legendNames.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/span").getText());
			legendNames.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1']/span").getText());
			legendNames.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-2']/span").getText());
			legendNames.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-3']/span").getText());
			
			ArrayList<String> legendColors = new ArrayList<String>();
			legendColors.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/*[@class='highcharts-graph']").getAttribute("fill"));
			legendColors.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1']/*[@class='highcharts-graph']").getAttribute("fill"));
			legendColors.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-2']/*[@class='highcharts-graph']").getAttribute("fill"));
			legendColors.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-3']/*[@class='highcharts-graph']").getAttribute("fill"));
			
			ArrayList<String> legendStyles = new ArrayList<String>();
			legendStyles.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/*[@class='highcharts-graph']").getAttribute("d"));
			legendStyles.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1']/*[@class='highcharts-graph']").getAttribute("d"));
			legendStyles.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-2']/*[@class='highcharts-graph']").getAttribute("d"));
			legendStyles.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-3']/*[@class='highcharts-graph']").getAttribute("d"));
			
			pushwidget("Temp Dashboard","Automation Test Dashboards");
			gotoDashBoardHome();
			aJaxWait();
			Thread.sleep(5000);
			
			//Verify values for Legend color and marker styles on the widget chart 
			ArrayList<String> legendNamesWidget = new ArrayList<String>();
			legendNamesWidget.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/span").getText());
			legendNamesWidget.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1']/span").getText());
			legendNamesWidget.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-2']/span").getText());
			legendNamesWidget.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-3']/span").getText());
			
			ArrayList<String> legendColorsWidget = new ArrayList<String>();
			legendColorsWidget.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/*[@class='highcharts-graph']").getAttribute("fill"));
			legendColorsWidget.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1']/*[@class='highcharts-graph']").getAttribute("fill"));
			legendColorsWidget.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-2']/*[@class='highcharts-graph']").getAttribute("fill"));
			legendColorsWidget.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-3']/*[@class='highcharts-graph']").getAttribute("fill"));
			
			ArrayList<String> legendStylesWidget = new ArrayList<String>();
			legendStylesWidget.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/*[@class='highcharts-graph']").getAttribute("d"));
			legendStylesWidget.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1']/*[@class='highcharts-graph']").getAttribute("d"));
			legendStylesWidget.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-2']/*[@class='highcharts-graph']").getAttribute("d"));
			legendStylesWidget.add(getWebElementXpath_D("//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-3']/*[@class='highcharts-graph']").getAttribute("d"));
			
			Collections.sort(legendNames);
			Collections.sort(legendNamesWidget);
			
			Collections.sort(legendColors);
			Collections.sort(legendColorsWidget);
			
			Collections.sort(legendStyles);
			Collections.sort(legendStylesWidget);
			
			boolean nameflag,colorflag,styleflag;
			nameflag = legendNames.equals(legendNamesWidget);
			Assert.assertTrue(nameflag);
			
			colorflag = legendColors.equals(legendColorsWidget);
			Assert.assertTrue(colorflag);
			
			styleflag = legendStyles.equals(legendStylesWidget);
			Assert.assertTrue(styleflag);
			
			printLog("Verified values for Legend Names, Legend color and marker styles on the chart");

			login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}