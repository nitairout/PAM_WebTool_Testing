package smokeTest;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies creating a user defined measurement and checks measurement is available in PAM card or not.
 */
public class QIDM_153_ManageMeasurementsSmokeTests extends TestBase {
	LoginTC login = null;
	String measurementName ="auto smoke test measurement";
	String expression = "[Electricity:ENERGY] [kWh] / 0.5";
	
	@Test
	public void manageMeasurementsSmokeTests() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Goto Manage measurements page
			gotoManageMeasurementPage();
			
			//Verify that default page should be 'Manage Measurements'
			String activeTab = getWebElementXpath_D("//li[@aria-selected='true']/span").getText();
			Assert.assertEquals(activeTab, "Manage Measurements");
			
			//Delete the Measurement if already existing with the same name 'MeasurementWithoutUnits'
			Utility.deleteMeasurement(measurementName);	
			
			//Clicked on 'New Measurement' button
			getWebElementActionXpath("NewMeasurement_Button").click();
			Thread.sleep(2000);
			
			//Verified the header of the popup
			String popupHeader = getWebElementXpath_D("//div[@class='modal-header']/h3").getText();
			Assert.assertEquals(popupHeader, "Create Measurement"); 
			
			//Provide the measurement name
			getWebElementXpath_D("//div[@id='basicSetupTab']/div/div/div[1]/div/input").clear();
			getWebElementXpath_D("//div[@id='basicSetupTab']/div/div/div[1]/div/input").sendKeys(measurementName);
			
			//Verified the default commodity name should be 'Electricity
			String defaultCommodity = new Select(getWebElementXpath_D("//div[@id='basicSetupTab']//select[@name='selectedCommodityedit']")).getFirstSelectedOption().getText();
			Assert.assertEquals(defaultCommodity, "Electricity"); 
			
			//Provide the UOM as 'kWh'
			getWebElementXpath_D("//div[@id='basicSetupTab']//input[@placeholder='Add a unit']").click();
			getWebElementXpath_D("//div[@id='basicSetupTab']//input[@placeholder='Add a unit']").sendKeys("kWh");
			Thread.sleep(5000);
			getWebElementActionXpath_D("//span[normalize-space(text())='kWh']").click();
			
			//Click on Save & Close button
			getWebElementActionXpath_D("//div[@class='modal-footer']/child::button[normalize-space(text())='Save & Close']").click();
			Thread.sleep(12000);
			
			printLog("Successfully added the measurement "+measurementName);
			
			//Clicked on Configurations Tab
			explicitWait_Xpath(locators.getProperty("ConfigureLocationsTab_ManageMeasurements"));
			getWebElementActionXpath("ConfigureLocationsTab_ManageMeasurements").click();
			Thread.sleep(10000);
			
			printLog("Go to Configurations Tab");
			//Filter with site name 'PAMTest_Capriata/Saiwa'
			getWebElementActionXpath("LocationPanelFilter").click();
			getWebElementXpath("LocationPanelFilter").sendKeys(testData.getProperty("PAMTestCapriataSaiwa"));
			Thread.sleep(2000);
			getWebElementXpath("LocationPanelFilter").click();
			Thread.sleep(2000);
			//Click on site 'PAMTest_Capriata/Saiwa'
			getWebElementActionXpath_D("//span[normalize-space()='PAMTest_Capriata/Saiwa']").click();
			Thread.sleep(10000);
			
			//Filter with measurement name 'auto smoke test measurement'
			getWebElementXpath("FilterByNameAndUOM").clear();
			getWebElementXpath("FilterByNameAndUOM").sendKeys(measurementName);			
			Thread.sleep(5000);
			
			//Click on setting icon for the measurement for edit expression
			getWebElementXpath("ConfigLocationMeasurementSettingIcon").click();
			Thread.sleep(5000);
			
			//Provide the measurement
			getWebElementXpath("EditExpression_Textarea").clear();
			getWebElementXpath("EditExpression_Textarea").sendKeys(expression);
			
			//Click on Validate button
			getWebElementActionXpath("EditExpression_Validate").click();
			Utility.moveTheScrollToTheDown();
			
			//Click on 'Save and close' button
			getWebElementActionXpath_D("//div[@class='ra-pam-modal-action-btns']/child::button[normalize-space(text())='Save & Close']").click();
			Thread.sleep(5000); 
			Utility.moveTheScrollToTheTop();
					
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
