package regression.AllCardsCommonTCs;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies various common analysis reports are loaded as expected on PAM cards or not. 
 * For all the reports specified in steps, verify dynamic date ranges and measurements are displayed as expected
 */
public class QIDM_249_CommonAnalysis extends TestBase {
	LoginTC login = null;
	HashMap<String, Throwable> failedReasonForMeasurement = null;
	Map<String, String> passedReason = null;

	@Test
	public void commonAnalysis() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			loginWithExternalUser("psrtestuser");
			goToAnalysisPage();

			Utility.moveTheScrollToTheTop();
			// Click on 'Common Analyses' in PAM page
			getWebElementActionXpath_D("//a[normalize-space()='Common Analyses']").click();
			Thread.sleep(6000);

			String[] reportType = { "IntervalReport", "DailyTotals", "CalendarView", "WeekdayWeekend", "AverageProfileComparison", "MultiSite", "TemperatureWithDegreeDays" };

			failedReasonForMeasurement = new HashMap<String, Throwable>();
			passedReason = new HashMap<String, String>();
			for (int i = 0; i < reportType.length; i++) {
				switch (reportType[i]) {
				case "IntervalReport":
					try {
						intervalReportVerification();
						passedReason.put("IntervalReport", "Passed");
					} catch (Throwable e) {
						failedReasonForMeasurement.put("IntervalReport", e);
					}
					break;
				case "DailyTotals":
					try {
						dailyTotalsVerification();
						passedReason.put("DailyTotals", "Passed");
					} catch (Throwable e) {
						failedReasonForMeasurement.put("DailyTotals", e);
					}
					break;
				case "CalendarView":
					try {
						calendarViewVerification();
						passedReason.put("CalendarView", "Passed");
					} catch (Throwable e) {
						failedReasonForMeasurement.put("CalendarView", e);
					}
					break;
				case "WeekdayWeekend":
					try {
						WeekdayWeekendVerification();
						passedReason.put("WeekdayWeekend", "Passed");
					} catch (Throwable e) {
						failedReasonForMeasurement.put("WeekdayWeekend", e);
					}
					break;
				case "AverageProfileComparison":
					try {
						AverageProfileComparisonVerification();
						passedReason.put("AverageProfileComparison", "Passed");
					} catch (Throwable e) {
						failedReasonForMeasurement.put("AverageProfileComparison", e);
					}
					break;
				case "MultiSite":
					try {
						multiSiteVerification();
						passedReason.put("MultiSite", "Passed");
					} catch (Throwable e) {
						failedReasonForMeasurement.put("MultiSite", e);
					}
					break;
				case "TemperatureWithDegreeDays":
					try {
						temperatureWithDegreeDaysVerification();
						passedReason.put("TemperatureWithDegreeDays", "Passed");
					} catch (Throwable e) {
						failedReasonForMeasurement.put("TemperatureWithDegreeDays", e);
					}
					break;
				default:
					printLog("No measurements are selected");

				}

			}

			for (String key : passedReason.keySet()) {
				printLog("The " + key + " was Passed");
			}

