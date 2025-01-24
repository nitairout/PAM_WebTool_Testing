package regression.LoadProfileAnalysisCard;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies Axes-chart option for Load profiler analysis and pushes same chart to the widget
 * and verified axes settings are same as chart
 */
public class QIDM_179_LoadProfileChartOptionsAxes extends TestBase{
	LoginTC login = null;
	@Test
	public void loadProfileChartOptionsAxes() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Go to 'Temp Dashboard' under 'Automation Test Dashboards' folder
			Utility.goToPAMDashboard("Automation Test Dashboards", "Temp Dashboard");
			
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			// Select Electric and Gas commodity for a site PAMTest_Energy Balance.
			Utility.selectMultipleMeasurements("Electricity#Energy~standard|Gas#Volume~standard");
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			getWebElementXpath("PAMTest_CapriataSaiwa_Gas").click();
			
			// Select the Fixed date range of '01/01/2023' - '01/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();
			
			// verify start and end date from chart
			String expectedStartAndEndDate = "1/1/2023 12:00 AM - 2/1/2023 12:00 AM";
			String actualStartEndDateFromChart=getWebElementActionXpath_D("//*[contains(@class,'highcharts-title')]").getText();
			Assert.assertEquals(actualStartEndDateFromChart, expectedStartAndEndDate);
			printLog("Verified the Fixed date range from chart");
			
			// Verify the legends display under the chart.
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText(), testData.getProperty("PAMTest_CapriataSaiwam3"));
			
			//verified the primary and secondary y-axis before configuration
			String[] primaryYAxisList = getWebElementXpath("PrimaryYaxisChart").getText().replaceAll("\\n", " ").split(" ");
			Assert.assertNotEquals(primaryYAxisList[primaryYAxisList.length-1], "400");
			String[] secondaryYAxisList = getWebElementXpath("SecondaryYaxisChart").getText().replaceAll("\\n", " ").split(" ");
			Assert.assertNotEquals(secondaryYAxisList[secondaryYAxisList.length-1], "300");
			printLog("Verified the primary and secondary y-axis from chart before configuration");
			
			// Click on KabobMenu and select Chart Options.
			kabobMenuOptions("Chart Options");
			
			// Click on Axes tab
			getWebElementXpath("AxisTabFromChartOptions").click();
			
			//Verified by Default Dynamic Axis Setting enable
			Assert.assertTrue(getWebElementXpath("DynamicAxisSettings").isSelected());
			Assert.assertTrue(getWebElementXpath("IgnoreExtremeDataPoints").isSelected());
			Assert.assertTrue(getWebElementXpath("YAxisStartsAtZero").isSelected());
			printLog("Verified by Default Dynamic Axis Setting enable");
			
			getWebElementXpath("FixedAxisSettings").click();			
			getWebElementXpath("PrimaryYaxisMax").sendKeys("400");
			getWebElementXpath("SecondaryYaxisMax").sendKeys("300");
			
			getWebElementXpath("SaveAndClose").click();
			//added more wait time for load the chart
			aJaxWait();
			aJaxWait();
			
			//verified the primary and secondary y-axis after configuration
			primaryYAxisList = getWebElementXpath("PrimaryYaxisChart").getText().replaceAll("\\n", " ").split(" ");
			Assert.assertEquals(primaryYAxisList[primaryYAxisList.length-1], "400");
			
			secondaryYAxisList = getWebElementXpath("SecondaryYaxisChart").getText().replaceAll("\\n", " ").split(" ");
			Assert.assertEquals(secondaryYAxisList[secondaryYAxisList.length-1], "300");
			printLog("Verified the primary and secondary y-axis from chart after configuration");
			
			//pushWidget()
			pushwidget("Temp Dashboard","Automation Test Dashboards");
			gotoDashBoardHome();
			
			Assert.assertTrue(getWebElementActionXpath("PrimaryYaxisWidgetChart").getText().contains("400"));
			Assert.assertTrue(getWebElementActionXpath("SecondaryYaxisWidgetChart").getText().contains("300"));
			printLog("Verified the primary and secondary y-axis from dashboard widget chart");
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
