package smokeTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;

/*
 * This test verifies very basic functionality of 'Calendar Load Profile Analysis Card' to make sure card is working as expected after new builds are deployed.  
 * It verifies if chart is loaded with default configurations, verifies data is there on chart and table tab.  
 */
public class QIDM_148_CalendarLoadProfileAnalysisSmokeTest extends TestBase {
	LoginTC login = null;

	@Test
	public void calendarLoadProfileAnalysisSmokeTest() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");

			goToAnalysisPage();
			goToPAMCard("CalendarAnalysisCard");

			// Verify the default message
			Assert.assertTrue(d.getPageSource().contains(testData.getProperty("defaultMessage")));

			// Verify the widget title as 'New Calendar Load Profile Analysis'
			Assert.assertEquals(getWebElementXpath("WidgetHeader").getText(), "New Calendar Load Profile Analysis");
			printLog("Verified the card title as 'New Calendar Load Profile Analysis'");

			String[] expectedDefaultMeasuremets = { "electricity-Demand", "gas-Volume", "gas-Energy", "water-Volume", "steam-Energy", "weather-Temperature", "weather-Relative Humidity" };
			// Click on electric commodity icon to enter into select measurement pop up
			getWebElementActionXpath_D("//table/thead/tr[1]/th//i[contains(@class,'se-icon-electricity-o com-header')]").click();
			// Verify the default measurements
			List<WebElement> defaultMeasurements = d.findElements(By.xpath("//div[@class='selected-measurements-list-container']//child::div[contains(@class,'measurement-list-item')]"));
			Assert.assertEquals(defaultMeasurements.size(), 7);

			for (int i = 0; i < expectedDefaultMeasuremets.length; i++) {
				String measurementXpath="(//div[@class='selected-measurements-list-container']//child::div[contains(@class,'measurement-name')])["+(i+1)+"]/span";
				Assert.assertEquals(getWebElementActionXpath_D(measurementXpath).getText(), (expectedDefaultMeasuremets[i].split("-")[1]));
				Assert.assertTrue(d.findElement(By.xpath("(//div[contains(@class,'measurement-list-item')]/child::i[1])["+(i+1)+"]")).getAttribute("class").contains((expectedDefaultMeasuremets[i].split("-")[0])));
			}
			printLog("Verified the default measurements available for the Calendar Load Profile Analysis card");
			// Click on save and close
			getWebElementActionXpath_D("//span[contains(text(),'Save & Close')]").click();
			Thread.sleep(5000);

			// Search with site testData.getProperty("PAMTestCapriataSaiwa")
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			System.out.println("This test will fail intentionally");
			Reporter.log("This test will fail intentionally");
int i=10/0;
/*
			// Click on the measurements
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			getWebElementXpath("PAMTest_CapriataSaiwa_Gas").click();
			getWebElementXpath("PAMTest_CapriataSaiwa_Water").click();
			getWebElementXpath("PAMTest_CapriataSaiwa_Weather").click();

			refreshToLoadTheChart();

			LocalDate today = LocalDate.now();
			// Verify by default selected option should be 'This month'
			Assert.assertEquals(getWebElementXpath("SelectFromOptions").getText(), "This month");
			Assert.assertEquals(getWebElementXpath("ClpChartMonth").getText(), changeTheDateFormat("MMMM yyyy", today));
			printLog("Verified the default Date Range of the card");

			ArrayList<String> legendList=new ArrayList<String>();
			legendList.add("PAMTest_Capriata/Saiwa kW");
			legendList.add(testData.getProperty("PAMTest_CapriataSaiwam3"));
			legendList.add(testData.getProperty("PAMTest_CapriataSaiwaGJ"));
			legendList.add(testData.getProperty("PAMTest_CapriataSaiwam3"));
			legendList.add(testData.getProperty("PAMTest_CapriataSaiwa0C"));
			legendList.add("PAMTest_Capriata/Saiwa %");
			
			// Verify the legends
			int legendSize=d.findElements(By.xpath("//span[@class='legend-text--calendar']")).size();
			for(int i=1;i<=legendSize;i++) {
				Assert.assertTrue(legendList.contains(getWebElementXpath_D("(//span[@class='legend-text--calendar'])["+i+"]").getText().replaceAll("\\n", " ").trim()));
			}
			printLog("Verified the Calendat Load Profile Analysis Card Legends");
			
			String defaultWeekBegins = d.findElement(By.xpath("//div[@id='timelineCalendar']//button[contains(@class,'k-selected')]")).getText();
			if(defaultWeekBegins.equalsIgnoreCase("Sunday")) {
				getWebElementActionXpath("WeekBeginsMonday").click();
				Thread.sleep(2000);
				defaultWeekBegins = d.findElement(By.xpath("//div[@id='timelineCalendar']//button[contains(@class,'k-selected')]")).getText();
			}
			Assert.assertEquals(defaultWeekBegins, "Monday");
			printLog("Verified that default Week begins with Monday");
			
			// Select a date from the chart
			//getWebElementActionXpath_D("(//*[@class='highcharts-series-group'])[7]").click();
			getWebElementActionXpath_D("(//*[@class='highcharts-root'])[1]").click();
			Thread.sleep(2000);

			// Select a day from the Chart and verify selected date is displayed under 'Selected Days'
			String selectedDay = getWebElementXpath_D("//div[@class='groupTree']//span[@class='info']").getText();
			Assert.assertNotNull(selectedDay);
			printLog("Verify the selected day as " + selectedDay);
			String legendPath,actualLegendName;
			//Verify the legends on the chart
			for(int i=0;i<4;i++) {
				legendPath = "//div[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-"+i+"']/span";
				actualLegendName = d.findElement(By.xpath(legendPath)).getText().replaceAll("\\n", " ");
				
				//This is used to convert the date( remove comma)
				DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM d, yyyy");
		        LocalDate localDate = LocalDate.parse(selectedDay, parser);
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
		        String formattedSelectedDate = localDate.format(formatter);
		        
				Assert.assertTrue(actualLegendName.contains(formattedSelectedDate));
			}
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);

			//Verify the hourly row data(01.00 interval i.e 4th row from table) should not be null
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablebody']/tr[4]/th").getText(), "01:00");
			Assert.assertNotNull(getWebElementXpath_D("//*[@id='tablebody']/tr[4]/td[1]").getText());
			Assert.assertNotNull(getWebElementXpath_D("//*[@id='tablebody']/tr[4]/td[2]").getText());
			Assert.assertNotNull(getWebElementXpath_D("//*[@id='tablebody']/tr[4]/td[3]").getText());
			Assert.assertNotNull(getWebElementXpath_D("//*[@id='tablebody']/tr[4]/td[4]").getText());
			Assert.assertNotNull(getWebElementXpath_D("//*[@id='tablebody']/tr[4]/td[5]").getText());
			Assert.assertNotNull(getWebElementXpath_D("//*[@id='tablebody']/tr[4]/td[6]").getText());
			printLog("Verified 4th row of table date which is 01:00 interval for default date range");

			login.logout();
*/
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}