package regression.AllCardsCommonTCs;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;

/*
 * This test verifies Last 'Days/Weeks/Months/Years' options available under timeline options. 
 * For each option it checks if we get warning message when entered more than expected days/weeks/months /years.
 * Then it loads chart for each option and verifies if 'Include Current Day/week/month/Year works as expected
 * by checking the date range on the top.
 */
public class QIDM_214_AllCardsTimelineLastXIntervals extends TestBase{
	LoginTC login = null;
	@Test
	public void allCardsTimelineLastXIntervals() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			refreshToLoadTheChart();
			
			String timeLinePath = "//input[@id='timelineLastx']";
			String msgPath = "//div[contains(@class,'alert-warning')]/small";
			String disabledCheckbox = "//select[@id='timelineLastxInterval']/following-sibling::span[contains(@class,'disabled')]";
			String checkbox = "//i[@class='fa fa-check']";
			String includeCurrentCheck = "//span[starts-with(text(),'Include current')]/preceding-sibling::span/i";
			String includeCurrentUnCheck = "//span[starts-with(text(),'Include current')]/preceding-sibling::span";
			DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;
			DayOfWeek lastDayOfWeek = DayOfWeek.SUNDAY;
			
			//Provide 200 days under 'Last X days time line options verify the message and check box should be in disabled/Hidden
			getWebElementXpath_D(timeLinePath).click();
			getWebElementXpath_D(timeLinePath).clear();
			getWebElementXpath_D(timeLinePath).sendKeys("200");
			Assert.assertEquals(d.findElement(By.xpath(msgPath)).getText(),"Please select a value equal to or less than 180.");
			Assert.assertTrue((d.findElements(By.xpath(disabledCheckbox)).size() > 0));
			
			//Provide 2 days under 'Last X days time line options
			getWebElementXpath_D(timeLinePath).click();
			getWebElementXpath_D(timeLinePath).clear();
			getWebElementXpath_D(timeLinePath).sendKeys("2");
			getWebElementXpath_D(checkbox).click();
			Thread.sleep(5000);
			
			//All variable declaration
			LocalDate today = LocalDate.now();
			String expectedStartChartDate,expectedEndChartDate,actualStartChartDate,actualEndChartDate;
			
