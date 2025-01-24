package regression.LoadProfileAnalysisCard;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test case selects all comparison date ranges available in load profile card and 
 * verifies comparison date ranges are displayed as expected for each selected option.
 * 
 */

public class QIDM_46_LoadProfileComparisonDateRanges extends TestBase{
	LoginTC login = null;
	String legendName = "PAMTest_Capriata/Saiwa kW";
	String legendNamePreviousYear = legendName+" (Previous Year)";
	String legendNamePreviousMonth = legendName+" (Previous Month)";
	String legendNamePreviousWeek = legendName+" (Previous Week)";
	String legendNamePreviousDay = legendName+" (Previous Day)";
	String legendNameYearBeforeLast = legendName+" (Year Before Last)";
	String lineLegend = legendName+" (1/1/2022)";
	String loadProfileComparisonStartDTExpectedVal= "1/1/2022 12:15 AM,404.0~1/1/2022 12:30 AM,424.0~1/1/2022 12:45 AM,424.0~1/1/2022 01:00 AM,404.0";
	@Test
	public void loadProfileComparisonDateRanges() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");

			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			refreshToLoadTheChart();
			
			//verify the legends to check if the page is loaded
			Assert.assertTrue(getWebElementXpath_D("//span[text()='PAMTest_Capriata/Saiwa  kW']").isDisplayed());
			
			//Selected time line date as yesterday
			getWebElementXpath("TimeLineYesterday").click();
			Thread.sleep(4000);
			
			//Selected data tab(table)
			getWebElementXpath("DataTableTab").click();
			
			//Select previous year as Comparison Date Range
			comparisionDateRange("Previous Year");
			
