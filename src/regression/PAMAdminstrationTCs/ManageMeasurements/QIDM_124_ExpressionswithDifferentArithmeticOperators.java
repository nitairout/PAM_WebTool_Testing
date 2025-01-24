package regression.PAMAdminstrationTCs.ManageMeasurements;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;
/* 
 * This test verifies calculated values for measurements having expressions with different arithmetic operators 
 * like division, subtraction, addition and multiplication. Calculations are verified at the time of this test case creation, 
 * this test will fail if there are any changes in the values.
 */
public class QIDM_124_ExpressionswithDifferentArithmeticOperators extends TestBase {
	LoginTC login = null;
	
	@Test
	public void expressionswithDifferentArithmeticOperators() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Select multiple measurements under Electricity - 'PAMAutoTest-division','PAMAutoTest-subtraction','PAMAutoTest-multiplication' and 'PAMAutoTest-addition'
			Utility.selectMultipleMeasurements("Electricity#PAMAutoTest-division~userDefined*PAMAutoTest-subtraction~userDefined*PAMAutoTest-multiplication~userDefined*PAMAutoTest-addition~userDefined");
			
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			
			//Click on the measurement for subsite
			getWebElementXpath("PAMTest_MainMeter_Energy").click();
			
			// Select the Fixed date range of '01/01/2023' - '01/02/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan022023"));
			refreshToLoadTheChart();
			Thread.sleep(5000);
			
			
			//Hide option and location panel
			Utility.hideOptionLocationPanelForCards();
			
			//Verified all the 4 legends from the chart
			Assert.assertEquals(getWebElementActionXpath("LineLegendOne").getText().replaceAll("\\n", " "), testData.getProperty("PAMTest_CapriataSaiwaPAMTest_MainMeterkWh"));
			Assert.assertEquals(getWebElementActionXpath("LineLegendTwo").getText().replaceAll("\\n", " "), testData.getProperty("PAMTest_CapriataSaiwaPAMTest_MainMeterkWh"));
			Assert.assertEquals(getWebElementActionXpath("LineLegendThree").getText().replaceAll("\\n", " "), testData.getProperty("PAMTest_CapriataSaiwaPAMTest_MainMeterkWh"));
			Assert.assertEquals(getWebElementActionXpath("LineLegendFour").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kgCO2");
			printLog("Verified legends from chart!!");
			
			//Selected data tab(table)
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//1st 4 rows of table data verification
			String expectedSubMeterTableData="1/1/2023 12:15 AM~12.00~0.9100~50.56~81.00|1/1/2023 12:30 AM~18.00~0.8800~48.89~78.00|1/1/2023 12:45 AM~18.00~0.9000~50.00~80.00|1/1/2023 01:00 AM~15.00~0.9100~50.56~81.00";
			verifyTableDataWithExpected(expectedSubMeterTableData,4,"QIDM_124_ExpressionswithDifferentArithmeticOperators");
			printLog("Verified the table data for 4 rows");
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

