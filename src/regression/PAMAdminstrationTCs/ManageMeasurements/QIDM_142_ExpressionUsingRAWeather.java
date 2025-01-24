package regression.PAMAdminstrationTCs.ManageMeasurements;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies retrieving the daily minimum and maximum temperature values using RAWeather() in the expression.
 * Check the following measurements for the expressions PAMAuto Test-RAWeatherData1 & PAMAuto Test-RAWeatherData2. 
 * Values are verified at the time of TC creation and test case will fail any change in values. 
 */

public class QIDM_142_ExpressionUsingRAWeather extends TestBase {
	LoginTC login = null;
	
	@Test
	public void expressionUsingRAWeather() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			Utility.selectMultipleMeasurements("Weather#PAMAuto Test-RAWeatherData1~userDefined*PAMAuto Test-RAWeatherData2~userDefined");
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Weather").click();

			//Select the Fixed date range of '01/01/2022' - '01/31/2022' (Note: data for 2023 was not available)
			addFixedDateRange(testData.getProperty("FixedDateStartJan012022"), testData.getProperty("FixedDateEndJan312022"));
			
			refreshToLoadTheChart();
			//Legend verification
			Assert.assertEquals(getWebElementActionXpath("LineLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \u00B0F");
			Assert.assertEquals(getWebElementActionXpath("LineLegendTwo").getText().replaceAll("\\n", " "), testData.getProperty("PAMTest_CapriataSaiwa0C"));
			printLog("Verified legends for 2 measurement");
			//Table Data tab
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			//Table header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), "PAMAuto Test-RAWeatherData1 (\u00B0F)");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), "PAMAuto Test-RAWeatherData2 (\u00B0C)");
			printLog("Verified table header for 2 measurement");
			
			//Table data verification
			String tableDataVerifocation="1/1/2022~54.00~14.00|1/2/2022~55.00~14.00";
			verifyTableDataWithExpected(tableDataVerifocation, 2, "QIDM_142_ExpressionUsingRAWeather");

			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
