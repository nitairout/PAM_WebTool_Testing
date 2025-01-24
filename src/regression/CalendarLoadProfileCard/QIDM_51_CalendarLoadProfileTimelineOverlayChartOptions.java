package regression.CalendarLoadProfileCard;

import java.time.LocalDate;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test validates the calendar load profile default timeline options, 
 * changing to different month, changing week Begins, Overlay and Chart Options. 
 * And then it pushes chart to dashboard and verifies chart on the widget is same as PAM card
 */

public class QIDM_51_CalendarLoadProfileTimelineOverlayChartOptions extends TestBase {
	LoginTC login = null;

	@Test
	public void calendarLoadProfileTimelineOverlayChartOptions() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Go to 'Temp Dashboard' under 'Automation Test Dashboards' folder
			Utility.goToPAMDashboard("Automation Test Dashboards", "Temp Dashboard");
			
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			goToAnalysisPage();

			// Navigate to Calendar Load Profile Analysis Card
			goToPAMCard("CalendarAnalysisCard");

			// Search for Site'PAMTest_Trasformatore 2' and select Electric Measurement
			searchSiteInLocationList("PAMTest_Trasformatore 2");
			getWebElementActionXpath_D("//tr[.='PAMTest_Capriata/Saiwa']/following-sibling::tr[.='PAMTest_Trasformatore 2'][1]/child::td[3]/child::span").click();
			printLog("Searched for 'PAMTest_Trasformatore 2' under PAMTest_Cabina 1 and clicked on Electric Measurement");

			refreshToLoadTheChart();
			
			LocalDate today = LocalDate.now();
			// Verify by default selected option should be 'This month'
			Assert.assertEquals(getWebElementXpath("SelectFromOptions").getText(), "This month");
			Assert.assertEquals(getWebElementXpath("ClpChartMonth").getText(), changeTheDateFormat("MMMM yyyy", today));
			printLog("Verified This Month is selected in time line option");
			
			//Verify the legend
			Assert.assertEquals(d.findElement(By.xpath("//span[@class='legend-text--calendar']/span")).getText(), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 2 kW");
			
			// Verified Time Line options for 'Last month'
			getWebElementActionXpath("LastMonth").click();
			Thread.sleep(2000);
			Assert.assertEquals(getWebElementXpath("SelectFromOptions").getText(), "Last month");
			Assert.assertEquals(getWebElementXpath("ClpChartMonth").getText(), changeTheDateFormat("MMMM yyyy", today.minusMonths(1)));
			printLog("Verified Last Month is selected in time line option");
			
			//Verify the legend
			Assert.assertEquals(d.findElement(By.xpath("//span[@class='legend-text--calendar']/span")).getText(), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 2 kW");
			
			// Selected the chart WeekBegins with Sunday
			getWebElementActionXpath("WeekBeginsSunday").click();
			Assert.assertEquals(getWebElementXpath("ChartCalendarStartDay").getText(), "SUN");
			printLog("Verified if Sunday as begins of  week in the chart");

			// Selected the chart WeekBegins with Monday
			getWebElementActionXpath("WeekBeginsMonday").click();
			Assert.assertEquals(getWebElementXpath("ChartCalendarStartDay").getText(), "MON");
			printLog("Verified if Monday as begins of  week in the chart");
			
			// Verified Time Line options for 'Fixed'
			fixedDateRangeForCLP("January", "2023");
			Thread.sleep(2000);

			// Go to Chart Options and choose Measurement and select the color for the Demand and verify on the chart
			kabobMenuOptions("Chart Options");
			getWebElementActionXpath("ChartOptionsMeasurement").click();
			getWebElementXpath("CLPFirstMeasurementDropdown").click();
			new Select(getWebElementXpath("CLPFirstMeasurementDropdown")).selectByValue("3: 3");
			getWebElementActionXpath("SaveAndClose").click();
			aJaxWait();
			Assert.assertTrue(getWebElementXpath("LegendIcon").getAttribute("style").contains("color: rgb(61, 205, 88)"));

			// Go to Chart Options and choose Commodity and select the color for the Electricity and verify on the chart
			kabobMenuOptions("Chart Options");
			getWebElementActionXpath("ChartOptionsCommodity").click();
			getWebElementXpath("CLPFirstMeasurementDropdown").click();
			new Select(getWebElementXpath("CLPFirstMeasurementDropdown")).selectByValue("4: 4");
			getWebElementActionXpath("SaveAndClose").click();
			aJaxWait();
			Assert.assertTrue(getWebElementXpath("LegendIcon").getAttribute("style").contains("color: rgb(177, 0, 67)"));

			// Select a day from the Chart and verify selected date is displayed under 'Selected Days'
			getWebElementActionXpath_D("(//*[@class='highcharts-series-group'])[1]").click();
			aJaxWait();
			String selectedDay = getWebElementXpath_D("//div[@class='groupTree']//span[@class='info']").getText();
			String expectedDate = "Jan 1, 2023"; //as fixed date range
			// changeTheDateFormat("MMM 1, yyyy", today.with(TemporalAdjusters.firstDayOfYear()));
			Assert.assertEquals(selectedDay, expectedDate);
			printLog("Verify the selected day as " + selectedDay);
			
			// Added the Overlay and Markers value and verify the marker line displays on the chart
			getWebElementActionXpath("OverlayExpander").click();
			Thread.sleep(1000);
			getWebElementActionXpath("MarkersCheckbox").click();
			Thread.sleep(1000);
			getWebElementActionXpath("DemandFromMarker").click();
			Thread.sleep(1000);
			getWebElementActionXpath("NewMarkerValue").sendKeys("250");
			Thread.sleep(1000);
			Assert.assertEquals(getWebElementActionXpath("MarkerLine").isDisplayed(), true);
			printLog("Verify the MarkerLine on a chart is displaying on the chart");

			// Select the Average Profile and verify the text 'Average' on the legend
			getWebElementActionXpath("AverageProfileCheckbox").click();
			aJaxWait();
			Assert.assertTrue(getWebElementXpath_D("//div[contains(@class,'legend-container')]/span[1]/span[2]/span").getText().contains("Average"));
			Assert.assertTrue(getWebElementXpath_D("//i[contains(@class,'fa fa-ellipsis-h legend-padding')]").getAttribute("style").contains("color: rgb(177, 0, 67)"));

			pushwidget("Temp Dashboard","Automation Test Dashboards");
			gotoDashBoardHome();
			aJaxWait();
			Thread.sleep(5000);
			//Verify the date range on the chart
			Assert.assertEquals(getWebElementXpath_D("//gridster/gridster-item[1]//div[@id='calendarChart']/div[@class='header-month']").getText(), "January 2023");
			printLog("Verify the date range as 'January 2023' on the chart");	
			
			//Verify the legend on the chart
			Assert.assertTrue(getWebElementXpath_D("//gridster/gridster-item[1]//div[@class='legend-container']/span[1]/span").getText().replaceAll("\\n", " ").contains("PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter \\ PAMTest_Electric \\ PAMTest_Cabina 1 \\ PAMTest_Trasformatore 2 kW"));
			Assert.assertTrue(getWebElementXpath_D("//gridster/gridster-item[1]//div[@class='legend-container']/span[2]/span").getText().replaceAll("\\n", " ").contains("PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter \\ PAMTest_Electric \\ PAMTest_Cabina 1 \\ PAMTest_Trasformatore 2 kW - Average"));
			printLog("Verify the legends on the chart");
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