			//Default last 2 days chart data 
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy", today.plusDays(1)) + " 12:00 AM";
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.minusDays(1)) + " 12:00 AM";
			//verify start and end date from chart 
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
			
			//Since the include checkbox xpath is changing dynamically we are using this approch to check and uncheck the checkbox.
			//if the checkbox is checked the size is 2 and if the checkbox is unchecked the size is 1.
			
			//uncheck the 'Include Current Day' 
			if(d.findElements(By.xpath(includeCurrentCheck)).size()==2) {
				getWebElementXpath_D(includeCurrentUnCheck).click();
				aJaxWait();
			}
			
			getWebElementXpath_D(checkbox).click();
			Thread.sleep(5000);
			
			//Default last 2 days chart data 
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy", today) + " 12:00 AM";
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.minusDays(2)) + " 12:00 AM";
			//verify start and end date from chart 
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
			
			new Select(getWebElementActionXpath_D("//*[@id='timelineLastxInterval']")).selectByVisibleText("Weeks");
			aJaxWait();
			//Provide 30 Weeks under 'Last X Weeks time line options
			getWebElementXpath_D(timeLinePath).click();
			getWebElementXpath_D(timeLinePath).clear();
			getWebElementXpath_D(timeLinePath).sendKeys("30");
			
			Assert.assertEquals(d.findElement(By.xpath(msgPath)).getText(),"Please select a value equal to or less than 26.");
			Assert.assertTrue((d.findElements(By.xpath(disabledCheckbox)).size() > 0));
			
			//Provide 2 Weeks under 'Last X Weeks time line options
			getWebElementXpath_D(timeLinePath).click();
			getWebElementXpath_D(timeLinePath).clear();
			getWebElementXpath_D(timeLinePath).sendKeys("2");
			
			//check the 'Include Current Week' 
			if(d.findElements(By.xpath(includeCurrentCheck)).size()==1) {
				getWebElementXpath_D(includeCurrentUnCheck).click();
				aJaxWait();
			}
			
			getWebElementXpath_D(checkbox).click();
			Thread.sleep(5000);
			
			//Default last 2 weeks chart data 
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy", today);
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.minusWeeks(1).with(TemporalAdjusters.previousOrSame(firstDayOfWeek)));
			//verify start and end date from chart 
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
			//uncheck the 'Include Current Week' 
			if(d.findElements(By.xpath(includeCurrentCheck)).size()==2) {
				getWebElementXpath_D(includeCurrentUnCheck).click();
				aJaxWait();
			}
			
			getWebElementXpath_D(checkbox).click();
			Thread.sleep(5000);
			
			//Default last 2 weeks chart data 
			//expectedEndChartDate = changeTheDateFormat("M/d/yyyy", today.minusDays(2));
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy", today.with(TemporalAdjusters.previous(lastDayOfWeek)));
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.minusWeeks(2).with(TemporalAdjusters.previousOrSame(firstDayOfWeek)));
			//verify start and end date from chart 
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
			
			new Select(getWebElementActionXpath_D("//*[@id='timelineLastxInterval']")).selectByVisibleText("Months");
			aJaxWait();
			//Provide 20 Months under 'Last X Months time line options
			getWebElementXpath_D(timeLinePath).click();
			getWebElementXpath_D(timeLinePath).clear();
			getWebElementXpath_D(timeLinePath).sendKeys("25");
			Thread.sleep(3000);
			Assert.assertEquals(d.findElement(By.xpath(msgPath)).getText(),"Please select a value equal to or less than 24.");
			Assert.assertTrue((d.findElements(By.xpath(disabledCheckbox)).size() > 0));
			
			//Provide 2 Months under 'Last X Months time line options
			getWebElementXpath_D(timeLinePath).click();
			getWebElementXpath_D(timeLinePath).clear();
			getWebElementXpath_D(timeLinePath).sendKeys("2");
			
			//check the 'Include Current Month' 
			if(d.findElements(By.xpath(includeCurrentCheck)).size()==1) {
				getWebElementXpath_D(includeCurrentUnCheck).click();
				aJaxWait();
			}
			
			getWebElementXpath_D(checkbox).click();
			Thread.sleep(5000);
			
			//Default last 2 Months chart data 
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy", today);
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.minusMonths(1).withDayOfMonth(1));
			//verify start and end date from chart 
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
			//uncheck the 'Include Current Month' 
			if(d.findElements(By.xpath(includeCurrentCheck)).size()==2) {
				getWebElementXpath_D(includeCurrentUnCheck).click();
				aJaxWait();
			}
			
			getWebElementXpath_D(checkbox).click();
			Thread.sleep(5000);
			
			//Default last 2 months chart data 
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy", today.minusMonths(1).withDayOfMonth(today.minusMonths(1).getMonth().length(today.isLeapYear())));
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.minusMonths(2).withDayOfMonth(1));
			//verify start and end date from chart 
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
			
			new Select(getWebElementActionXpath_D("//*[@id='timelineLastxInterval']")).selectByVisibleText("Years");
			aJaxWait();
			//Provide 5 Years under 'Last X Months time line options
			getWebElementXpath_D(timeLinePath).click();
			getWebElementXpath_D(timeLinePath).clear();
			getWebElementXpath_D(timeLinePath).sendKeys("5");
			
			Assert.assertEquals(d.findElement(By.xpath(msgPath)).getText(),"Please select a value equal to or less than 3.");
			Assert.assertTrue((d.findElements(By.xpath(disabledCheckbox)).size() > 0));
			
			//Provide 2 Years under 'Last X Years time line options
			getWebElementXpath_D(timeLinePath).click();
			getWebElementXpath_D(timeLinePath).clear();
			getWebElementXpath_D(timeLinePath).sendKeys("2");
			
			//check the 'Include Current Month' 
			if(d.findElements(By.xpath(includeCurrentCheck)).size()==1) {
				getWebElementXpath_D(includeCurrentUnCheck).click();
				aJaxWait();
			}
			
			getWebElementXpath_D(checkbox).click();
			Thread.sleep(5000);
			
			//Default last 2 Years chart data 
			expectedEndChartDate = changeTheDateFormat("MMM YYYY",today).toUpperCase();
			expectedStartChartDate = "JAN " +changeTheDateFormat("yyyy", today.minusYears(1));
			//verify start and end date from chart 
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
			//uncheck the 'Include Current Year' 
			if(d.findElements(By.xpath(includeCurrentCheck)).size()==2) {
				getWebElementXpath_D(includeCurrentUnCheck).click();
				aJaxWait();
			}
			
			getWebElementXpath_D(checkbox).click();
			Thread.sleep(5000);
			
			//Default last 2 Years chart data 
			expectedEndChartDate = changeTheDateFormat("yyyy", today.minusYears(1));
			expectedStartChartDate = changeTheDateFormat("yyyy", today.minusYears(2));
			//verify start and end date from chart 
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
		
			login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}