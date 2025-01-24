package regression.AllCardsCommonTCs;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies large data warning message in Load Profile and Trend Analysis cards when large date range is selected.
 */
public class QIDM_171_AllCardsLargeDataWarningMessage  extends TestBase {
	LoginTC login = null;
	String popup = null;

	@Test
	public void allCardsLargeDataWarningMessage() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			//search the site as 'PAMTest_Naperville, IL \ PAMTest_Main Meter \ PAMTest_Electric \ PAMTest_Feeder B'
			searchSiteInLocationList("PAMTest_Feeder B");
			//Click on Electricity - Demand measurement for 'PAMTest_Feeder B' site
			getWebElementXpath("PAMTest_FeederB_Energy").click();
			refreshToLoadTheChart();
			
			String timeLinePath = "//input[@id='timelineLastx']";
			//Provide 520 under 'Last X days time line options
			getWebElementXpath_D(timeLinePath).click();
			getWebElementXpath_D(timeLinePath).clear();
			getWebElementXpath_D(timeLinePath).sendKeys("520");
			getWebElementXpath_D("//i[@class='fa fa-check']").click();
			Thread.sleep(5000);
			//Verify there is no any warning message and chart is loaded
			List<WebElement> warningMSG = d.findElements(By.xpath("//div[@class='modal-body' and @ng-show='exceeded']/div[2]"));
			if(warningMSG.size()>0) {
				popup = getWebElementXpath_D("//div[@class='modal-body' and @ng-show='exceeded']/div[2]").getText();
				printLog("Verified a warning message is "+popup);
				Assert.fail("Should not display the warning message.");
			}else {
				printLog("Verified that a warning message is not displaying");
			}

			//closing daylight saving pop up
			if(getWebElementXpath_D("//div[contains(@class,'alert alert-dismissable alert-warning')]//button[@class='close']/span[1]").isDisplayed()) {
				getWebElementXpath_D("//div[contains(@class,'alert alert-dismissable alert-warning')]//button[@class='close']/span[1]").click();
			}			
			
			//Provide 521 under 'Last X days time line options
			getWebElementXpath_D(timeLinePath).click();
			getWebElementXpath_D(timeLinePath).clear();
			getWebElementXpath_D(timeLinePath).sendKeys("521");
			getWebElementXpath_D("//i[@class='fa fa-check']").click();
			Thread.sleep(5000);
			//Verify there is a warning message and chart is loaded
			warningMSG = d.findElements(By.xpath("//div[contains(@class,'modal-content')]/div[2]/div[2]"));
			if(warningMSG.size()>0) {
				popup = getWebElementXpath_D("//div[contains(@class,'modal-content')]/div[2]/div[2]").getText();
				Assert.assertEquals(popup, "The large number of data points in this chart may result in reduced performance.");
				printLog("Verified that a warning message is displaying ... "+popup);
			}else {
				printLog("Should display the warning message.");
				Assert.fail("Should display the warning message.");
			}
			
			refreshToLoadTheChart();
			
			//Added the Fixed date "01/01/2022" - "06/30/2023"
			addFixedDateRange("01/01/2022","06/30/2023");
			//Verify there is a warning message and chart is loaded
			warningMSG = d.findElements(By.xpath("//div[contains(@class,'modal-content')]/div[2]/div[2]"));
			if(warningMSG.size()>0) {
				popup = getWebElementXpath_D("//div[contains(@class,'modal-content')]/div[2]/div[2]").getText();
				Assert.assertEquals(popup, "The large number of data points in this chart may result in reduced performance.");
				printLog("Verified that a warning message is displaying ... "+popup);
			}else {
				printLog("Should display the warning message.");
				Assert.fail("Should display the warning message.");
			}
			refreshToLoadTheChart();
			
			card2Card("Trend Analysis");
			
			//goToPAMCard("TrendAnalysisCard");
			//Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);
			
			//Click on OneClick icon
			getWebElementXpath("OnceClick_Icon").click();
			Thread.sleep(2000);
			//Click on Edit One Click Settings Link
			getWebElementXpath("EditOneClickSettingsLink").click();
			Thread.sleep(2000);
			//Click on Behaviour Tab
			getWebElementXpath("BehaviourTab").click();
			Thread.sleep(2000);
			//Select 'Activate Descendants matching specified criteria' under Action. 
			new Select(getWebElementActionXpath_D("(//div[@id='behaviorContainer']/div[2]//div[@class='col-xs-10']/select)[1]")).selectByVisibleText("Activate Descendants matching specified criteria");	
			Thread.sleep(2000);
			//Select 'Descendant node has the following' , Match 'Any' and select AGG_Electricity:include'
			new Select(getWebElementActionXpath_D("(//div[@id='behaviorContainer']/div[2]//div[@class='col-xs-10']/select)[2]")).selectByVisibleText("Descendant node has the following tags");	
			Thread.sleep(5000);
			new Select(getWebElementActionXpath_D("(//div[@id='behaviorContainer']/div[2]//div[@class='col-xs-3']/select)[2]")).selectByVisibleText("Any");	
			Thread.sleep(2000);
			
			getWebElementActionXpath_D("//*[@class='k-input-values']/input").click();
			Thread.sleep(2000);
			getWebElementActionXpath_D("//*[@class='k-input-values']/input").sendKeys("AGG_ELECTRICITY:Include");
			Thread.sleep(2000);         
			getWebElementActionXpath_D("//span[contains(.,'AGG_ELECTRICITY:  Include')]").click();
			Thread.sleep(2000);
			
			//Click on save and close
			getWebElementActionXpath_D("//span[contains(.,'Save & Close')]").click();
			Thread.sleep(5000);
			
			//Search the site as 'PAMTest_Capriata/Saiwa'
			searchSiteInLocationList("PAMTest_Capriata/Saiwa");
			//Click on SiteName 'PAMTest_Capriata/Saiwa'
			getWebElementActionXpath_D("//td[normalize-space()='PAMTest_Capriata/Saiwa']").click();
			
			//Added the Fixed date "01/01/2023" - "01/31/2023"
			addFixedDateRange("01/01/2023","01/31/2023");
			Utility.moveTheScrollToTheTop();
			//Verify there is no any warning message and chart is loaded
			warningMSG = d.findElements(By.xpath("//div[@class='modal-body' and @ng-show='exceeded']/div[2]"));
			if(warningMSG.size()>0) {
				popup = getWebElementXpath_D("//div[@class='modal-body' and @ng-show='exceeded']/div[2]").getText();
				printLog("Verified a warning message is "+popup);
				Assert.fail("Should not display the warning message.");
			}else {
				printLog("Verified that a warning message is not displaying");
			}
					
			refreshToLoadTheChart();
			
			//Added the Fixed date - "01/01/2022" - "12/31/2022"
			addFixedDateRange("01/01/2022","12/31/2022");
			
			Utility.moveTheScrollToTheTop();
			//Verify there is a warning message and chart is loaded
			warningMSG = d.findElements(By.xpath("//div[contains(@class,'modal-content')]/div[2]/div[2]"));
			if(warningMSG.size()>0) {
				popup = getWebElementXpath_D("//div[contains(@class,'modal-content')]/div[2]/div[2]").getText();
				Assert.assertEquals(popup, "The large number of data points in this chart may result in reduced performance.");
				printLog("Verified that a warning message is displaying ... "+popup);
			}else {
				printLog("Should display the warning message.");
				Assert.fail("Should display the warning message.");
			}
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

