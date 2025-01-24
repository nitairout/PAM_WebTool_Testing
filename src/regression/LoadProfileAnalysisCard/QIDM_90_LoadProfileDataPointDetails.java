package regression.LoadProfileAnalysisCard;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies data point details pop up and verifies single data point details 
 * for a interval data in both raw and corrected data mode.   
 * 
 */

public class QIDM_90_LoadProfileDataPointDetails extends TestBase{
	LoginTC login = null;
	@Test
	public void loadProfileDataPointDetails() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);

			//Selecting PAMTest_Shadow 7650
			searchSiteInLocationList("PAMTest_Shadow 7650");
			getWebElementXpath("PAMTest_Shadow7650_Energy").click();
			printLog("Selected PAMTest_Shadow 7650.");
			
			//Added the Fixed date
			addFixedDateRange("03/11/2023","03/11/2023");
			
			//Expand overlay
			getWebElementActionXpath("OverlayExpander").click();
			Thread.sleep(1000);
			//Data quality flag on
			getWebElementActionXpath("OverlayDataQualityFlagsCheckBox").click();
			Thread.sleep(1000);
			printLog("Data quality flag checked.");
			
			refreshToLoadTheChart();
			
			//Verify if the raw mode is by default
			if(!isElementPresent("ChangeTOCorrectedMode")){
				throw new Exception("Raw mode is not selected by default!");
			}
			printLog("Verified corrected mode is selected.");
			
			//Legend verification
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650 kWh");
			printLog("Legend verified for Raw mode.");
			//click on the flag
			getWebElementActionXpath_D("("+locators.getProperty("DataQualityFlagsOnCorectMode")+")[2]").click();
			Thread.sleep(5000);
			//Site verification
			Assert.assertEquals(getWebElementActionXpath("ChartFlagPopUpHeaderSiteName").getText(), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650");
			//Time stamp verification
			Assert.assertEquals(getWebElementXpath_D("//div[@id='se-pt-dtl-inner-body-header']//span[@class='col-xs-6']").getText(), "Sat-Mar 11, 2023-17:45:00");
			//Prev button verification
			Assert.assertTrue(isElementPresent_D("//div[@id='se-pt-dtl-inner-body-header']//i[contains(@class,'fa-backward')]"));
			//Next button verification
			Assert.assertTrue(isElementPresent_D("//div[@id='se-pt-dtl-inner-body-header']//i[contains(@class,'fa-forward')]"));
			//Commodity icon and measurement
			Assert.assertTrue(isElementPresent_D("//div[@id='se-pt-dtl-val-commodity']//i"));
			Assert.assertEquals(getWebElementXpath_D("//div[@id='se-pt-dtl-val-commodity']//h2").getText(), "Energy");
			//Unit UOM
			Assert.assertEquals(getWebElementXpath_D("//span[@id='se-pt-dtl-val-item-unit']").getText(), "kWh");
			//Value
			//Assert.assertEquals(getWebElementXpath_D("//h1[@id='se-pt-dtl-val-item-num']").getText(), "88.00");
			Assert.assertEquals(getWebElementXpath_D("//h1[@id='se-pt-dtl-val-item-num']").getText(), "88.00");
			//Accuracy button color
			Assert.assertEquals(getWebElementXpath_D("//button[@id='se-pt-dtl-metered-btn']").getCssValue("background-color"), "rgba(50, 173, 60, 1)");
			//Validity
			//Assert.assertEquals(getWebElementXpath_D("//button[@id='se-pt-dtl-suspect-btn']").getCssValue("background-color"), "rgba(50, 173, 60, 1)");
			Assert.assertEquals(getWebElementXpath_D("//button[@id='se-pt-dtl-val-btn-lock']").getText().trim(), "Unlocked");
			Assert.assertEquals(getWebElementXpath_D("//div[@id='se-pt-dtl-val-btn-edit']").getText().trim(), "Original");
			//Delta calculation note
			//String rawexceptionNote="[DeltaCalculator 2023-03-11 19:54:55] System detected a catch-up spike.";
			String rawexceptionNote="System detected a catch-up spike.";
			Assert.assertTrue(getWebElementXpath("ChartFlagPopupNote").getText().contains(rawexceptionNote));
			printLog("Data point pop up opened and verified the data.");
			
			//Edit
			getWebElementActionXpath_D("//span[@id='se-pt-dtl-edit-btn' and text()='Edit']").click();
			Thread.sleep(3000);
			//unit val text box activated
			Assert.assertTrue(isElementPresent_D("//input[@id='se-pt-dtl-val-item-num']"));
			//Save button displayed
			Assert.assertTrue(isElementPresent_D("//span[@id='se-pt-dtl-save-btn']"));
			//Cancel
			getWebElementXpath_D("//span[@id='se-pt-dtl-cancel-btn' and text()='Cancel']").click();
			printLog("Verified on edit mode.");
			
			//close the pop up
			getWebElementActionXpath("CloseDataPointDetailPopUp").click();
			Thread.sleep(1000);
			printLog("Canceled the updated value.");
			
			// Selected corrected mode
			getWebElementActionXpath("ChangeTOCorrectedMode").click();
			aJaxWait();
			printLog("Selected Corrected mode and load the chart.");
			
			//Legend verification
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650 kWh");
			printLog("Legend verified for Raw mode.");
			//click on the flag
			getWebElementActionXpath_D("("+locators.getProperty("DataQualityFlagsOnCorectMode")+")[2]").click();
			Thread.sleep(5000);
			//Site verification
			Assert.assertEquals(getWebElementActionXpath("ChartFlagPopUpHeaderSiteName").getText(), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650");
			//Time stamp verification
			Assert.assertEquals(getWebElementXpath_D("//div[@id='se-pt-dtl-inner-body-header']//span[@class='col-xs-6']").getText(), "Sat-Mar 11, 2023-17:45:00");
			//Prev button verification
			Assert.assertTrue(isElementPresent_D("//div[@id='se-pt-dtl-inner-body-header']//i[contains(@class,'fa-backward')]"));
			//Next button verification
			Assert.assertTrue(isElementPresent_D("//div[@id='se-pt-dtl-inner-body-header']//i[contains(@class,'fa-forward')]"));
			//Commodity icon and measurement
			Assert.assertTrue(isElementPresent_D("//div[@id='se-pt-dtl-val-commodity']//i"));
			Assert.assertEquals(getWebElementXpath_D("//div[@id='se-pt-dtl-val-commodity']//h2").getText(), "Energy");
			//Unit UOM
			Assert.assertEquals(getWebElementXpath_D("//span[@id='se-pt-dtl-val-item-unit']").getText(), "kWh");
			//Value
			//Assert.assertEquals(getWebElementXpath_D("//h1[@id='se-pt-dtl-val-item-num']").getText(), "82.32");
			//Accuracy button color
			Assert.assertEquals(getWebElementXpath_D("//button[@id='se-pt-dtl-metered-btn']").getCssValue("background-color"), "rgba(255, 255, 255, 1)");
			//Validity
			Assert.assertEquals(getWebElementXpath_D("//button[@id='se-pt-dtl-val-btn-lock']").getText().trim(), "Unlocked");
			Assert.assertEquals(getWebElementXpath_D("//div[@id='se-pt-dtl-val-btn-edit']").getText().trim(), "Original");
			//Delta calculation note
			//String correctedexceptionNote="[DeltaCalculator 2023-03-11 19:54:55] System detected a catch-up spike. Estimate calculated with a weighted average profile.";
			String correctedexceptionNote="System detected a catch-up spike. Estimate calculated with a weighted average profile.";
			Assert.assertTrue(getWebElementXpath("ChartFlagPopupNote").getText().replaceAll("\\n", " ").contains(correctedexceptionNote));
			//Edit button disabled message
			Assert.assertEquals(getWebElementXpath_D("//span[@id='se-pt-dtl-disab-msg']/i").getText().trim(), "Edit is disabled for Estimate values. Enter raw data mode to modify.");
			//Edit button is disabled
			Assert.assertEquals(getWebElementXpath_D("//span[@id='se-pt-dtl-edit-btn']").getAttribute("disabled"),"true");
			printLog("Data point pop up opened and verified the data.");
		
			//close the pop up
			getWebElementActionXpath("CloseDataPointDetailPopUp").click();
			Thread.sleep(1000);
			printLog("Close the pop up");
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}