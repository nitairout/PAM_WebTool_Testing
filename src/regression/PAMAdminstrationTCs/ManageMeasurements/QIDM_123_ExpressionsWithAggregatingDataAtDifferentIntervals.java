package regression.PAMAdminstrationTCs.ManageMeasurements;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/* 
 * This test verifies aggregating the data for following time intervals, Hour, Daily, Weekly, Monthly and Yearly. 
 * For this test we have created 5 measurements with expression for each interval type. 
 * We load these measurements in load profile card and verify if data is displayed at expected intervals. 
 * These values are verified manually at the time of test case creation, this test will fail if there are any changes in those values.
 */
public class QIDM_123_ExpressionsWithAggregatingDataAtDifferentIntervals extends TestBase {
	LoginTC login = null;
	ArrayList<String> tableCellData = null;
	ArrayList<ArrayList<String>> tabledata = null;
	List<WebElement> measurementList = null;
	String hourFlag = null;
	String dayFlag = null;
	String weekFlag = null;
	String monthFlag = null;
	String yearFlag = null;
	int count = 0;
	HashMap<String, Throwable> failedReasonForMeasurement = null;
	Map<String, String> passedReason = null;
	ArrayList<String> a = null;

	@Test
	public void expressionsWithAggregatingDataAtDifferentIntervals() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			// Navigate to PAM page
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			//Selected the measurement
			Utility.selectSingleMeasurement("Other", "PAMAutoTest-Aggregate Per Hour",userDefined);
			// Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			// Click on the measurement for site
			getWebElementXpath("PAMTest_MainMeter_Other").click();
			Thread.sleep(1000);
			refreshToLoadTheChart();
			// Added fixed date range
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			//refreshToLoadTheChart();

			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			String[] intervals = { "AggregatePerHour", "AggregatePerDay", "AggregatePerWeek", "AggregatePerMonth", "AggregatePerYear" };
			//String[] intervals = {"AggregatePerYear" };
			failedReasonForMeasurement = new HashMap<String, Throwable>();
			passedReason = new HashMap<String, String>();
			for (int i = 0; i < intervals.length; i++) {
				switch (intervals[i]) {
				case "AggregatePerHour":
					try {
						perHourVerification();
						passedReason.put("AggregatePerHour", "Passed");
					} catch (Throwable e) {
						failedReasonForMeasurement.put("AggregatePerHour", e);
					}
					break;
				case "AggregatePerDay":
					try {
						perDayVerification();
						passedReason.put("AggregatePerDay", "Passed");
					} catch (Throwable e) {
						failedReasonForMeasurement.put("AggregatePerDay", e);
					}
					break;
				case "AggregatePerWeek":
					try {
						perWeekVerification();
						passedReason.put("AggregatePerWeek", "Passed");
					} catch (Throwable e) {
						failedReasonForMeasurement.put("AggregatePerWeek", e);
					}
					break;
				case "AggregatePerMonth":
					try {
						perMonthVerification();
						passedReason.put("AggregatePerMonth", "Passed");
					} catch (Throwable e) {
						failedReasonForMeasurement.put("AggregatePerMonth", e);
					}
					break;
				case "AggregatePerYear":
					try {
						perYearVerification();
						passedReason.put("AggregatePerYear", "Passed");
					} catch (Throwable e) {
						failedReasonForMeasurement.put("AggregatePerYear", e);
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
				printLog("The " + key + " was Failed :"+failedReasonForMeasurement.get(key));
			}

			// Redirect to exception block if any verification failure
			if (failedReasonForMeasurement.size() > 0) {
				throw new Throwable("QIDM_123_ExpressionsWithAggregatingDataAtDifferentIntervals failed");
			}

			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void perHourVerification() throws Throwable {
		try {
			//Selected the measurement
			Utility.selectSingleMeasurement("Other", "PAMAutoTest-Aggregate Per Hour",userDefined);
			//refreshToLoadTheChart();
			Utility.moveTheScrollToTheDown();
			String hourlyTableData[] = { "1/1/2023 01:00 AM~360.0", "1/1/2023 02:00 AM~345.0", "1/1/2023 03:00 AM~353.0", "1/1/2023 04:00 AM~356.0" };
			// Get the data from Table data
			tabledata = Utility.returnPamTableData(4);

			// Verified the table data and csv file data as expected
			for (int i = 0; i < tabledata.size(); i++) {
				tableCellData = tabledata.get(i);
				Assert.assertEquals(tableCellData.get(0), hourlyTableData[i].split("~")[0]);
				Assert.assertEquals(tableCellData.get(1), hourlyTableData[i].split("~")[1]);
				tableCellData = null;
			}
			printLog("Verified the table data verification for per Hour");
		} catch (Throwable t) {
			throw t;
		}
	}

	private void perDayVerification() throws Throwable {
		try {
			//Selected the measurement
			Utility.selectSingleMeasurement("Other", "PAMAutoTest-Aggregate Per Day",userDefined);
			//refreshToLoadTheChart();
			Utility.moveTheScrollToTheDown();
			String dayTableData[] = { "1/2/2023 12:00 AM~9,459", "1/3/2023 12:00 AM~11,854", "1/4/2023 12:00 AM~3,847", "1/5/2023 12:00 AM~2,246" };
			// Get the data from Table data
			tabledata = Utility.returnPamTableData(4);
			tableCellData = null;
			// Verified the table data and csv file data as expected
			for (int i = 0; i < tabledata.size(); i++) {
				tableCellData = tabledata.get(i);
				Assert.assertEquals(tableCellData.get(0), dayTableData[i].split("~")[0]);
				Assert.assertEquals(tableCellData.get(1), dayTableData[i].split("~")[1]);
				tableCellData = null;
			}
			printLog("Verified the table data verification for per Day");
		} catch (Throwable t) {
			throw t;
		}

	}

	private void perWeekVerification() throws Throwable {
		try {
			//Selected the measurement
			Utility.selectSingleMeasurement("Other", "PAMAutoTest-Aggregate Per Week",userDefined);
			//refreshToLoadTheChart();
			Utility.moveTheScrollToTheDown();
			String weekTableData[] = { "1/2/2023 12:00 AM~95,372", "1/9/2023 12:00 AM~45,566", "1/16/2023 12:00 AM~58,835", "1/23/2023 12:00 AM~50,651" };
			// Get the data from Table data
			tabledata = Utility.returnPamTableData(4);
			tableCellData = null;
			// Verified the table data and csv file data as expected
			for (int i = 0; i < tabledata.size(); i++) {
				tableCellData = tabledata.get(i);
				Assert.assertEquals(tableCellData.get(0), weekTableData[i].split("~")[0]);
				Assert.assertEquals(tableCellData.get(1), weekTableData[i].split("~")[1]);
				tableCellData = null;
			}
			printLog("Verified the table data verification for per Week");
		} catch (Throwable t) {
			throw t;
		}
	}

	private void perMonthVerification() throws Throwable {
		try {
			//Selected the measurement
			Utility.selectSingleMeasurement("Other", "PAMAutoTest-Aggregate Per Month",userDefined);
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJun302023"));
			//refreshToLoadTheChart();
			Utility.moveTheScrollToTheDown();
			String monthTableData[] = { "2/1/2023 12:00 AM~261,782", "3/1/2023 12:00 AM~244,664", "4/1/2023 12:00 AM~226,871", "5/1/2023 12:00 AM~209,727" };
			// Get the data from Table data
			tabledata = Utility.returnPamTableData(4);
			tableCellData = null;
			// Verified the table data and csv file data as expected
			for (int i = 0; i < tabledata.size(); i++) {
				tableCellData = tabledata.get(i);
				Assert.assertEquals(tableCellData.get(0), monthTableData[i].split("~")[0]);
				Assert.assertEquals(tableCellData.get(1), monthTableData[i].split("~")[1]);
				tableCellData = null;
			}
			printLog("Verified the table data verification for per Month");
		} catch (Throwable t) {
			throw t;
		}
	}

	private void perYearVerification() throws Throwable {
		try {
			//Selected the measurement
			Utility.selectSingleMeasurement("Other", "PAMAutoTest-Aggregate Per Year",userDefined);
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJun302023"));
			//refreshToLoadTheChart();
			Utility.moveTheScrollToTheDown();
			String yearTableData[] = { "7/1/2023 12:00 AM~1,436,784" };
			// Get the data from Table data
			tabledata = Utility.returnPamTableData(1);
			tableCellData = null;
			// Verified the table data and csv file data as expected
			for (int i = 0; i < tabledata.size(); i++) {
				tableCellData = tabledata.get(i);
				Assert.assertEquals(tableCellData.get(0), yearTableData[i].split("~")[0]);
				Assert.assertEquals(tableCellData.get(1), yearTableData[i].split("~")[1]);
				tableCellData = null;
			}
			printLog("Verified the table data verification for per Year");
		} catch (Throwable t) {
			throw t;
		}
	}
}