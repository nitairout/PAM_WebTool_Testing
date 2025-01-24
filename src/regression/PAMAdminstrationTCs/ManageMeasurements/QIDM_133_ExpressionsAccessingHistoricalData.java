package regression.PAMAdminstrationTCs.ManageMeasurements;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies the data for the measurements where expressions are referring to some historical data.  
 * check the measurements in Manage Measurements to see the expressions for following measurements.
 * AutoTest-HistoricalData-SUM PER DAY-5DaysAgo & AutoTest-HistoricalData-SUM PER DAY-LAST MONTH
 */
public class QIDM_133_ExpressionsAccessingHistoricalData extends TestBase {
	LoginTC login = null;
	
	@Test
	
	public void expressionsAccessingHistoricalData() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Select multiple measurements under Electricity - 'Energy' & 'AutoTest-HistoricalData-SUM PER DAY-5DaysAgo'
			Utility.selectMultipleMeasurements("Electricity#Energy~standard*AutoTest-HistoricalData-SUM PER DAY-5DaysAgo~userDefined");
			
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			
			//Click on the measurements for subsite
			getWebElementXpath("PAMTest_MainMeter_Energy").click();			
			
			// Select the Fixed date range of '01/01/2023' - '01/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			
			refreshToLoadTheChart();
			
			//Verify the legend on the chart
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText().replaceAll("\\n", " "), testData.getProperty("PAMTest_CapriataSaiwaPAMTest_MainMeterkWh"));
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText().replaceAll("\\n", " "), testData.getProperty("PAMTest_CapriataSaiwaPAMTest_MainMeterkWh"));
			printLog("Verified legend on the chart is displayed for above selected measurment");
			
			//To visible Enlarge bottom panel field
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);			
			getWebElementActionXpath("StatisticsTab").click();
			Thread.sleep(2000);
			
			//Enlarge the bottom panel 
			enlargeBottomTabsPanel();
			
			//Verify statistics value
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementTitle").getText(), "Total Usage");
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementValue").getText(), "261,782");
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementTitle").getText(), "Total AutoTest-HistoricalData-SUM PER DAY-5DaysAgo");
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementValue").getText(), "347,258");	
			printLog("Verification of statistics tab is completed!!");
			
			//Click on the Table Data tab
			Utility.moveTheScrollToTheTop();
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Verify that 6th day value for the measurement AutoTest-HistoricalData-SUM PER DAY-5DaysAgo should be equal to first day value of energy
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[1]").getText(), getWebElementActionXpath_D("//*[@id='tablebody']/tr[6]/td[2]").getText());
			printLog("Verified that 6th day value for the measurement AutoTest-HistoricalData-SUM PER DAY-5DaysAgo should be equal to first day value of energy");
			
			// hide Enlarge bottom panel
			hideEenlargeBottomTabsPanel();
			
			//Select multiple measurements under Electricity - 'Energy' & 'AutoTest-HistoricalData-SUM PER DAY-LAST MONTH'
			Utility.selectMultipleMeasurements("Electricity#Energy~standard*AutoTest-HistoricalData-SUM PER DAY-LAST MONTH~userDefined");
			
			//Verify the legend on the chart
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText().replaceAll("\\n", " "), testData.getProperty("PAMTest_CapriataSaiwaPAMTest_MainMeterkWh"));
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText().replaceAll("\\n", " "), testData.getProperty("PAMTest_CapriataSaiwaPAMTest_MainMeterkWh"));
			printLog("Verified legend on the chart is displayed for above selected measurment");
			
			//Select comparison date range as 'Previous Month'
			comparisionDateRange("Previous Month");
			
			//Externded legned to make the previous month legend display
			chartOptionExtendedLegend();
			
			hideOptionLocationPanel();
			
			// Verify the legends display under the chart.
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText().replaceAll("\\n", " "), testData.getProperty("PAMTest_CapriataSaiwaPAMTest_MainMeterkWh"));
			Assert.assertEquals(getWebElementXpath("ColumnLegendTwo").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kWh");
			Assert.assertEquals(getWebElementXpath("ColumnLegendThree").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kWh (Previous Month)");
			Assert.assertEquals(getWebElementXpath("ColumnLegendFour").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kWh (Previous Month)");
			Assert.assertEquals(getWebElementXpath("LineLegendFive").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kWh (Variance from Previous Month)");
			Assert.assertEquals(getWebElementXpath("LineLegendSix").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kWh (Variance from Previous Month)");
			printLog("Verified legends from the chart.");
			
			//Verified the data for Jan 4th AutoTest-HistoricalData-SUM PER DAY-LAST MONTH value should be equal to 12/4/2022 Energy value
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[4]/th").getText(), "1/4/2023");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[3]").getText(), "12/4/2022");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[4]/td[2]").getText(), getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[4]").getText());
			printLog("Verified the table data for 4th row");
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}