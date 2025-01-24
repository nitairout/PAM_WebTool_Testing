package regression.PAMAdminstrationTCs.ManageMeasurements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies marking a measurement 'Applicable' OR  'Not Applicable ' to the location 
 * and verifies in PAM card.
 */
public class QIDM_121_EnableOrDisableMeasurement extends TestBase {
	LoginTC login = null;
	String measurementName ="Auto Test Not Applicable";
	
	@Test
	public void enableOrDisableMeasurement() throws Throwable {
		try {
			String siteNamePath = "//span[contains(text(),'PAMTest_Capriata/Saiwa')]";
			String electricCommodityTabPath = "//*[contains(@class,'commodity-tabs')]/li[2]";
			String totalMeasurementsPath = "//*[contains(@class,'commodity-tabs')]/li[2]/span/span[2]";
			String markThisMeasurementNotApplicable = "//input[@id='chkHidden']";
			
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to PAM page
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Select single measurement under Electricity - 'Auto Test Not Applicable'
			Utility.selectSingleMeasurement("Electricity",measurementName,userDefined);
			
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			//Click on the measurement for site
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			refreshToLoadTheChart();
						
			//Verify the Statistics value
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementTitle").getText(), "Total Auto Test Not Applicable");
			printLog("Verified the Statistics value");
			
			//Goto Manage measurements page
			gotoManageMeasurementPage();
			
			//Clicked on Configurations Tab
			getWebElementXpath("ConfigureLocationsTab_ManageMeasurements").click();
			Thread.sleep(5000);
			
			//Click on  site name 'PAMTest_Capriata/Saiwa'
			getWebElementActionXpath_D(siteNamePath).click();
			Thread.sleep(5000);
			//Click on Electric Commodity tab
			getWebElementActionXpath_D(electricCommodityTabPath).click();
			Thread.sleep(5000);
			
			//Verify that count of total and enabled measurements
			String[] totalConfiguredMeasurements = d.findElement(By.xpath(totalMeasurementsPath)).getText().split("/");			
			int enabledMeasurements1 = Integer.parseInt(totalConfiguredMeasurements[0]);
			int totalMeasurements1 =Integer.parseInt(totalConfiguredMeasurements[1]);
			
			printLog("Verified the count of total("+totalMeasurements1+") and enabled("+enabledMeasurements1+") measurements");
			
			//Filter with measurement name 'Auto Test Not Applicable'
			getWebElementXpath("FilterByNameAndUOM").click();
			getWebElementXpath("FilterByNameAndUOM").clear();
			getWebElementXpath("FilterByNameAndUOM").sendKeys(measurementName);			
			Thread.sleep(5000);
			
			//Click on setting icon for the measurement for edit expression
			getWebElementXpath("ConfigLocationMeasurementSettingIcon").click();
			Thread.sleep(5000);
			
			//select the checkbox 'Mark this measurement Not Applicable to this location'
			getWebElementXpath_D(markThisMeasurementNotApplicable).click();
			Utility.moveTheScrollToTheDown();
			//Click on 'Save and close' button
			getWebElementActionXpath("EditExpression_SaveAndClose").click();
			Thread.sleep(5000); 
			Utility.moveTheScrollToTheTop();
			
			//Verify that measurement has under 'Not Applicable'
			String actualLabel= getWebElementXpath_D("(//div[contains(@class,'k-panelbar-content')]/h4)[1]").getText();
			Assert.assertEquals(actualLabel,"Not Applicable");			
			String measurement= getWebElementXpath_D("//div[contains(@class,'k-panelbar-content')]//div/h4").getText();
			Assert.assertEquals(measurement,measurementName);
			Thread.sleep(5000);
			
			//Click on  site name 'PAMTest_Capriata/Saiwa'
			getWebElementActionXpath_D(siteNamePath).click();
			Thread.sleep(5000);
			//Click on Electric Commodity tab
			getWebElementActionXpath_D(electricCommodityTabPath).click();
			Thread.sleep(5000);
			
			//Clear the filter box
			getWebElementActionXpath_D("//span[@type='button'][contains(@class,'btn btn-default')]/i").click();
			
			// Verify that count of total and enabled measurements should be reduced by 1 to original values
			totalConfiguredMeasurements = d.findElement(By.xpath(totalMeasurementsPath)).getText().split("/");			
			int enabledMeasurements2 = Integer.parseInt(totalConfiguredMeasurements[0]);
			Assert.assertEquals(enabledMeasurements2,enabledMeasurements1-1);
			int totalMeasurements2 = Integer.parseInt(totalConfiguredMeasurements[1]);
			Assert.assertEquals(totalMeasurements2,totalMeasurements1-1);
			printLog("Verified the count of total("+totalMeasurements2+") and enabled("+enabledMeasurements2+") measurements it should be reduced by 1 to original values");
			
			//Navigate to PAM page
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Clicked on electricity measurement
			getWebElementActionXpath_D("//table/thead/tr[1]/th//i[contains(@class,'se-icon-"+(testData.getProperty("ElectricityCommodity").toLowerCase())+"-o com-header')]").click();
			Thread.sleep(2000);
			
			// Deselect all
			getWebElementActionXpath_D("//span[@class='se-btn-deselect-measures btn btn-link btn-xs redSE pull-right']").click();
			// Click on filter
			getWebElementXpath_D("//input[@id='commodityModalFilter']").clear();
			getWebElementXpath_D("//input[@id='commodityModalFilter']").sendKeys(measurementName);
			Thread.sleep(5000);
			List<WebElement> measurementList = d.findElements(By.xpath("//i[contains(@class,'fa fa-lg clickable fa-plus-circle')]"));
			Assert.assertEquals(measurementList.size(), 0);
			Thread.sleep(2000);
			
			// Click on save and close
			getWebElementActionXpath_D("//span[contains(text(),'Save & Close')]").click();
			Thread.sleep(5000);
			printLog("Verified that "+measurementName+" should be disabled");
			
			//Goto Manage measurements page
			gotoManageMeasurementPage();
			
			//Clicked on Configurations Tab
			getWebElementXpath("ConfigureLocationsTab_ManageMeasurements").click();
			Thread.sleep(5000);
			
			//Click on  site name 'PAMTest_Capriata/Saiwa'
			getWebElementActionXpath_D(siteNamePath).click();
			Thread.sleep(5000);
			//Click on Electric Commodity tab
			getWebElementActionXpath_D(electricCommodityTabPath).click();
			Thread.sleep(5000);
			
			//Filter with measurement name 'Auto Test Not Applicable'
			getWebElementXpath("FilterByNameAndUOM").click();
			getWebElementXpath("FilterByNameAndUOM").clear();
			getWebElementXpath("FilterByNameAndUOM").sendKeys(measurementName);			
			Thread.sleep(5000);
			
			//Click on setting icon for the measurement for edit expression
			getWebElementActionXpath("ConfigLocationMeasurementSettingIcon").click();
			Thread.sleep(5000);
			
			//unselect the checkbox 'Mark this measurement Not Applicable to this location'
			getWebElementXpath_D(markThisMeasurementNotApplicable).click();
			//Click on 'Save and close' button
			getWebElementActionXpath("EditExpression_SaveAndClose").click();
			Thread.sleep(5000); 
			Utility.moveTheScrollToTheTop();
			Thread.sleep(5000);
			//Click on site name 'PAMTest_Capriata/Saiwa'
			getWebElementActionXpath_D(siteNamePath).click();
			Thread.sleep(5000);
			//Click on Electric Commodity tab
			getWebElementActionXpath_D(electricCommodityTabPath).click();
			Thread.sleep(5000);
			
			//clear the Filtered the measurement
			getWebElementActionXpath_D("//span[@type='button'][contains(@class,'btn btn-default')]/i").click();
			
			//Verify that count of total and enabled measurements should be original values
			totalConfiguredMeasurements = d.findElement(By.xpath(totalMeasurementsPath)).getText().split("/");			
			int enabledMeasurements3 = Integer.parseInt(totalConfiguredMeasurements[0]);
			Assert.assertEquals(enabledMeasurements3,enabledMeasurements1);
			int totalMeasurements3 = Integer.parseInt(totalConfiguredMeasurements[1]);
			Assert.assertEquals(totalMeasurements3,totalMeasurements1);
			printLog("Verified the count of total("+totalMeasurements3+") and enabled("+enabledMeasurements3+") measurements should be original values");
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}