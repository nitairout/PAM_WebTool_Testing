package regression.PAMAdminstrationTCs.ManageMeasurements;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/* This test verifies the data for the measurements with different aggregation methods like Auto, Sum, Avg, Max and Min. 
 * For each measurement it verifies statistics and first 4 table values.(Note: At the time of test case creation, 
 * all these values are manually verified and confirmed values are displayed as expected for each aggregation method, 
 * if there are any change in these values, we will fail the test case)
 */
public class QIDM_122_ExpressionsWithDifferentAggregationMethods extends TestBase {
	LoginTC login = null;
	HashMap<String, Throwable> failedReasonForMeasurement = null;
	Map<String, String> passedReason = null;
	String byDayTableData = null;
	
	@Test
	public void expressionsWithDifferentAggregationMethods() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to PAM page
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			
			// Select the Fixed date range of '1/1/2023' - '1/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			Thread.sleep(5000);
			
			
			String[] aggregationMethod = { "AggregationMethodAuto", "AggregationMethodSum", "AggregationMethodAvg", "AggregationMethodMax", "AggregationMethodMin" };
			//String[] aggregationMethod = {"AggregationMethodMin" };
			failedReasonForMeasurement = new HashMap<String, Throwable>();
			passedReason = new HashMap<String, String>();
			for (int i = 0; i < aggregationMethod.length; i++) {
				switch (aggregationMethod[i]) {
				case "AggregationMethodAuto":
					try {
						aggregationMethodAUTOVerification();
						passedReason.put("AggregationMethodAuto", "Passed");
					} catch (Throwable e) {
						failedReasonForMeasurement.put("AggregationMethodAuto", e);
					}
					break;
				case "AggregationMethodSum":
					try {
						aggregationMethodSUMVerification();
						passedReason.put("AggregationMethodSum", "Passed");
					} catch (Throwable e) {
						failedReasonForMeasurement.put("AggregationMethodSum", e);
					}
					break;
				case "AggregationMethodAvg":
					try {
						aggregationMethodAVGVerification();
						passedReason.put("AggregationMethodAvg", "Passed");
					} catch (Throwable e) {
						failedReasonForMeasurement.put("AggregationMethodAvg", e);
					}
					break;
				case "AggregationMethodMax":
					try {
						aggregationMethodMAXVerification();
						passedReason.put("AggregationMethodMax", "Passed");
					} catch (Throwable e) {
						failedReasonForMeasurement.put("AggregationMethodMax", e);
					}
					break;
				case "AggregationMethodMin":
					try {
						aggregationMethodMINVerification();
						passedReason.put("AggregationMethodMin", "Passed");
					} catch (Throwable e) {
						failedReasonForMeasurement.put("AggregationMethodMin", e);
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

			// Redirect to exception block if any verification failure
			if (failedReasonForMeasurement.size() > 0) {
				throw new Throwable("QIDM_122_AggregationMethods failed");
			}
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private void aggregationMethodAUTOVerification() throws Throwable {
		try {
			//Selected the measurement
			Utility.selectSingleMeasurement("Other", "Aggregation Method: Auto",userDefined);
			d.navigate().refresh();
			aJaxWait();
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			
			//Click on the measurement for site
			getWebElementXpath("PAMTest_CapriataSaiwa_Other").click();
			Thread.sleep(5000);
			
			refreshToLoadTheChart();
			aJaxWait();
			//Verify the chart legend
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), "PAMTest_Capriata/Saiwa kWh/unit");
			printLog("Verified the Legend for 'Aggregation Method: Auto' measurement");
			
			
			//Verify the Statistics value
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementTitle").getText(), "Min Daily Aggregation Method: Auto");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementValue").getText(), "58.22");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementUnit").getText(), "kWh/unit");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementTitle").getText(), "Max Daily Aggregation Method: Auto");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementValue").getText(), "789.0");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementUnit").getText(), "kWh/unit");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementTitle").getText(), "Avg Daily Aggregation Method: Auto");
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementValue").getText(), "261.8");
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementUnit").getText(), "kWh/unit");
			printLog("Verified the Stats data for 'Aggregation Method: Auto' measurement");
			
			//Click on the Table Data tab
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			//First 4 rows of table data verification
			byDayTableData="1/1/2023~293.2|1/2/2023~367.5|1/3/2023~119.3|1/4/2023~69.63";
			verifyTableDataWithExpected(byDayTableData,4,"QIDM_122_AggregationMethods");
			printLog("Table Data verification completed for 'Aggregation Method: Auto' measurement");
			
			printLog("aggregationMethodAutoVerification...Passed");
		} catch (Throwable t) {
			t.printStackTrace();
			printLog("aggregationMethodAutoVerification...Failed");
			throw t;
		}
	}
	
	private void aggregationMethodSUMVerification() throws Throwable {
		try {
			//Selected the measurement
			Utility.selectSingleMeasurement("Gas", "Aggregation Method: Sum",userDefined);
			
			d.navigate().refresh();
			aJaxWait();
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			
			//Click on the measurement for site
			getWebElementXpath("PAMTest_CapriataSaiwa_Gas").click();
			Thread.sleep(5000);
			
			refreshToLoadTheChart();
			aJaxWait();
			//Verify the chart legend
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwam3"));
			//Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwam3"));
			printLog("Verified the Legend for 'Aggregation Method: Sum' measurement");
			
			//Click on 'Statistics' Tab
			getWebElementXpath("StatisticsTab").click();
			Thread.sleep(2000);
			//Verify the Statistics value
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementTitle").getText(), "Total Aggregation Method: Sum");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementValue").getText(), "1,459,808");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementUnit").getText(), "m3");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementTitle").getText(), "Min Daily Aggregation Method: Sum");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementValue").getText(), "11,289");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementUnit").getText(), "m3");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementTitle").getText(), "Max Daily Aggregation Method: Sum");
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementValue").getText(), "68,902");
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementUnit").getText(), "m3");
			printLog("Verified the Stats data for 'Aggregation Method: Sum' measurement");
			
			//Click on the Table Data tab
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			//First 4 rows of table data verification
			byDayTableData="1/1/2023~11,289|1/2/2023~50,021|1/3/2023~61,741|1/4/2023~59,916";
			verifyTableDataWithExpected(byDayTableData,4,"QIDM_122_AggregationMethods");
			printLog("Table Data verification completed for 'Aggregation Method: Sum' measurement");
			
			printLog("aggregationMethodSumVerification...Passed");
		} catch (Throwable t) {
			t.printStackTrace();
			printLog("aggregationMethodSumVerification...Failed");
			throw t;
		}
	}
	
	private void aggregationMethodAVGVerification() throws Throwable {
		try {
			//Selected the measurement
			Utility.selectSingleMeasurement("Weather", "Aggregation Method: Avg",userDefined);
			d.navigate().refresh();
			aJaxWait();
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			//Click on the measurement for site
			getWebElementXpath("PAMTest_CapriataSaiwa_Weather").click();
			Thread.sleep(5000);
			
			refreshToLoadTheChart();
			aJaxWait();
			//Verify the chart legend
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwa0C"));
			printLog("Verified the Legend for 'Aggregation Method: Avg' measurement");
			
			//Click on 'Statistics' Tab
			getWebElementXpath("StatisticsTab").click();
			Thread.sleep(2000);
			//Verify the Statistics value
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementTitle").getText(), "Min Daily Aggregation Method: Avg");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementValue").getText(), "5.000");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementUnit").getText(), "\u00B0C");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementTitle").getText(), "Max Daily Aggregation Method: Avg");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementValue").getText(), "15.00");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementUnit").getText(), "\u00B0C");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementTitle").getText(), "Avg Daily Aggregation Method: Avg");
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementValue").getText(), "10.05");
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementUnit").getText(), "\u00B0C");
			printLog("Verified the Stats data for 'Aggregation Method: Avg' measurement");
			
			//Click on the Table Data tab
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			//First 4 rows of table data verification
			String byDayTableData="1/1/2023~14.28|1/2/2023~15.00|1/3/2023~12.94|1/4/2023~13.80";
			verifyTableDataWithExpected(byDayTableData,4,"QIDM_122_AggregationMethods");
			printLog("Table Data verification completed for 'Aggregation Method: Avg' measurement");

			printLog("aggregationMethodAvgVerification...Passed");
		} catch (Throwable t) {
			t.printStackTrace();
			printLog("aggregationMethodAvgVerification...Failed");
			throw t;
		}
	}
	
	private void aggregationMethodMAXVerification() throws Throwable {
		try {
			//Selected the measurement
			Utility.selectSingleMeasurement("Electricity", "Aggregation Method: Max",userDefined);
			d.navigate().refresh();
			aJaxWait();
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			//Select the time interval - By hour
			Select timeInterval=new Select(getWebElementXpath("PAMIntervalDropDown"));
			timeInterval.selectByVisibleText("By hour");
			Thread.sleep(6000);
			
			Utility.moveTheScrollToTheTop();
			
			//Click on the measurement for site
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			Thread.sleep(5000);
			
			refreshToLoadTheChart();
			aJaxWait();
			//Verify the chart legend
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "PAMTest_Capriata/Saiwa kW");
			printLog("Verified the Legend for 'Aggregation Method: Max' measurement");
			
			//Click on 'Statistics' Tab
			getWebElementXpath("StatisticsTab").click();
			Thread.sleep(2000);
			//Verify the Statistics value
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementTitle").getText(), "Total Aggregation Method: Max");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementValue").getText(), "1,047,128");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementUnit").getText(), "kW");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementTitle").getText(), "Min Hourly Aggregation Method: Max");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementValue").getText(), "168.0");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementUnit").getText(), "kW");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementTitle").getText(), "Max Hourly Aggregation Method: Max");
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementValue").getText(), "7,548");
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementUnit").getText(), "kW");
			printLog("Verified the Stats data for 'Aggregation Method: Max' measurement");
			
			//Click on the Table Data tab
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			//First 4 rows of table data verification
			byDayTableData="1/1/2023 01:00 AM~1,440|1/1/2023 02:00 AM~1,380|1/1/2023 03:00 AM~1,412|1/1/2023 04:00 AM~1,424";
			verifyTableDataWithExpected(byDayTableData,4,"QIDM_122_AggregationMethods_Max");
			printLog("Table Data verification completed for 'Aggregation Method: Max' measurement");
			
			printLog("aggregationMethodMAXVerification...Passed");
		} catch (Throwable t) {
			t.printStackTrace();
			printLog("aggregationMethodMAXVerification...Failed");
			throw t;
		}
	}
	
	private void aggregationMethodMINVerification() throws Throwable {
		try {
			//Selected the measurement
			Utility.selectSingleMeasurement("Electricity", "Aggregation Method: Min",userDefined);
			d.navigate().refresh();
			aJaxWait();
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			//Select the time interval - By hour
			Select timeInterval=new Select(getWebElementXpath("PAMIntervalDropDown"));
			timeInterval.selectByVisibleText("By hour");
			Thread.sleep(6000);
			
			Utility.moveTheScrollToTheTop();
			/*
			//Click on the measurement for site
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			Thread.sleep(5000);
			
			refreshToLoadTheChart();
			aJaxWait();
			*/
			//Verify the chart legend
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "PAMTest_Capriata/Saiwa kW");
			printLog("Verified the Legend for 'Aggregation Method: Min' measurement");
			
			//Click on 'Statistics' Tab
			getWebElementXpath("StatisticsTab").click();
			Thread.sleep(2000);
			//Verify the Statistics value
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementTitle").getText(), "Min Hourly Aggregation Method: Min");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementValue").getText(), "32.00");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementUnit").getText(), "kW");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementTitle").getText(), "Max Hourly Aggregation Method: Min");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementValue").getText(), "1,868");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementUnit").getText(), "kW");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementTitle").getText(), "Avg Hourly Aggregation Method: Min");
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementValue").getText(), "306.8");
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementUnit").getText(), "kW");
			printLog("Verified the Stats data for 'Aggregation Method: Min' measurement");
			
			//Click on the Table Data tab
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//First 4 rows of table data verification
			String byDayTableData="1/1/2023 01:00 AM~352.0|1/1/2023 02:00 AM~340.0|1/1/2023 03:00 AM~340.0|1/1/2023 04:00 AM~344.0";
			verifyTableDataWithExpected(byDayTableData,4,"QIDM_122_AggregationMethods_Min");
			printLog("Table Data verification completed for 'Aggregation Method: Min' measurement");
			
			printLog("aggregationMethodMINVerification...Passed");
		} catch (Throwable t) {
			t.printStackTrace();
			printLog("aggregationMethodMINVerification...Failed");
			throw t;
		}
	}
}