			for (String key : failedReasonForMeasurement.keySet()) {
				printLog("The " + key + " was Failed");
			}

			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void intervalReportVerification() throws Throwable {
		try {
			ArrayList<String> newTb = new ArrayList<String>(d.getWindowHandles());
			if (newTb.size() > 1) {
				for (int i = newTb.size(); i > 0; i--) {
					d.switchTo().window(newTb.get(i));
					Thread.sleep(2000);
					d.close();
				}
			}
			d.switchTo().window(newTb.get(0));
			getWebElementActionXpath_D("//a[normalize-space()='Interval Report']").click();
			Thread.sleep(6000);

			newTb = new ArrayList<String>(d.getWindowHandles());
			// User has switched to new tab PAM page
			d.switchTo().window(newTb.get(1));
			aJaxWait();
			Thread.sleep(10000);
			// Verify the widget title as 'Interval Report'
			Assert.assertEquals(getWebElementXpath("WidgetHeader").getText(), "Interval Report");
			printLog("Verified the card title as 'Interval Report'");
			LocalDate today = LocalDate.now();
			String expectedStartChartDate, expectedEndChartDate;

			// Time line option 'last 7 days' (default date range for LP card) Calculating expected start and end date range for last 7 days
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy", today) + " 12:00 AM";
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.minusDays(7)) + " 12:00 AM";
			String expectedStartAndEndDate = expectedStartChartDate + " - " + expectedEndChartDate;
			// Chart actual start and end date
			String actualStartEndDateFromChart = getWebElementActionXpath_D("//*[contains(@class,'highcharts-title')]").getText();

			// verify actual date range is as expected date range
			Assert.assertEquals(actualStartEndDateFromChart, expectedStartAndEndDate);
			printLog("Verified the default Date Range of the card");
			// Verify the legends
			Assert.assertEquals(getWebElementActionXpath("LineLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			printLog("intervalReportVerification...Passed");
		} catch (Throwable t) {
			t.printStackTrace();
			printLog("intervalReportVerification...Failed");
			throw t;
		}
	}

	private void dailyTotalsVerification() throws Throwable {
		try {
			ArrayList<String> newTb = new ArrayList<String>(d.getWindowHandles());
			if (newTb.size() > 1) {
				for (int i = (newTb.size() - 1); i > 0; i--) {
					d.switchTo().window(newTb.get(i));
					Thread.sleep(2000);
					d.close();
				}
			}
			d.switchTo().window(newTb.get(0));

			getWebElementActionXpath_D("//a[normalize-space()='Daily Totals']").click();
			Thread.sleep(6000);

			newTb = new ArrayList<String>(d.getWindowHandles());
			// User has switched to new tab PAM page
			d.switchTo().window(newTb.get(1));
			aJaxWait();
			Thread.sleep(10000);
			// Verify the widget title as 'Daily Totals'
			Assert.assertEquals(getWebElementXpath("WidgetHeader").getText(), "Daily Totals");
			printLog("Verified the card title as 'Daily Totals'");

			LocalDate today = LocalDate.now();
			String expectedStartChartDate, expectedEndChartDate, actualStartChartDate, actualEndChartDate;

			// Default last 30 days chart data exclude current date
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy", today.minusDays(1));
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.minusDays(30));
			// verify start and end date from chart
			actualStartChartDate = getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate = getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
			printLog("Verified the default Date Range of the card");

			// Verify the legends
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText().replaceAll("\\n", " "), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			printLog("intervalReportVerification...Passed");
		} catch (Throwable t) {
			t.printStackTrace();
			printLog("intervalReportVerification...Failed");
			throw t;
		}
	}

