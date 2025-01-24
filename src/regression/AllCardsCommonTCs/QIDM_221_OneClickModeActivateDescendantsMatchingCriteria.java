package regression.AllCardsCommonTCs;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies functionality of one click mode 'Advance Data Selector' option 'Activate Descendants matching specified criteria' 
 * on Trend Analysis and Calendar Load Profile cards.
 * Also pushes same chart to dashboard and comes back to the card to verify one click mode is still enabled with expected site.
 */
public class QIDM_221_OneClickModeActivateDescendantsMatchingCriteria extends TestBase{
	LoginTC login = null;
	@Test
	public void oneClickModeActivateDescendantsMatchingCriteria() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Go to 'One Click Mode Tests' under 'Automation Test Dashboards' folder
			Utility.goToPAMDashboard("Automation Test Dashboards", "One Click Mode Tests");
			
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
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
			Thread.sleep(2000);
			getWebElementActionXpath_D("//*[@class='k-input-values']/input").click();
			Thread.sleep(2000);
			getWebElementActionXpath_D("//span[normalize-space()='PAMTest_MeterType:  T_ElectricMain']").click();
			Thread.sleep(2000);
			
			//Click on save and close
			getWebElementActionXpath_D("//span[text()='Save & Close']").click();
			Thread.sleep(5000);
			
			//Search the site as 'PAM Test_Capriata/Saiwa'
			searchSiteInLocationList("PAMTest_Shadow 7650");
			//Click on SiteName 'PAM Test_Capriata/Saiwa'
			getWebElementActionXpath_D("//td[contains(@class,'k-table-td k-text-nowrap') and text()='PAMTest_Capriata/Saiwa']").click();
			
			refreshToLoadTheChart();
			Thread.sleep(5000);
			Assert.assertEquals(getWebElementActionXpath("LineLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650 kW");
			
			//Push the Load Profile Analysis Card widget to dashboard
			pushwidget("One Click Mode Tests","Automation Test Dashboards");
			printLog("Push the Load Profile Analysis Card widget to dashboard");
			
			// Navigate to Calendar Load Profile Analysis card
			card2Card("Calendar Load Profile Analysis");
			aJaxWait();
			Thread.sleep(5000);
			List<WebElement> onceClickStatus = d.findElements(By.xpath(locators.getProperty("VerifyEnableOneClick")));
			Assert.assertEquals(onceClickStatus.size(), 1);
			printLog("Verified that one click mode is active for Comparison analysis card");		
			
			//Assert.assertEquals(getWebElementActionXpath("LineLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650 kW");
			Assert.assertEquals(d.findElement(By.xpath("//span[@class='legend-text--calendar']/span")).getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650 kW");
			
			//Push the Calendar Load Profile Analysis Card widget to dashboard
			pushwidget("One Click Mode Tests","Automation Test Dashboards");
			printLog("Push the Calendar Load Profile Analysis Card widget to dashboard");
			
			//Go to Dashboard page
			gotoDashBoardHome();
			aJaxWait();
			Thread.sleep(5000);
			
			// compress empty space to make widgets formatted
			Utility.compressEmptySpace();
			
			// calculate the number of widgets are available
			List<WebElement> widgetNames = d.findElements(By.xpath("//div/gridster/gridster-item"));
			Assert.assertEquals(widgetNames.size(), 2);
			printLog("Number of widgets available on the dahboard are : " + widgetNames.size());
			
			//Verified the widgets title on the dashboard
			Assert.assertEquals(getWebElementActionXpath_D("//gridster/gridster-item[1]/ra-widget-container/div/div[1]/div/div/div[1]/ra-tooltip/div/div").getText(),"New Load Profile Analysis");
			Assert.assertEquals(getWebElementActionXpath_D("//gridster/gridster-item[2]/ra-widget-container/div/div[1]/div/div/div[1]/ra-tooltip/div/div").getText(),"New Calendar Load Profile Analysis");
			printLog("Verified the widgets title on the dashboard");
			
			//Assert.assertTrue(getWebElementActionXpath_D("(//span[@ng-bind-html='serie.name'])[1]").getText().replaceAll("\\n", " ").contains("PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650 kW"));
			Assert.assertEquals(getWebElementActionXpath("LineLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650 kW");
			printLog("Verified the legend on the widget chart for 'PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650 kW' site");
			
			// Click on Filter icon
			getWebElementActionXpath("FilterIcon").click();
			aJaxWait();
			Thread.sleep(2000);
			
			String siteNameConfig = "(//div[@class='ra-autocomplete-container']//input[@class='light'])[2]";
			explicitWait_Xpath(siteNameConfig);
			d.findElement(By.xpath(siteNameConfig)).click();
			d.findElement(By.xpath(siteNameConfig)).sendKeys(testData.getProperty("PAMTestHerentalsBiscuits"));
			aJaxWait();
			
			String path = "//div[contains(@class,'site-name-body')]//span[contains(text(),'"+testData.getProperty("PAMTestHerentalsBiscuits")+"')]";		
			Actions actions = new Actions(d);
			WebElement site = d.findElement(By.xpath(path)); 
			actions.moveToElement(site,1,1).click().build().perform(); 
			Thread.sleep(2000);
			d.findElement(By.xpath("//div[@id='dvglobalfilter']//label[contains(text(),'Site')]")).click();
			Thread.sleep(2000);
			// Click on Save button from Filter tab
			getWebElementActionXpath("Save_button_FilterTab").click();
			aJaxWait();
			Thread.sleep(5000);
			
			Assert.assertEquals(getWebElementActionXpath("LineLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Herentals, Biscuits \\...\\ PAMTest_Electricity to Chocolate kW");
			printLog("Verified the legend on the widget chart for 'PAMTest_Herentals, Biscuits \\...\\ PAMTest_Electricity to Chocolate  kW' site");
			
			//Click on config icon for Load Profile analysis widget
			WebElement settingsIcon = d.findElement(By.xpath("//gridster/gridster-item[2]/ra-widget-container[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[3]/div[1]/i[1]"));
			Actions act = new Actions(d); 
			act.moveToElement(settingsIcon,1,1).perform(); 
			act = new Actions(d); 
			act.moveToElement(settingsIcon,1,1).perform();
			Thread.sleep(2000);
			String settings = "//gridster/gridster-item[2]/ra-widget-container[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[3]/div[1]/i[1]"; 
			Utility.moveTheElementandClick(settings);
			Thread.sleep(5000);
			
			// Click on Visit Page link
			getWebElementActionXpath_D("//*[contains(text(),'Visit Page')]").click();
			Thread.sleep(2000);
			printLog("Click on Visit Page link");

			ArrayList<String> newTb = new ArrayList<String>(d.getWindowHandles());
			// User has switched to new tab PAM page
			d.switchTo().window(newTb.get(1));
			aJaxWait();
			Thread.sleep(10000);
			Assert.assertTrue(d.findElement(By.xpath("(//span[@class='legend-text--calendar']/span)[1]")).getText().replaceAll("\\n", " ").contains("PAMTest_Herentals, Biscuits \\...\\ PAMTest_Electricity to Chocolate kW"));
			printLog("Verified the data has updated to filtered site data on PAM page");	
		
		}catch(Throwable e) {
				e.printStackTrace();
				throw e;
			}finally {
				//Go to Dashboard page
				gotoDashBoardHome();			
				// Verify if widgets are existing if yes delete the widgets
				Utility.deleteAllWidgets();			
				Utility.deleteFilter();			
				login.logout();
			}
		}
}