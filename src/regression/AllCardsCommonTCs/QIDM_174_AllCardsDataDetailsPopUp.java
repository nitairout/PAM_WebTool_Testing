package regression.AllCardsCommonTCs;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies Data Details for selected sites and measurements in Trend and Load profile cards.
 */

public class QIDM_174_AllCardsDataDetailsPopUp extends TestBase {
	LoginTC login = null;

	@Test
	public void allCardsDataDetailsPopUp() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();

			// Navigate to Trend Analysis card
			goToPAMCard("TrendAnalysisCard");
			//Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);
			
			//Selecting PAMTestCapriataSaiwa
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			refreshToLoadTheChart();
			//Selecting PAMTest_Shadow 7650
			searchSiteInLocationList("PAMTest_Shadow 7650");
			getWebElementXpath("PAMTest_Shadow7650_Energy").click();
			
			refreshToLoadTheChart();
			
			//Selected Data Details from kabob menu
			kabobMenuOptions("Data Details");
			
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[1]/td[2]").getText(), "PAMTest_Capriata/Saiwa");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[1]/td[3]//span").getText(), "Energy");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[1]/td[4]").getText(), "Calculated Measurement - 2 kWh");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[1]/td[5]").getText(), "Calculated Measurement");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[1]/td[6]").getText(), "Not Available");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[1]/td[7]").getText(), "Not Available");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[1]/td[8]").getText(), "Not Available");
			printLog("Data verified for PAMTestCapriataSaiwa.");
			
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[2]/td[2]").getText(), "PAMTest_Capriata/Saiwa\\...\\PAMTest_Shadow 7650");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[2]/td[3]//span").getText(), "Energy");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[2]/td[4]").getText(), "kWh Del Int");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[2]/td[5]").getText(), "MDLZ.EU.IT.ITATC_COMX.GTW_E10.ELEC_ION7650");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[2]/td[6]").getText(), "(UTC+01:00) Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[2]/td[7]").getText(), "15");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[2]/td[8]").getText(), "Online");
			printLog("Data verified for PAMTest_Shadow 7650.");
			
			//Ok to close the pop up
			getWebElementXpath_D("//button[contains(text(),'OK')]").click();
			Thread.sleep(1000);
			printLog("Ok to close the pop up.");
			
			card2Card("Load Profile Analysis");
			
			//Added Energy measurement from Electricity commodity
			Utility.selectMultipleMeasurements("Electricity#Energy~standard*Demand~standard");
			
			//de-Selecting PAMTestCapriataSaiwa
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			refreshToLoadTheChart();
			
			//Selected Data Details from kabob menu for LP
			kabobMenuOptions("Data Details");
			
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[1]/td[2]").getText(), "PAMTest_Capriata/Saiwa\\...\\PAMTest_Shadow 7650");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[1]/td[3]//span").getText(), "Energy");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[1]/td[4]").getText(), "kWh Del Int");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[1]/td[5]").getText(), "MDLZ.EU.IT.ITATC_COMX.GTW_E10.ELEC_ION7650");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[1]/td[6]").getText(), "(UTC+01:00) Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[1]/td[7]").getText(), "15");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[1]/td[8]").getText(), "Online");
			printLog("Data verified for Energy measurement.");

			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[2]/td[2]").getText(), "PAMTest_Capriata/Saiwa\\...\\PAMTest_Shadow 7650");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[2]/td[3]//span").getText(), "Demand");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[2]/td[4]").getText(), "Auto Calculated Measurement - 1 kW");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[2]/td[5]").getText(), "Calculated Measurement");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[2]/td[6]").getText(), "Not Available");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[2]/td[7]").getText(), "Not Available");
			Assert.assertEquals(getWebElementActionXpath_D("//kendo-grid-list/div/div[1]/table/tbody/tr[2]/td[8]").getText(), "Not Available");
			printLog("Data verified for Demand measurement.");

			//Ok to close the pop up
			getWebElementXpath_D("//button[contains(text(),'OK')]").click();
			Thread.sleep(1000);
			printLog("Ok to close the pop up for LP.");
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
