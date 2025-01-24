package regression.ScatterPlotAnalysisCard;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies all the timeline options available in Scatter Plot Analysis. 
 * Selects each option one by one and loads chart  and verifies chart is loaded as expected by verifying the date range on the top.
 */
public class QIDM_71_ScatterPlotAnalysisTimeLineOptions extends TestBase{
	LoginTC login = null;
	@Test
	public void scatterPlotAnalysisTimeLineOptions() throws Throwable {
		try {
			String chartStartDate = "//span[@id='dateRangeStart']";
			String chartEndDate = "//span[@id='dateRangeEnd']";
			
			//Login into RA application with Internal user
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to PAM page
			goToAnalysisPage();
			
			//Navigate to Scatter Plot Analysis Card page
			goToPAMCard("ScatterPlotCard");
			printLog("Navigated to Scatter plot Card page");
			
			Assert.assertEquals(getWebElementXpath_D("//div[@class='widget-header']/h3/span").getText(), "New Scatter Plot Analysis");
			printLog("Verified the card name");
			
			//Selecting Energy and temperature measurements for site
			Utility.selectMultipleMeasurements("Electricity#Energy~standard|Weather#Temperature~standard");
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			getWebElementXpath("PAMTest_CapriataSaiwa_Weather").click();
			printLog("Selected Electricity Energy & Weather Tempearture with PAMTest_Capriata/Saiwa");
			
			refreshToLoadTheChart();
			Thread.sleep(5000);
			Utility.moveTheScrollToTheTop();
			//Verify the legend and start & end date from chart
			String legend = getWebElementXpath("LineLegendOne").getText();
			Assert.assertEquals(legend, "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C");
			
			legend = getWebElementXpath("ScatterLegendTwo").getText();
			Assert.assertEquals(legend, "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C");		
			printLog("Verified the Scatter Plot Card Legends");
			Thread.sleep(1000);
			
			//Verify start and end date from chart
			LocalDate today = LocalDate.now();
			String expectedStartChartDate,expectedEndChartDate,actualStartChartDate,actualEndChartDate;
			
			//Default last 30 days chart data 
			expectedEndChartDate =changeTheDateFormat("M/d/yyyy",today);
			expectedStartChartDate =changeTheDateFormat("M/d/yyyy",today.minusDays(29));
			Assert.assertEquals(getWebElementXpath_D(chartStartDate).getText(), expectedStartChartDate);
			Assert.assertEquals(getWebElementXpath_D(chartEndDate).getText(), expectedEndChartDate);			
			printLog("Verified the start & end date for Scatter Plot Card.");
			
			//First 4 rows of table data verification
			getWebElementActionXpath("DataTableTab").click();
			ArrayList<ArrayList<String>> holdPamTableData=Utility.returnPamTableData(4);
			ArrayList<String> holdRowWiseData=null;
			for(int i=0;i<holdPamTableData.size();i++) {
				holdRowWiseData=holdPamTableData.get(i);
				Assert.assertEquals(holdRowWiseData.get(0), changeTheDateFormat("M/d/yyyy",today.minusDays((29-i))));
			}
			printLog("Verified first 4 rows of table date for default 30 days");
			
			Utility.moveTheScrollToTheTop();
			//Selected today as time line option
			getWebElementActionXpath("TimeLineToday").click();
			Thread.sleep(3000);
			Utility.moveTheScrollToTheTop();
			//Calculating the date format
			expectedStartChartDate=changeTheDateFormat("M/d/yyyy",today)+" 12:00 AM";
			expectedEndChartDate =changeTheDateFormat("M/d/yyyy",today.plusDays(1))+" 12:00 AM";
			//verify start and end date from chart
			actualStartChartDate=getWebElementXpath_D(chartStartDate).getText();
			actualEndChartDate=getWebElementXpath_D(chartEndDate).getText();
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);			
			//Verify 1st 4 date intervals from table
			getWebElementActionXpath("DataTableTab").click();
			holdPamTableData=Utility.returnPamTableData(4);
			String pamTableDate= changeTheDateFormat("M/d/yyyy",today);
			Assert.assertEquals(holdPamTableData.get(0).get(0),pamTableDate+" 01:00 AM");
			Assert.assertEquals(holdPamTableData.get(1).get(0), pamTableDate+" 02:00 AM");
			Assert.assertEquals(holdPamTableData.get(2).get(0), pamTableDate+" 03:00 AM");
			Assert.assertEquals(holdPamTableData.get(3).get(0), pamTableDate+" 04:00 AM");
			printLog("Verified first 4 rows of table date for todays");
			
			//Selected yesterday as time line
			getWebElementActionXpath("TimeLineYesterday").click();
			Thread.sleep(3000);
			Utility.moveTheScrollToTheTop();
			//Calculating the date format
			expectedStartChartDate =changeTheDateFormat("M/d/yyyy",today.minusDays(1))+" 12:00 AM";
			expectedEndChartDate =changeTheDateFormat("M/d/yyyy",today)+" 12:00 AM";
			//verify start and end date from chart
			actualStartChartDate=getWebElementXpath_D(chartStartDate).getText();
			actualEndChartDate=getWebElementXpath_D(chartEndDate).getText();
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
			//Verify 1st 4 date intervals from table
			getWebElementActionXpath("DataTableTab").click();
			holdPamTableData=Utility.returnPamTableData(4);
			pamTableDate= changeTheDateFormat("M/d/yyyy",today.minusDays(1));
			Assert.assertEquals(holdPamTableData.get(0).get(0),pamTableDate+" 01:00 AM");
			Assert.assertEquals(holdPamTableData.get(1).get(0), pamTableDate+" 02:00 AM");
			Assert.assertEquals(holdPamTableData.get(2).get(0), pamTableDate+" 03:00 AM");
			Assert.assertEquals(holdPamTableData.get(3).get(0), pamTableDate+" 04:00 AM");
			printLog("Verifiedtable date for yesterdy time line !!");

			//Select year to date as time line
			getWebElementActionXpath("TimeLineYearToDate").click();
			Thread.sleep(15000);//added more time to get year data
			Utility.moveTheScrollToTheTop();						
			//Calculating the date format
			expectedStartChartDate = changeTheDateFormat("MMM yyyy", today.with(TemporalAdjusters.firstDayOfYear())).toUpperCase();
			expectedEndChartDate = changeTheDateFormat("MMM yyyy",today).toUpperCase();
			//verify start and end date from chart
			actualStartChartDate=getWebElementXpath_D(chartStartDate).getText();
			actualEndChartDate=getWebElementXpath_D(chartEndDate).getText();
			
			if(expectedEndChartDate.contains("JAN")) {
				//Calculating the date format
				expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.with(TemporalAdjusters.firstDayOfMonth()));
				expectedEndChartDate = changeTheDateFormat("M/d/yyyy",today);
				int days = Integer.parseInt(expectedEndChartDate.substring(expectedEndChartDate.indexOf("/")+1, expectedEndChartDate.lastIndexOf("/")));
				//verify start and end date from chart
				
				if(days<=6) {
					expectedStartChartDate=changeTheDateFormat("M/d/yyyy",today.with(TemporalAdjusters.firstDayOfMonth()))+" 12:00 AM";
					expectedEndChartDate =changeTheDateFormat("M/d/yyyy",today.plusDays(1))+" 12:00 AM";
					Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
					Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
					
					//Verify 1st 4 rows of table data
					getWebElementActionXpath("DataTableTab").click();
					holdPamTableData=Utility.returnPamTableData(4);
					holdRowWiseData=null;
					pamTableDate= changeTheDateFormat("M/d/yyyy",today.with(TemporalAdjusters.firstDayOfMonth()));
					Assert.assertEquals(holdPamTableData.get(0).get(0),pamTableDate+" 01:00 AM");
					Assert.assertEquals(holdPamTableData.get(1).get(0), pamTableDate+" 02:00 AM");
					Assert.assertEquals(holdPamTableData.get(2).get(0), pamTableDate+" 03:00 AM");
					Assert.assertEquals(holdPamTableData.get(3).get(0), pamTableDate+" 04:00 AM");
					printLog("Verified table date for Month to Date!!");
				}else {
					Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
					Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
					
					//Verify 1st 4 rows of table data
					getWebElementActionXpath("DataTableTab").click();
					holdPamTableData=Utility.returnPamTableData(4);
					holdRowWiseData=null;
					for(int i=0;i<holdPamTableData.size();i++) {
						holdRowWiseData=holdPamTableData.get(i);
						Assert.assertEquals(holdRowWiseData.get(0), changeTheDateFormat("M/d/yyyy",today.with(TemporalAdjusters.firstDayOfMonth()).plusDays(i)));
					}
					printLog("Verified table date for Month to Date!!");
				}
				
			} else {
				Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
				Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
				// Verify 1st 4 rows of table data
				getWebElementActionXpath("DataTableTab").click();
				holdPamTableData = Utility.returnPamTableData(4);
				holdRowWiseData = null;
				for (int i = 0; i < holdPamTableData.size(); i++) {
					holdRowWiseData = holdPamTableData.get(i);
					Assert.assertEquals(holdRowWiseData.get(0), changeTheDateFormat("MMM yyyy", today.with(TemporalAdjusters.firstDayOfYear()).plusMonths(i)));
				}
			}
			printLog("Verified table date for Year To Date !!");
			