			//Verify the chart legends
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), legendName);
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText(), legendNamePreviousYear);
			
			//calculating the date format
			LocalDate today = LocalDate.now();
			String previousYearSameDay=changeTheDateFormat("M/d/yyyy", today.minusDays(365));
			String yesterdaysDay=changeTheDateFormat("M/d/yyyy", today.minusDays(1));
			//Verify 1st 4 rows of table data
			String intervalArr[]= {" 12:15 AM"," 12:30 AM"," 12:45 AM"," 01:00 AM"};
			ArrayList<ArrayList<String>> holdPreviousYearTabledata=Utility.returnPamTableData(4) ;
			ArrayList<String> previousYearTableCellData=null;
			for(int i=0;i<holdPreviousYearTabledata.size();i++) {
				previousYearTableCellData=holdPreviousYearTabledata.get(i);
				Assert.assertEquals(previousYearTableCellData.get(0), yesterdaysDay+intervalArr[i]);
				Assert.assertNotNull(previousYearTableCellData.get(1));
				Assert.assertEquals(previousYearTableCellData.get(2), previousYearSameDay+intervalArr[i]);
				Assert.assertNotNull(previousYearTableCellData.get(3));
				previousYearTableCellData=null;
			}
			printLog("Verified for Previous year data!!");
						
			//Select Previous Month Comparison Date
			comparisionDateRange("Previous Month");
			Thread.sleep(5000);
			//Verify chart legends			
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), legendName);
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText(), legendNamePreviousMonth);
			//Verify 1st 4 rows of table data
			String previousMonthSameDay=changeTheDateFormat("M/d/yyyy", today.minusDays(29));
			ArrayList<ArrayList<String>> holdPreviousMonthTabledata=Utility.returnPamTableData(4) ;
			ArrayList<String> previousMonthableCellData=null;
			for(int i=0;i<holdPreviousMonthTabledata.size();i++) {
				previousMonthableCellData=holdPreviousMonthTabledata.get(i);
				Assert.assertEquals(previousMonthableCellData.get(0), yesterdaysDay+intervalArr[i]);
				Assert.assertNotNull(previousMonthableCellData.get(1));
				Assert.assertEquals(previousMonthableCellData.get(2), previousMonthSameDay+intervalArr[i]);
				Assert.assertNotNull(previousMonthableCellData.get(3));
				previousMonthableCellData=null;
			}
			printLog("Verified for Previous Month data!!");
			
			//Select Previous Week Comparison Date
			comparisionDateRange("Previous Week");
			Thread.sleep(5000);
			//Verify the chart legends
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), legendName);
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText(), legendNamePreviousWeek);
			//Verify 1st 4 rows of table data
			ArrayList<ArrayList<String>> holdPreviousWeekTabledata=Utility.returnPamTableData(4) ;
			String previousWeekSameDay=changeTheDateFormat("M/d/yyyy", today.minusDays(8));
			ArrayList<String> previousWeekTableCellData=null;
			for(int i=0;i<holdPreviousWeekTabledata.size();i++) {
				previousWeekTableCellData=holdPreviousWeekTabledata.get(i);
				Assert.assertEquals(previousWeekTableCellData.get(0), yesterdaysDay+intervalArr[i]);
				Assert.assertNotNull(previousWeekTableCellData.get(1));
				Assert.assertEquals(previousWeekTableCellData.get(2), previousWeekSameDay+intervalArr[i]);
				Assert.assertNotNull(previousWeekTableCellData.get(3));
				previousWeekTableCellData=null;
			}
			printLog("Verified for Previous Week data!!");
			
			//Select Previous Day Comparison Date
			comparisionDateRange("Previous Day");
			Thread.sleep(5000);
			//Verify chart legends
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), legendName);
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText(), legendNamePreviousDay);
			//Verify 1st 4 rows of table data
			ArrayList<ArrayList<String>> holdPreviousDayTabledata=Utility.returnPamTableData(4) ;
			String previousDay=changeTheDateFormat("M/d/yyyy", today.minusDays(2));
			ArrayList<String> previousDayTableCellData=null;
			for(int i=0;i<holdPreviousDayTabledata.size();i++) {
				previousDayTableCellData=holdPreviousDayTabledata.get(i);
				Assert.assertEquals(previousDayTableCellData.get(0), yesterdaysDay+intervalArr[i]);
				Assert.assertNotNull(previousDayTableCellData.get(1));
				Assert.assertEquals(previousDayTableCellData.get(2), previousDay+intervalArr[i]);
				Assert.assertNotNull(previousDayTableCellData.get(3));
				previousDayTableCellData=null;
			}
			printLog("Verified for Previous Day data!!");
			
			//Select Year Before Last Comparison Date
			comparisionDateRange("Year Before Last");
			Thread.sleep(5000);
			//Verify the chart legends
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), legendName);
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText(), legendNameYearBeforeLast);
			//Verify 1st 4 rows of table data
			String yearBeforeLast=changeTheDateFormat("M/d/yyyy", today.minusDays(729));
			ArrayList<ArrayList<String>> holdYearBeforeLastTabledata=Utility.returnPamTableData(4) ;
			ArrayList<String> yearBeforeLastTableCellData=null;
			for(int i=0;i<holdYearBeforeLastTabledata.size();i++) {
				yearBeforeLastTableCellData=holdYearBeforeLastTabledata.get(i);
				Assert.assertEquals(yearBeforeLastTableCellData.get(0), yesterdaysDay+intervalArr[i]);
				Assert.assertNotNull(yearBeforeLastTableCellData.get(1));
				Assert.assertEquals(yearBeforeLastTableCellData.get(2), yearBeforeLast+intervalArr[i]);
				Assert.assertNotNull(yearBeforeLastTableCellData.get(3));
				yearBeforeLastTableCellData=null;
			}
			printLog("Verified for Year Beforw Last Day data!!");
			
			//Select Select start date as Comparison Date
			/*comparisionDateRange("Select Start Date");
			Thread.sleep(2000);
			//fixedStartDate("01/01/2022","LoadProfileAnalysisCard");*/
			comparisionDateRange("Select Start Date");
			Thread.sleep(2000);
			getWebElementActionXpath_D("//div[@id='comparisonDateRange']//kendo-datepicker[contains(@class,'k-datepicker')]//child::input").click();
			Thread.sleep(1000);
			//pressing 2 times arrow to move to the month field
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementXpath_D("//div[@id='comparisonDateRange']//kendo-datepicker[contains(@class,'k-datepicker')]//child::input").sendKeys("01/01/2022");
			Thread.sleep(1000);
			getWebElementXpath_D("//*[@id='comparisonDateRange']//button/i[@class='fa fa-check']").click();
			Thread.sleep(8000);
			
			//Verify the chart legends
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), legendName);
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText(), lineLegend);
			
			 //Verify  1st 4 rows of table data
			ArrayList<ArrayList<String>> holdStartDateTabledata=Utility.returnPamTableData(4) ;
			List<String> holdExpectedData=Arrays.asList(loadProfileComparisonStartDTExpectedVal.split("~"));
			List<String> rowWiseExpectedData=null;
			List<String> rowWiseActualData=null;
			for(int i=0;i<holdStartDateTabledata.size();i++) {
				rowWiseExpectedData=Arrays.asList(holdExpectedData.get(i).split(","));
				rowWiseActualData=holdStartDateTabledata.get(i);
				Assert.assertEquals(rowWiseActualData.get(2), rowWiseExpectedData.get(0));
				Assert.assertEquals(rowWiseActualData.get(3), rowWiseExpectedData.get(1));
				rowWiseExpectedData=null;
				rowWiseActualData=null;
			}
			printLog("Verified for Select start date !!");
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}