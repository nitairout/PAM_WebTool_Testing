package regression.PAMAdminstrationTCs.ManageMeasurements;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies calculated values for measurements having expressions with SUM, MAX & Average Time aggregation.  
 * Calculations are verified at the time of test case creation, this test will fail if there are any changes in the values.
 */
public class QIDM_126_ExpressionsWithTimeAggregation extends TestBase {
	LoginTC login = null;
	
	@Test
	public void expressionsWithTimeAggregation() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Select multiple measurements under Electricity - 'Energy' and Other - 'PAMAutoTest-SUM PER WEEK'
			Utility.selectMultipleMeasurements("Electricity#Energy~standard|Other#PAMAutoTest-SUM PER WEEK~userDefined");
			
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			
			//Click on the measurements for subsite
			getWebElementXpath("PAMTest_MainMeter_Energy").click();			
			getWebElementXpath("PAMTest_MainMeter_Other").click();
			
			// Select the Fixed date range of '01/01/2023' - '01/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();
			
			//Select the time interval - By week
			Select timeInterval=new Select(getWebElementXpath("PAMIntervalDropDown"));
			timeInterval.selectByVisibleText("By week");
			Thread.sleep(5000);
			
			Utility.moveTheScrollToTheTop();
			
			//Verify the chart legend
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText().replaceAll("\\n", " "), testData.getProperty("PAMTest_CapriataSaiwaPAMTest_MainMeterkWh"));
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText().replaceAll("\\n", " "), testData.getProperty("PAMTest_CapriataSaiwaPAMTest_MainMeterkWh"));
			printLog("Verified legend on the chart is displayed for above selected measurments");
			
			//Click on the Table Data tab
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			//First 4 rows of table data verification
			String byDayTableData="W52 2022~9,459~9,459|W01 2023~45,566~45,566|W02 2023~58,835~58,835|W03 2023~50,651~50,651";
			verifyTableDataWithExpected(byDayTableData,4,"QIDM_126_ExpressionsWithTimeAggregation");
			printLog("Table Data verification completed!!");
			
			Utility.moveTheScrollToTheTop();
			
			//Select single measurements under Other - 'PAMAutoTest- Average Per Day'
			Utility.selectSingleMeasurement("Other","PAMAutoTest- Average Per Day",userDefined);
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter \u00B0C");
			printLog("Verified legend on the chart is displayed for above selected measurment");
			
			//First 4 rows of table data verification
			byDayTableData="W52 2022~14.28|W01 2023~13.49|W02 2023~12.16|W03 2023~6.534";
			verifyTableDataWithExpected(byDayTableData,4,"QIDM_126_ExpressionsWithTimeAggregation");
			printLog("Table Data verification completed!!");
			
			Utility.moveTheScrollToTheTop();
			
			//Select single measurements under Electricity - 'PAMAutoTest-MAX PER MONTH'
			Utility.selectMultipleMeasurements("Electricity#PAMAutoTest-MAX PER MONTH~userDefined*Demand~standard");
			//Click on the measurements for subsite
			getWebElementXpath("PAMTest_MainMeter_Energy").click();	
			
			refreshToLoadTheChart();
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kW");
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kW");
			printLog("Verified legend on the chart is displayed for above selected measurments");
			
			//Select the time interval - By month
			timeInterval=new Select(getWebElementXpath("PAMIntervalDropDown"));
			timeInterval.selectByVisibleText("By month");
			Thread.sleep(5000);
			
			//First row of table data verification
			byDayTableData="Jan 2023~1,988~1,988";
			verifyTableDataWithExpected(byDayTableData,1,"QIDM_126_ExpressionsWithTimeAggregation");
			printLog("Table Data verification completed!!");
			
			Utility.moveTheScrollToTheTop();
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}