			//Select month to date as time line
			getWebElementActionXpath("TimeLineMonthToDate").click();
			aJaxWait();
			
			//Calculating the date format
			expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.with(TemporalAdjusters.firstDayOfMonth()));
			expectedEndChartDate = changeTheDateFormat("M/d/yyyy",today);
			int days = Integer.parseInt(expectedEndChartDate.substring(expectedEndChartDate.indexOf("/")+1, expectedEndChartDate.lastIndexOf("/")));
			//verify start and end date from chart
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			if(days<=6) {
				expectedStartChartDate=changeTheDateFormat("M/d/yyyy",today.with(TemporalAdjusters.firstDayOfMonth()))+" 12:00 AM";
				expectedEndChartDate =changeTheDateFormat("M/d/yyyy",today.plusDays(1))+" 12:00 AM";
				Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
				Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
				//Verify 1st 4 rows of table data
				getWebElementActionXpath("DataTableTab").click();
				holdPamTableData=Utility.returnPamTableData(4);
				pamTableDate= changeTheDateFormat("M/d/yyyy",today.with(TemporalAdjusters.firstDayOfMonth()));
				Assert.assertEquals(holdPamTableData.get(0).get(0),pamTableDate+" 01:00 AM");
				Assert.assertEquals(holdPamTableData.get(1).get(0), pamTableDate+" 02:00 AM");
				Assert.assertEquals(holdPamTableData.get(2).get(0), pamTableDate+" 03:00 AM");
				Assert.assertEquals(holdPamTableData.get(3).get(0), pamTableDate+" 04:00 AM");
			}else {
				Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
				Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
				//Verify 1st 4 rows of table data
				getWebElementActionXpath("DataTableTab").click();
				holdPamTableData=Utility.returnPamTableData(4);
				holdRowWiseData=null;
				for(int i=0;i<holdPamTableData.size();i++) {
					holdRowWiseData=holdPamTableData.get(i);
					Assert.assertEquals(holdRowWiseData.get(0), changeTheDateFormat("M/d/yyyy",today.with(TemporalAdjusters.firstDayOfMonth()).plusDays(i)));
				}
			}
			
			printLog("Verified table date for Month to Date!!");
						
			//Select starting from as time line
			//Select 'Starting from' and provide first day of month and Verify the StartDate and EndDate of the Bar chart
			getWebElementActionXpath("TimeLineStartingFrom").click();
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
			
			getWebElementActionXpath("StartingFromDateApply").click();
			aJaxWait();
			Utility.moveTheScrollToTheTop();
			
			String timeIntervalBy = getWebElementXpath("PAMIntervalDropDown").getAttribute("value");
			if (!timeIntervalBy.equalsIgnoreCase("Days")) {
				//Calculating the date format
				expectedStartChartDate = startingDateToAdd+" 12:00 AM";
				expectedEndChartDate = changeTheDateFormat("M/d/yyyy",today.plusDays(1))+" 12:00 AM";
			}else {
				//Calculating the date format
				expectedStartChartDate = startingDateToAdd;
				expectedEndChartDate = changeTheDateFormat("M/d/yyyy",today);
			}
			
			//verify start and end date from chart
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
			
			//Verify 1st 4 rows of table data
			getWebElementActionXpath("DataTableTab").click();
			holdPamTableData=Utility.returnPamTableData(4);
			holdRowWiseData=null;
			for(int i=0;i<holdPamTableData.size();i++) {
				holdRowWiseData=holdPamTableData.get(i);
				//System.out.println(changeTheDateFormat("M/d/yyyy",today.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).plusDays(i)));
				if (timeIntervalBy.equalsIgnoreCase("Hours")) {
					Assert.assertEquals(holdRowWiseData.get(0),changeTheDateFormat("M/d/yyyy",today.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()))+" 0"+(i+1)+":00 AM");
				}else {
					Assert.assertEquals(holdRowWiseData.get(0),changeTheDateFormat("M/d/yyyy",today.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).plusDays(i)));
				}
			}
			printLog("Verified table dates for date starting from!");
			
			//Added fixed date range
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			Thread.sleep(5000);
			Utility.moveTheScrollToTheTop();
			expectedStartChartDate= testData.getProperty("FixedDateStartJan012023");
			expectedEndChartDate= testData.getProperty("FixedDateEndJan312023");
			//verify start and end date from chart
			actualStartChartDate=getWebElementXpath_D(chartStartDate).getText();
			actualEndChartDate=getWebElementXpath_D(chartEndDate).getText();
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);			
			//Verify 1st 4 interval date from table
			getWebElementActionXpath("DataTableTab").click();
			holdPamTableData=Utility.returnPamTableData(4);
			String dates[]="1/1/2023,1/2/2023,1/3/2023,1/4/2023".split(",");
			holdRowWiseData=null;
			for(int i=0;i<holdPamTableData.size();i++) {
				holdRowWiseData=holdPamTableData.get(i);
				Assert.assertEquals(holdRowWiseData.get(0),dates[i]);
			}
			printLog("Verified table date for fixed date!");
			
			login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}