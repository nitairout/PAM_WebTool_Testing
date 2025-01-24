package regression.PAMAdminstrationTCs.ManageMeasurements;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*This test verifies calculated values for measurements having expressions with different exponential operators 
 * like LOG, SQRT and POWER.Calculations are verified at the time of this test case creation,this test will fail if there are any changes in the values.
 */
public class QIDM_125_ExpressionswithDifferentExponentialOperators extends TestBase {
	LoginTC login = null;
	
	@Test
	public void expressionswithDifferentExponentialOperators() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Select multiple measurements under Electricity - 'PAMAutoTest-LOG','PAMAutoTest-SQRT' and 'PAMAutoTest-Power'
			Utility.selectMultipleMeasurements("Electricity#PAMAutoTest-LOG~userDefined*PAMAutoTest-SQRT~userDefined*PAMAutoTest-Power~userDefined");
			
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			
			//Click on the measurement for subsite
			getWebElementXpath("PAMTest_MainMeter_Energy").click();
			
			// Select the Fixed date range of '01/01/2023' - '01/02/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan022023"));
			refreshToLoadTheChart();
			
			//Hide option and location panel
			Utility.hideOptionLocationPanelForCards();
			
			//Verified all the 3 legends from the chart
			Assert.assertEquals(getWebElementActionXpath("LineLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter");
			Assert.assertEquals(getWebElementActionXpath("LineLegendTwo").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter");
			Assert.assertEquals(getWebElementActionXpath("LineLegendThree").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter");
			printLog("Verified legends from chart!!");
			
			//Selected data tab(table)
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//1st 4 rows of table data verification
			String expectedSubMeterTableData="1/1/2023 12:15 AM~1.959~9.539~8,281|1/1/2023 12:30 AM~1.944~9.381~7,744|1/1/2023 12:45 AM~1.954~9.487~8,100|1/1/2023 01:00 AM~1.959~9.539~8,281";
			verifyTableDataWithExpected(expectedSubMeterTableData,4,"QIDM_125_ExpressionswithDifferentExponentialOperators");
			printLog("Verified the table data for 4 rows");
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}