package regression.ComparisonAnalysisCard;

import java.util.ArrayList;
import java.util.Collections;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test validates Comparison Analysis chart options, which includes changing measurements and commodity series colors, hiding legend and etc. 
 * */

public class QIDM_81_ComparisonAnalysisChartOptions extends TestBase {
	LoginTC login = null;

	@Test
	public void comparisonAnalysisChartOptions() throws Throwable {
		try {

			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			
			goToPAMCard("ComparisonAnalysisCard");
			Utility.selectMultipleMeasurements("Electricity#Energy~standard|Gas#Volume~standard");
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			getWebElementXpath("PAMTest_CapriataSaiwa_Gas").click();
			refreshToLoadTheChart();
			searchSiteInLocationList(testData.getProperty("PAMTestHerentalsBiscuits"));
			getWebElementXpath("PAMTest_HerentalsBiscuits_Energy").click();
			getWebElementXpath("PAMTest_HerentalsBiscuits_Gas").click();
			printLog("Selected Electricity Energy & Gas Volume with PAMTestCapriataSaiwa and PAMTest_Herentals, Biscuits site ");

			refreshToLoadTheChart();
			// Closing the pop up if appearing on the chart
			if (getWebElementActionXpath("AlertMessage").isDisplayed()) {
				getWebElementActionXpath("AlertMessage").click();
			}

			// Verify the chart legends for both measurements
			String legendOne = getWebElementXpath("ColumnLegendOne").getText();
			String legendTwo = getWebElementXpath("ColumnLegendTwo").getText();
			
			//Verified the Legend on the chart
			ArrayList<String> actualLegend = new ArrayList<String>();
			actualLegend.add(legendOne);
			actualLegend.add(legendTwo);
			
			ArrayList<String> expectedLegend = new ArrayList<String>();
			//Updating from 'Location Measurement Unit' to 'Location Unit' - on 'May 5th 2024'
			//expectedLegend.add("Volume m3");
			//expectedLegend.add("Energy kWh");
			expectedLegend.add("m3");
			expectedLegend.add("kWh");
			
			Collections.sort(actualLegend);
			boolean flag;
			flag = actualLegend.containsAll(expectedLegend);
			Assert.assertTrue(flag);			
			printLog("Verified the legends on the chart.");
			
			//Assert.assertEquals(legendTwo, "Volume m3");
			//Assert.assertEquals(legendOne, "Energy kWh");
			
			// verify the default color for Volume measurement legend 
			Assert.assertEquals(getWebElementXpath("LegendTwo").getAttribute("fill"), "#f5a300");

			// Go to chart options and selected Volume Measurement as Green color
			kabobMenuOptions("Chart Options");
			getWebElementActionXpath("ChartOptionsMeasurement").click();
			getWebElementActionXpath("SecondMeasurementDropdown").click();
			new Select(getWebElementXpath("SecondMeasurementDropdown")).selectByValue("3: 3");
			getWebElementActionXpath("SaveAndClose").click();
			aJaxWait();
			
			// verify the changed color of legend should be Green
			Assert.assertEquals(getWebElementXpath("LegendTwo").getAttribute("fill"), "#3dcd58");

			// Closing the pop up if appearing on the chart
			if (getWebElementActionXpath("AlertMessage").isDisplayed()) {
				getWebElementActionXpath("AlertMessage").click();
			}
			// verify the default color for Energy Commodity legend 
			Assert.assertEquals(getWebElementXpath("LegendOne").getAttribute("fill"), "#2fb5ea");
			
			// Go to chart options and then select Commodity and selected Electricity as wine color
			kabobMenuOptions("Chart Options");
			getWebElementXpath("ChartOptionsCommodity").click();
			getWebElementXpath("FirstMeasurementDropdown").click();
			new Select(getWebElementXpath("FirstMeasurementDropdown")).selectByValue("4: 4");
			getWebElementXpath("SaveAndClose").click();
			aJaxWait();
			
			// verify the changed color of legend should be wine color
			Assert.assertEquals(getWebElementXpath("LegendOne").getAttribute("fill"), "#b10043");

			// Unselect the Gas commodity
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Gas").click();
			refreshToLoadTheChart();
			searchSiteInLocationList(testData.getProperty("PAMTestHerentalsBiscuits"));
			getWebElementXpath("PAMTest_HerentalsBiscuits_Gas").click();
			refreshToLoadTheChart();
			
			kabobMenuOptions("Chart Options");
			// Click on Legend tab
			getWebElementXpath("ChartOptionsLegendTab").click();
			Thread.sleep(2000);
			getWebElementActionXpath("HideLegend").click();
			getWebElementXpath("ShowLabelsOnPieChart").click();
			getWebElementXpath("SaveAndClose").click();
			aJaxWait();
			Thread.sleep(5000);
			
			//Verify the legends are not populating on the chart
			int legendsList = d.findElements(By.xpath("//div[@class='highcharts-legend-item highcharts-column-series highcharts-color-undefined highcharts-series-0']/span")).size();
			Assert.assertEquals(legendsList, 0);

			// Go to Pie chart and verify the labels on the pie chart
			if(!checkBrowserHeadless) {
				getWebElementActionXpath("PieChart").click();
				aJaxWait();
			}
			//Verified the sites on the chart
			ArrayList<String> actualchartLabels = new ArrayList<String>();
			actualchartLabels.add(getWebElementActionXpath_D("(//*[@class='highcharts-text-outline'][2])[1]").getText());
			actualchartLabels.add(getWebElementActionXpath_D("(//*[@class='highcharts-text-outline'][2])[2]").getText());
			
			ArrayList<String> expectedchartLabels = new ArrayList<String>();
			expectedchartLabels.add("PAMTest_Herentals, Biscu\u2026");
			expectedchartLabels.add(testData.getProperty("PAMTestCapriataSaiwa"));
			
			Collections.sort(actualchartLabels);
			boolean flag1;
			flag1 = actualchartLabels.containsAll(expectedchartLabels);
			Assert.assertTrue(flag1);			
			
			//Assert.assertEquals(getWebElementActionXpath_D("(//*[@class='highcharts-text-outline'][2])[1]").getText(), "PAMTest_Herentals, Biscu\u2026");
			//Assert.assertEquals(getWebElementActionXpath_D("(//*[@class='highcharts-text-outline'][2])[2]").getText(), testData.getProperty("PAMTestCapriataSaiwa"));

			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}