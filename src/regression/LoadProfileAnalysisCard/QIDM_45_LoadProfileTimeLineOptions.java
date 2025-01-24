package regression.LoadProfileAnalysisCard;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies all the timeline options available in Load Profile card. 
 * Selects each option one by one and loads chart for default (demand) measurement 
 * and verifies chart is loaded as expected by verifying the date range on the top
 */

public class QIDM_45_LoadProfileTimeLineOptions extends TestBase{
	LoginTC login = null;
	@Test
	public void loadProfileTimeLineOptions() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			Utility.selectSingleMeasurement("Electricity","Demand",standard);
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			refreshToLoadTheChart();
			
			//verify the legends to check if the page is loaded
			Assert.assertTrue(getWebElementXpath_D("//span[text()='PAMTest_Capriata/Saiwa  kW']").isDisplayed());
			
			//All variable declaration
			LocalDate today = LocalDate.now();
			String expectedStartChartDate,expectedEndChartDate,actualStartEndDateFromChart;
			
			// Time line option 'last 7 days' (defualt date range for LP card)
			//Calculating expected start and end date range for last 7 days
			expectedEndChartDate =changeTheDateFormat("M/d/yyyy" ,today.plusDays(1))+" 12:00 AM";
			expectedStartChartDate =changeTheDateFormat("M/d/yyyy",today.minusDays(6))+" 12:00 AM";
			
			//verify actual date range is as expected date range
			actualStartEndDateFromChart=getWebElementActionXpath_D("//*[contains(@class,'highcharts-title')]").getText();
			Assert.assertEquals(expectedStartChartDate+" - "+expectedEndChartDate, actualStartEndDateFromChart);
			
			//Timeline option 'Today'
			getWebElementActionXpath("TimeLineToday").click();
			aJaxWait();
			Utility.moveTheScrollToTheTop();
			
			//Calculating expected start and end date range for 'Today' (Current day)
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy",today.plusDays(0))+" 12:00 AM";
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy",today.plusDays(1))+" 12:00 AM";
			
			//verify actual date range is as expected date range
			actualStartEndDateFromChart=getWebElementActionXpath_D("//*[contains(@class,'highcharts-title')]").getText();
			Assert.assertEquals(expectedStartChartDate+" - "+expectedEndChartDate, actualStartEndDateFromChart);
			
			//Selected 'Yesterday' time line
			getWebElementActionXpath("TimeLineYesterday").click();
			aJaxWait();
			Utility.moveTheScrollToTheTop();
			//Calculating the date format
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy",today.minusDays(1))+" 12:00 AM";
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy",today.plusDays(0))+" 12:00 AM";

			//Verifying the start and end date from chart
			actualStartEndDateFromChart=getWebElementActionXpath_D("//*[contains(@class,'highcharts-title')]").getText();
			Assert.assertEquals(expectedStartChartDate+" - "+expectedEndChartDate, actualStartEndDateFromChart);
			
			//Selecting year to date
			getWebElementActionXpath("TimeLineYearToDate").click();
			aJaxWait();
			Utility.moveTheScrollToTheTop();
		
			//Calculating the date format
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.with(TemporalAdjusters.firstDayOfYear()))+" 12:00 AM";
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy",today.plusDays(1))+" 12:00 AM";

			//Verifying the start and end date from chart
			actualStartEndDateFromChart=getWebElementActionXpath_D("//*[contains(@class,'highcharts-title')]").getText();
			Assert.assertEquals(expectedStartChartDate+" - "+expectedEndChartDate, actualStartEndDateFromChart);
			
			//Selecting month to date
			getWebElementActionXpath("TimeLineMonthToDate").click();
			aJaxWait();
			Utility.moveTheScrollToTheTop();
			//Calculating the date format
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.with(TemporalAdjusters.firstDayOfMonth()))+" 12:00 AM";
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy",today.plusDays(1))+" 12:00 AM";

			//Verifying the start and end date from chart
			actualStartEndDateFromChart=getWebElementActionXpath_D("//*[contains(@class,'highcharts-title')]").getText();
			Assert.assertEquals(expectedStartChartDate+" - "+expectedEndChartDate, actualStartEndDateFromChart);
			
			//Adding start date
			getWebElementActionXpath("TimeLineStartingFrom").click();
			Thread.sleep(1000);
			String startingDateToAdd=changeTheDateFormat("M/d/yyyy",today.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()));
			getWebElementActionXpath_D("//span[normalize-space()='Starting from']").click();
			Thread.sleep(1000);
			getWebElementActionXpath_D("//small[normalize-space()='Start Date']/following-sibling::kendo-datepicker//child::input").click();
			Thread.sleep(1000);
			//pressing 2 times arrow to move to the month field
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementXpath_D("//small[normalize-space()='Start Date']/following-sibling::kendo-datepicker//child::input").sendKeys(startingDateToAdd);
			Thread.sleep(1000);
			
			getWebElementXpath("StartingFromDateApply").click();
			Thread.sleep(5000);
			Utility.moveTheScrollToTheTop();
			//Calculating the date format
			expectedStartChartDate = startingDateToAdd+" 12:00 AM";
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy",today.plusDays(1))+" 12:00 AM";

			//Verifying the start and end date from chart
			actualStartEndDateFromChart=getWebElementActionXpath_D("//*[contains(@class,'highcharts-title')]").getText();
			Assert.assertEquals(actualStartEndDateFromChart, expectedStartChartDate+" - "+expectedEndChartDate);
			
			//Added the Fixed date
			addFixedDateRange(testData.getProperty("FixedDateStartJan012022"), testData.getProperty("FixedDateEndJan312022"));
			aJaxWait();
			Utility.moveTheScrollToTheTop();
			//closing daylight saving pop up
			if(d.findElements(By.xpath("//div[@class='alert alert-warning alert-dismissable']//button[@class='close']/span[1]")).size()>0)
				getWebElementXpath_D("//div[@class='alert alert-warning alert-dismissable']//button[@class='close']/span[1]").click();
			expectedStartChartDate= testData.getProperty("FixedDateStartJan012022")+" 12:00 AM";
			expectedEndChartDate= "2/1/2022 12:00 AM";

			//Verifying the start and end date from chart
			actualStartEndDateFromChart=getWebElementActionXpath_D("//*[contains(@class,'highcharts-title')]").getText();
			Assert.assertEquals(actualStartEndDateFromChart,expectedStartChartDate+" - "+expectedEndChartDate);
			
			printLog("Verified Year To Starting from!!");
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}