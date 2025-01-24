package regression.PAMAdminstrationTCs.ManageMeasurements;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies calculated values for measurements having some advanced calculated expressions.
 * (check manage measurement page to see the formula's for these measurements.  
 * Calculations are verified at the time of test case creation, this test will fail if there are any changes in the values.
 */

public class QIDM_131_AdvancedCalculatedExpressions extends TestBase {
	LoginTC login = null;
	
	@Test
	public void advancedCalculatedExpressions() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Select single measurement under Electricity - 'PAMAutoTest-GEM8187'
			Utility.selectSingleMeasurement("Electricity","PAMAutoTest-GEM8187",userDefined);
			
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			
			//Click on the measurements for subsite
			getWebElementXpath("PAMTest_MainMeter_Energy").click();			
			
			// Select the Fixed date range of '01/01/2023' - '01/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();

			//Verify the legend on the chart
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kWh");
			printLog("Verified legend on the chart is displayed for above selected measurment");
			
			//Verify statistics value
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementTitle").getText(), "Total PAMAutoTest-GEM8187");
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementValue").getText(), "613,962");			
			printLog("Verification of statistics tab is completed!!");
			
			//Click on the Table Data tab
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//First 4 rows of table data verification
			String byDayTableData="1/1/2023~12,087|1/2/2023~22,670|1/3/2023~22,139|1/4/2023~22,360";
			verifyTableDataWithExpected(byDayTableData,4,"QIDM_131_AdvancedCalculatedExpressions");
			printLog("Table Data verification completed!!");
			
			//Select single measurement under Electricity - 'PAMAutoTest-GEM8078'
			Utility.selectSingleMeasurement("Electricity","PAMAutoTest-GEM8078",userDefined);
			
			//Select the time interval - By month
			Select timeInterval=new Select(getWebElementXpath("PAMIntervalDropDown"));
			timeInterval.selectByVisibleText("By month");
			aJaxWait();
			//refreshToLoadTheChart();
			
			//Verify the legend
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter");
			printLog("Verified legend on the chart is displayed for above selected measurment");
			
			//Verify the table data value
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/th").getText(), "Jan 2023");
			String tableDataValue = getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[1]").getText();
			
			//Click on the Statistics tab
			getWebElementXpath("StatisticsTab").click();
			Thread.sleep(2000);
			
			//Verify statistics value
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementTitle").getText(), "Total PAMAutoTest-GEM8078");
			String statisticsValue = getWebElementActionXpath("Statistics_FirstElementValue").getText();			

			//Compare Statistics and Table Data values, it should be same
			Assert.assertEquals(tableDataValue,statisticsValue);
			printLog("Verified that Total consumption values in Statistics and values in Data table are same");
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}