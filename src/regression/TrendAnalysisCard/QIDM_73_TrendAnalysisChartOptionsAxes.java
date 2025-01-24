package regression.TrendAnalysisCard;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies Axes-chart option for trend analysis and pushes same chart to the widget
 *  and verified axes settings are same as chart
 */
public class QIDM_73_TrendAnalysisChartOptionsAxes extends TestBase {

	LoginTC login = null;

	@Test
	public void trendAnalysisChartOptionsAxes() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Go to 'Temp Dashboard' under 'Automation Test Dashboards' folder
			Utility.goToPAMDashboard("Automation Test Dashboards", "Temp Dashboard");
			
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			goToAnalysisPage();

			// Navigate to TrendAnalysis Card
			goToPAMCard("TrendAnalysisCard");
			
			//Searching with "PAMTest_Capriata/Saiwa" and expanding to select PAMTest_Energy Balance & PAMTest_Main Meter energy
			searchSiteInLocationList("PAMTest_Capriata/Saiwa");
			getWebElementActionXpath_D("//td[normalize-space()='PAMTest_Capriata/Saiwa']//*[contains(@class,'k-svg-icon')]").click();
			Thread.sleep(1000);
			//Energy
			getWebElementXpath_D("//td[normalize-space()='PAMTest_Energy Balance']/following-sibling::td[2]/child::span").click();
			Thread.sleep(1000);
			//Gas
			getWebElementXpath_D("//td[normalize-space()='PAMTest_Energy Balance']/following-sibling::td[3]/child::span").click();
			Thread.sleep(1000);
			
			//Energy
			getWebElementXpath_D("//td[normalize-space()='PAMTest_Main Meter']/following-sibling::td[2]/child::span").click();
			Thread.sleep(1000);
			//Gas
			getWebElementXpath_D("//td[normalize-space()='PAMTest_Main Meter']/following-sibling::td[3]/child::span").click();
			Thread.sleep(1000);
			
			printLog("Selected Electricity Energy & Gas Volume for sites PAMTest_CapriataSaiwa and PAMTest_EnergyBalance");
			Thread.sleep(1000);

			// Select the Fixed date range of '01/01/2023' - '01/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();
			aJaxWait();
			// verify start and end date from chart
			Assert.assertEquals(getWebElementXpath("ChartStartDate").getText(), testData.getProperty("FixedDateStartJan012023"));
			Assert.assertEquals(getWebElementXpath("ChartEndDate").getText(), testData.getProperty("FixedDateEndJan312023"));
			printLog("Verified the Fixed date range from chart");
			
			// Verify the legends display under the chart.
			ArrayList<String> legendList=new ArrayList<String>();
			legendList.add("PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance kWh");legendList.add("PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kWh");legendList.add("PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance GJ");
			legendList.add("PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter GJ");legendList.add("PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance m3");legendList.add("PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter m3");
			Assert.assertTrue(legendList.contains(getWebElementXpath("ColumnLegendOne").getText()));
			Assert.assertTrue(legendList.contains(getWebElementXpath("ColumnLegendTwo").getText()));
			Assert.assertTrue(legendList.contains(getWebElementXpath("ColumnLegendThree").getText()));
			Assert.assertTrue(legendList.contains(getWebElementXpath("ColumnLegendFour").getText()));
			Assert.assertTrue(legendList.contains(getWebElementXpath("ColumnLegendFive").getText()));
			Assert.assertTrue(legendList.contains(getWebElementXpath("ColumnLegendSix").getText()));
			printLog("Verified legends from the chart.");
			
			//verified the primary and secondary y-axis before configuration
			String[] primaryYAxisList = getWebElementXpath("PrimaryYaxisChart").getText().replaceAll("\\n", " ").split(" ");
			Assert.assertNotEquals(primaryYAxisList[primaryYAxisList.length-1], "40k");
			String[] secondaryYAxisList = getWebElementXpath("SecondaryYaxisChart").getText().replaceAll("\\n", " ").split(" ");
			Assert.assertNotEquals(secondaryYAxisList[secondaryYAxisList.length-1], "2000");
			printLog("Verified the primary and secondary y-axis from chart before configuration");
			
			// Click on KabobMenu and select Chart Options.
			kabobMenuOptions("Chart Options");
			// Click on Axes tab
			getWebElementXpath("AxisTabFromChartOptions").click();
			
			//Verified by Default Dynamic Axis Setting enable
			Assert.assertEquals(getWebElementXpath("DynamicAxisSettings").getAttribute("checked"),"true");
			Assert.assertEquals(getWebElementXpath("YAxisStartsAtZero").isDisplayed(),true);
			printLog("Verified by Default Dynamic Axis Setting enable");
			
			getWebElementXpath("FixedAxisSettings").click();			
			getWebElementXpath("PrimaryYaxisMax").sendKeys("4000");
			getWebElementXpath("SecondaryYaxisMax").sendKeys("2000");
			
			getWebElementXpath("SaveAndClose").click();
			//added more wait time for load the chart
			aJaxWait();
			
			//verified the primary and secondary y-axis after setup
			primaryYAxisList = getWebElementXpath("PrimaryYaxisChart").getText().replaceAll("\\n", " ").split(" ");
			Assert.assertTrue(primaryYAxisList[primaryYAxisList.length-1].equalsIgnoreCase("4000") || primaryYAxisList[primaryYAxisList.length-1].equalsIgnoreCase("4k"));
			secondaryYAxisList = getWebElementXpath("SecondaryYaxisChart").getText().replaceAll("\\n", " ").split(" ");
			Assert.assertTrue(secondaryYAxisList[secondaryYAxisList.length-1].equalsIgnoreCase("2000") || secondaryYAxisList[secondaryYAxisList.length-1].equalsIgnoreCase("2k"));
			printLog("Verified the primary and secondary y-axis from chart after configuration");
			
			//Pushed the widget to dashboard
			pushwidget("Temp Dashboard","Automation Test Dashboards");
			gotoDashBoardHome();
			aJaxWait();
			Thread.sleep(15000);//loading chart on dashboard page takes more time
			
			//Enlarge the dashboard widget size
			Utility.dragAndDrop(locators.getProperty("WidgetDragAndDropXpath"));
			Thread.sleep(5000);
			
			Assert.assertTrue(getWebElementActionXpath("PrimaryYaxisWidgetChart").getText().contains("4000") || getWebElementActionXpath("PrimaryYaxisWidgetChart").getText().contains("4k"));
			Assert.assertTrue(getWebElementActionXpath("SecondaryYaxisWidgetChart").getText().contains("2000") || getWebElementActionXpath("SecondaryYaxisWidgetChart").getText().contains("2k"));
			printLog("Verified the primary and secondary y-axis from dashboard widget chart");
			// click on logout
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

}
