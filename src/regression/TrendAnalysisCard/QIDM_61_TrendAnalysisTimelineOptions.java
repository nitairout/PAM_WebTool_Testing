package regression.TrendAnalysisCard;
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
 * This test verifies all the time line options available in Trend card. Selects each option one by one and loads chart for default 
 * Energy measurement and verifies that chart is loaded as expected by verifying the date range on the top.
*/

public class QIDM_61_TrendAnalysisTimelineOptions extends TestBase{
	LoginTC login = null;
	@Test
	public void trendAnalysisTimelineOptions() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			Utility.selectSingleMeasurement(testData.getProperty("ElectricityCommodity"),testData.getProperty("EnergyMeasurement"),standard);
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			refreshToLoadTheChart();
			
			//All variable declaration
			LocalDate today = LocalDate.now();
			String expectedStartChartDate,expectedEndChartDate,actualStartChartDate,actualEndChartDate;
			
			//Default last 30 days chart data 
			expectedEndChartDate =changeTheDateFormat("M/d/yyyy",today);
			expectedStartChartDate =changeTheDateFormat("M/d/yyyy",today.minusDays(29));
			//verify start and end date from chart 
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
			
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
			String pamTableDate="";
			//Selected today as time line option
			getWebElementActionXpath("TimeLineToday").click();
			aJaxWait();
			
			//Calculating the date format
			expectedStartChartDate=changeTheDateFormat("M/d/yyyy",today)+" 12:00 AM";
			expectedEndChartDate =changeTheDateFormat("M/d/yyyy",today.plusDays(1))+" 12:00 AM";
			//verify start and end date from chart
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
			
			//Verify 1st 4 date intervals from table
			getWebElementActionXpath("DataTableTab").click();
			holdPamTableData=Utility.returnPamTableData(4);
			pamTableDate= changeTheDateFormat("M/d/yyyy",today);
			Assert.assertEquals(holdPamTableData.get(0).get(0),pamTableDate+" 01:00 AM");
			Assert.assertEquals(holdPamTableData.get(1).get(0), pamTableDate+" 02:00 AM");
			Assert.assertEquals(holdPamTableData.get(2).get(0), pamTableDate+" 03:00 AM");
			Assert.assertEquals(holdPamTableData.get(3).get(0), pamTableDate+" 04:00 AM");
			printLog("Verified first 4 rows of table date for todays");
			
			Utility.moveTheScrollToTheTop();
			//Selected yesterday as time line
			getWebElementActionXpath("TimeLineYesterday").click();
			aJaxWait();
			//Calculating the date format
			expectedStartChartDate =changeTheDateFormat("M/d/yyyy",today.minusDays(1))+" 12:00 AM";
			expectedEndChartDate =changeTheDateFormat("M/d/yyyy",today)+" 12:00 AM";
			//verify start and end date from chart
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
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

			Utility.moveTheScrollToTheTop();
			//Select year to date as time line
			getWebElementActionXpath("TimeLineYearToDate").click();
			aJaxWait();
						
			//Calculating the date format
			expectedStartChartDate = changeTheDateFormat("MMM YYYY", today.with(TemporalAdjusters.firstDayOfYear())).toUpperCase();
			expectedEndChartDate = changeTheDateFormat("MMM YYYY",today).toUpperCase();
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			
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
				//Nitai: Added this 2 line based on 28th release execution.
				//expectedStartChartDate = changeTheDateFormat("M/d/yyyy", today.with(TemporalAdjusters.firstDayOfYear()));
				//expectedEndChartDate = changeTheDateFormat("M/d/yyyy",today);
				
				
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
			
			Utility.moveTheScrollToTheTop();
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
			getWebElementActionXpath("TimeLineStartingFrom").click();
			Thread.sleep(1000);
			String startingDateToAdd=changeTheDateFormat("M/d/yyyy",today.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()));
			getWebElementActionXpath("StartingFromStartDate").click();
			Thread.sleep(1000);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementActionXpath("StartingFromStartDate").sendKeys(startingDateToAdd);
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
			Utility.moveTheScrollToTheTop();
			expectedStartChartDate= testData.getProperty("FixedDateStartJan012023");
			expectedEndChartDate= testData.getProperty("FixedDateEndJan312023");
			//verify start and end date from chart
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
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
			

			Utility.moveTheScrollToTheTop();
			//Drill down 1st date from chart
			Utility.moveTheElementandClick("(//*[contains(@class,'highcharts-point')])[1]");
			Thread.sleep(4000);
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, "1/1/2023 12:00 AM");
			Assert.assertEquals(actualEndChartDate, "1/2/2023 12:00 AM");
			printLog("Verified drill down dates!");
			
			//Original view
			getWebElementActionXpath_D("//span[contains(text(),'Original')]").click();
			Thread.sleep(4000);
			actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			Assert.assertEquals(actualStartChartDate, expectedStartChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndChartDate);
			printLog("Verified Original view dates!");

			login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}