	private void calendarViewVerification() throws Throwable {
		try {
			ArrayList<String> newTb = new ArrayList<String>(d.getWindowHandles());
			if (newTb.size() > 1) {
				for (int i = (newTb.size() - 1); i > 0; i--) {
					d.switchTo().window(newTb.get(i));
					Thread.sleep(2000);
					d.close();
				}
			}
			d.switchTo().window(newTb.get(0));

			getWebElementActionXpath_D("//a[normalize-space()='Calendar View']").click();
			Thread.sleep(6000);

			newTb = new ArrayList<String>(d.getWindowHandles());
			// User has switched to new tab PAM page
			d.switchTo().window(newTb.get(1));
			aJaxWait();
			Thread.sleep(10000);
			// Verify the widget title as 'Calendar View'
			Assert.assertEquals(getWebElementXpath("WidgetHeader").getText(), "Calendar View");
			printLog("Verified the card title as 'Calendar View'");

			LocalDate today = LocalDate.now();
			// Verify by default selected option should be 'Last month'
			Assert.assertEquals(getWebElementXpath("SelectFromOptions").getText(), "Last month");
			Assert.assertEquals(getWebElementXpath("ClpChartMonth").getText(), changeTheDateFormat("MMMM yyyy", today.minusMonths(1)));
			printLog("Verified Last month is selected in time line option");

			// Verify the legend
			Assert.assertEquals(d.findElement(By.xpath("//span[@class='legend-text--calendar']/span")).getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			printLog("calendarViewVerification...Passed");
		} catch (Throwable t) {
			t.printStackTrace();
			printLog("calendarViewVerification...Failed");
			throw t;
		}
	}

	private void WeekdayWeekendVerification() throws Throwable {
		try {
			ArrayList<String> newTb = new ArrayList<String>(d.getWindowHandles());
			if (newTb.size() > 1) {
				for (int i = (newTb.size() - 1); i > 0; i--) {
					d.switchTo().window(newTb.get(i));
					Thread.sleep(2000);
					d.close();
				}
			}
			d.switchTo().window(newTb.get(0));

			getWebElementActionXpath_D("//a[normalize-space()='Weekday - Weekend']").click();
			Thread.sleep(6000);

			newTb = new ArrayList<String>(d.getWindowHandles());
			// User has switched to new tab PAM page
			d.switchTo().window(newTb.get(1));
			aJaxWait();
			Thread.sleep(10000);
			// Verify the widget title as 'Weekday - Weekend'
			Assert.assertEquals(getWebElementXpath("WidgetHeader").getText(), "Weekday - Weekend");
			printLog("Verified the card title as 'Weekday - Weekend'");
			LocalDate today = LocalDate.now();
			String expectedStartChartDate, expectedEndChartDate;

			// Time line option 'last 14 days' Calculating expected start and end date range for last 14 days exclude current date
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy", today.minusDays(1));
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.minusDays(14));
			String expectedStartAndEndDate = expectedStartChartDate + " - " + expectedEndChartDate;
			// Chart actual start and end date
			String actualStartEndDateFromChart = getWebElementActionXpath_D("//*[contains(@class,'highcharts-title')]").getText();

			// verify actual date range is as expected date range
			Assert.assertEquals(actualStartEndDateFromChart, expectedStartAndEndDate);
			printLog("Verified the default Date Range of the card");
			// Verify the legends
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText(), "PAMTest_Capriata/Saiwa kWh (Weekday)");
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendTwo").getText(), "PAMTest_Capriata/Saiwa kWh (Weekend)");
			printLog("WeekdayWeekendReportVerification...Passed");
		} catch (Throwable t) {
			t.printStackTrace();
			printLog("WeekdayWeekendReportVerification...Failed");
			throw t;
		}
	}

	private void AverageProfileComparisonVerification() throws Throwable {
		try {
			ArrayList<String> newTb = new ArrayList<String>(d.getWindowHandles());
			if (newTb.size() > 1) {
				for (int i = (newTb.size() - 1); i > 0; i--) {
					d.switchTo().window(newTb.get(i));
					Thread.sleep(2000);
					d.close();
				}
			}
			d.switchTo().window(newTb.get(0));

			getWebElementActionXpath_D("//a[normalize-space()='Average Profile Comparison']").click();
			Thread.sleep(6000);

			newTb = new ArrayList<String>(d.getWindowHandles());
			// User has switched to new tab PAM page
			d.switchTo().window(newTb.get(1));
			aJaxWait();
			Thread.sleep(10000);
			// Verify the widget title as 'Average Profile Comparison'
			Assert.assertEquals(getWebElementXpath("WidgetHeader").getText(), "Average Profile Comparison");
			printLog("Verified the card title as 'Average Profile Comparison'");
			LocalDate today = LocalDate.now();
			String expectedStartChartDate, expectedEndChartDate;

			// Time line option 'last 7 days' (default date range for LP card) Calculating expected start and end date range for last 7 days exclude current date
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy", today) + " 12:00 AM";
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.minusDays(7)) + " 12:00 AM";
			String expectedStartAndEndDate = expectedStartChartDate + " - " + expectedEndChartDate;
			// Chart actual start and end date
			String actualStartEndDateFromChart = getWebElementActionXpath_D("//*[contains(@class,'highcharts-title')]").getText();

			// verify actual date range is as expected date range
			Assert.assertEquals(actualStartEndDateFromChart, expectedStartAndEndDate);
			printLog("Verified the default Date Range of the card");
			// Verify the legends
			Assert.assertEquals(getWebElementActionXpath("LineLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			Assert.assertEquals(getWebElementActionXpath("LineLegendTwo").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh") + " - Average");
			printLog("AverageProfileComparisonVerification...Passed");
		} catch (Throwable t) {
			t.printStackTrace();
			printLog("AverageProfileComparisonVerification...Failed");
			throw t;
		}
	}

	private void multiSiteVerification() throws Throwable {
		try {
			ArrayList<String> newTb = new ArrayList<String>(d.getWindowHandles());
			if (newTb.size() > 1) {
				for (int i = (newTb.size() - 1); i > 0; i--) {
					d.switchTo().window(newTb.get(i));
					Thread.sleep(2000);
					d.close();
				}
			}
			d.switchTo().window(newTb.get(0));

			getWebElementActionXpath_D("//a[normalize-space()='Multi Site']").click();
			Thread.sleep(6000);

			newTb = new ArrayList<String>(d.getWindowHandles());
			// User has switched to new tab PAM page
			d.switchTo().window(newTb.get(1));
			aJaxWait();
			Thread.sleep(10000);
			// Verify the widget title as 'Multi Site'
			Assert.assertEquals(getWebElementXpath("WidgetHeader").getText(), "Multi Site");
			printLog("Verified the card title as 'Multi Site'");
			LocalDate today = LocalDate.now();
			String expectedStartChartDate, expectedEndChartDate, actualStartChartDate, actualEndChartDate;
			// Time line option 'last 1 month' Calculating expected start and end date range for last month
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy", today.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()));
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()));

			actualStartChartDate = getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate = getWebElementXpath("ChartEndDate").getText();

			// verify actual date range is as expected date range
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
			printLog("Verified the default Date Range of the card");
			// Verify the legends
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText(), "kWh");

			// Verified the sites on the chart
			String[] sitesList = getWebElementXpath("ChartXaxisLabels").getText().split("\\n");
			Assert.assertTrue(sitesList.length > 1);
			printLog("multiSiteVerification...Passed");
		} catch (Throwable t) {
			t.printStackTrace();
			printLog("multiSiteVerification...Failed");
			throw t;
		}
	}

	private void temperatureWithDegreeDaysVerification() throws Throwable {
		try {
			ArrayList<String> newTb = new ArrayList<String>(d.getWindowHandles());
			if (newTb.size() > 1) {
				for (int i = (newTb.size() - 1); i > 0; i--) {
					d.switchTo().window(newTb.get(i));
					Thread.sleep(2000);
					d.close();
				}
			}
			d.switchTo().window(newTb.get(0));

			getWebElementActionXpath_D("//a[normalize-space()='Temperature With Degree Days']").click();
			Thread.sleep(10000);

			newTb = new ArrayList<String>(d.getWindowHandles());
			// User has switched to new tab PAM page
			d.switchTo().window(newTb.get(1));
			aJaxWait();
			Thread.sleep(10000);
			// Verify the widget title as 'Temperature with Degree Days'
			Assert.assertEquals(getWebElementXpath("WidgetHeader").getText(), "Temperature with Degree Days");
			printLog("Verified the card title as 'Temperature with Degree Days'");
			LocalDate today = LocalDate.now();
			String expectedStartChartDate, expectedEndChartDate;

			// Time line option 'last 12 Months' Calculating expected start and end date range for last 12 months include Current month
			expectedEndChartDate = changeTheDateFormat("MMM YYYY", today);
			expectedStartChartDate = changeTheDateFormat("MMM YYYY", today.minusMonths(11));
			String expectedStartAndEndDate = expectedStartChartDate.toUpperCase() + " - " + expectedEndChartDate.toUpperCase();
			// Chart actual start and end date
			String actualStartEndDateFromChart = getWebElementActionXpath_D("//*[contains(@class,'highcharts-title')]").getText();

			// verify actual date range is as expected date range
			Assert.assertEquals(actualStartEndDateFromChart, expectedStartAndEndDate);
			printLog("Verified the default Date Range of the card");
			// Verify the legends
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText().replaceAll("\\n", " "), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			Assert.assertEquals(getWebElementActionXpath("LineLegendTwo").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa °C");
			Assert.assertEquals(getWebElementActionXpath("LineLegendThree").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa dd°C");
			Assert.assertEquals(getWebElementActionXpath("LineLegendFour").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa dd°C");
			printLog("temperatureWithDegreeDaysVerification...Passed");
		} catch (Throwable t) {
			t.printStackTrace();
			printLog("temperatureWithDegreeDaysVerification...Failed");
			throw t;
		}
	}

	private void loginWithExternalUser(String externalUser) throws Throwable {
		try {
			printLog("Execution URL is " + Dto.getURl());
			System.out.println(Dto.getURl());
			// Add the url into browser
			d.get(Dto.getURl());
			waitForPageLoad();
			Thread.sleep(5000);

			// Added the user id
			explicitWait_CSS("#raUserName");
			getWebElementCSS_D("#raUserName").clear();
			getWebElementCSS_D("#raUserName").sendKeys(Dto.getInternalusername());
			Thread.sleep(1000);
			// Click on login button
			d.findElement(By.cssSelector("#btnLogin")).click();
			Thread.sleep(3000);

			// Added the pwd
			explicitWait_CSS("#raPassword");
			getWebElementCSS_D("#raPassword").clear();
			getWebElementCSS_D("#raPassword").sendKeys(Dto.getInternalPassword());

			// Click on login button
			d.findElement(By.cssSelector("#btnLogin")).click();
			waitForPageLoad();
			Thread.sleep(15000);
			goToAnalysisPage();

			getWebElementXpath_D("//input[@id='ctl00_ContentPlaceHolder1_txtUsername']").sendKeys(externalUser);
			getWebElementXpath_D("//span[@id='ctl00_ContentPlaceHolder1_lblUserSearch']").click();
			Thread.sleep(5000);
			explicitWait_Xpath("//a[contains(text(),'" + externalUser + "')]").click();
			explicitWait_Xpath("//*[@id='ctl00_NavMenu']/ul/li[3]/a/span");
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}

	}
}