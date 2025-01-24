package regression.PAMAdminstrationTCs.ManageMeasurements;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 *This test verifies retrieving the value for base temperature for both cooling and 
 *heating using RABaseTemp in the expression. Check following measurements to check 
 *the expressions  PAMAutoTest_RABaseTempForCooling and PAMAutoTest_RABaseTempForHeating
 *
 */
public class QIDM_141_ExpressionUsingRABaseTemp extends TestBase {
	LoginTC login = null;
	
	@Test
	public void expressionUsingRABaseTemp() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");

			Utility.selectMultipleMeasurements("Weather#PAMAutoTest_RABaseTempForCooling~userDefined*PAMAutoTest_RABaseTempForHeating~userDefined");
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Weather").click();
			
			//Select the Fixed date range of '01/01/2023' - '01/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			
			refreshToLoadTheChart();
			
			//Legend verification
			String legendOne=getWebElementActionXpath("LineLegendOne").getText().replaceAll("\\n", " ");
			String legendTwo=getWebElementActionXpath("LineLegendTwo").getText().replaceAll("\\n", " ");
			Assert.assertEquals(legendOne, "PAMTest_Capriata/Saiwa \u00B0C");
			Assert.assertEquals(legendTwo, "PAMTest_Capriata/Saiwa \u00B0C");
			printLog("Verified legends for 2 measurement");

			//Table Data tab
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			//Table header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), "PAMAutoTest_RABaseTempForCooling (\u00B0C)");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), "PAMAutoTest_RABaseTempForHeating (\u00B0C)");
			printLog("Verified table header for 2 measurement");
			
			//Table data verification
			String tableDataVerifocation="1/1/2023~18.33~18.33|1/2/2023~18.33~18.33|1/3/2023~18.33~18.33";
			verifyTableDataWithExpected(tableDataVerifocation, 3, "QIDM_141_ExpressionUsingRABaseTemp");

			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}